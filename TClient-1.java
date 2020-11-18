import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TClient
{

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        System.out.println("Client starting - connecting to Host " + host + " at Port " + port);
        System.out.println("Author: William Dunn\n");
        System.out.println("Type 'help' for more information.\n");

        String command;
        String temp;

        Scanner sc = new Scanner(System.in);
        Socket s = new Socket(host, port);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.print("cmd: ");
        command = sc.nextLine();

        PrintStream p = new PrintStream(s.getOutputStream());
        p.println(command);

        while (sc1.hasNext())
        {
            temp = sc1.nextLine();
            System.out.println(temp);
        }

        System.exit(0);
        sc.Close();
        s.Close();
        sc1.Close();
    }

}




