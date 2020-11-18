import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TServer
{
    public static void main(String[] args) throws IOException, TwitterException
    {
        String temp = "";

        String command;

        InetAddress ipAddr = InetAddress.getLocalHost();
        ipAddr.getHostAddress();

        ServerSocket s1 = new ServerSocket(5050);
        Socket ss = s1.accept();
        Scanner sc = new Scanner(ss.getInputStream());
        command = sc.nextLine();

        String[] commandArgs = command.split(" ");

        ConfigurationBuilder cf = new ConfigurationBuilder();

        cf.setDebugEnabled(true).setOAuthConsumerKey("6YMMAke5FLgRdEcBVBJzCzzH6")
            .setOAuthConsumerSecret("OFQ8RoNhz7M0RCqXrbohqXxqYiVhjBObquWwGqJ4cnFaxBeSpn")
            .setOAuthAccessToken("937825913793863680-5SZvUesIDOu9nz89LkCkYmuUBHYzN6j")
            .setOAuthAccessTokenSecret("mokYnSlCFmBEpEEhPiiTbREDRFTt8ajiseXg2eLxxlkUY");

        TwitterFactory tf = new TwitterFactory(cf.build());
        twitter4j.Twitter twitter = tf.getInstance();

        String command1;
        command1 = commandArgs[0];

        String user1;

        if (commandArgs.length == 1) {

            switch (command1) {
            case "help":
                temp = "Valid commands are:\n" + "\thelp - this command\n" + " \tid   - version/author(s) info \n"
                    + " \thost - server's host \n" + " \tport – server’s port \n"
                    + " \ttweet user count - show the users last 'count' tweets\n"
                    + " \ttweet user - return the user's profile";
                break;
            case "id":
                temp = "Twitter Server Version 1.2\n" + "Written by William Dunn";
                break;
            case "host":
                temp = "Server running on: " + ipAddr;
                break;
            case "port":
                temp = "Port is: ";
                break;
            }
        } else if (commandArgs.length == 2) {

            user1 = commandArgs[1];

            User user = twitter.showUser(user1);
            if (user.getStatus() != null) {
                System.out.println("@" + user.getScreenName() + " - " + user.getDescription());
            } else {
                System.out.println("@" + user.getScreenName());
            }

        } else if (commandArgs.length == 3) {

            List<Status> status = twitter.getUserTimeline(commandArgs[1]);
            int count = 1;

            for (Status st : status) {
                if (count <= Integer.parseInt(commandArgs[2])) {
                    System.out.println("-- tweet " + count + " of " + Integer.parseInt(commandArgs[2])
                                       + " ------------------------------------");
                    System.out.println(st.getCreatedAt());
                    System.out.println(st.getUser().getName());
                    System.out.println(" >>>" + st.getText());
                    System.out.println("");
                    count++;
                }
            }
        } else {
            System.err.println("Invalid command");
            System.exit(-1);

        }
    }

    PrintStream p = new PrintStream(ss.getOutputStream());
    p.print(temp);

    s1.Close();
    sc.Close();
	}

}



