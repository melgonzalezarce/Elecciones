import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Melissa on 27/09/2015.
 */
public class Broker {

    public static void main(String[] args) throws IOException {

        /*if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }*/

        int portNumber = 4444;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new MultiServerBrokerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("No se puede escuchar al puerto " + portNumber);
            System.exit(-1);
        }
    }



}
