import Cliente.Excepciones.ErrorSolicitudExcepcion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Melissa on 05/10/2015.
 */
public class ProxyServer {
    ArrayList<Candidatos> participantes = new ArrayList<Candidatos>();
    int portProxyServer = 4445;
    String nombreServicio = "Barras";

    public static void main(String[] args) throws IOException {
        ProxyServer px = new ProxyServer();
        px.registrarServicio();
        px.empezar();
    }

    public void registrarServicio() {
        String hostBroker = "localhost";
        int portBroker = 4444;
        try (
                Socket socket = new Socket(hostBroker, portBroker);
                PrintWriter toBroker = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromBroker = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader mensaje =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromBrokerStream;
            String fromProxyServer;

            fromBrokerStream = fromBroker.readLine();
            System.out.println("Broker: " + fromBrokerStream);

            System.out.print(">>");
            fromProxyServer = "alta/" + nombreServicio + "-" + socket.getInetAddress().getCanonicalHostName() + "-" + portProxyServer;
            System.out.println("ProxyServer: " + fromProxyServer);
            toBroker.println(fromProxyServer);

            fromBrokerStream = fromBroker.readLine();
            System.out.println("Broker: " + fromBrokerStream);

        } catch (UnknownHostException e) {
            System.err.println("No conozco el host " + hostBroker);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede leer/escribir para la conexion de " +
                    hostBroker);
            System.exit(1);
        }
    }

    public void empezar() throws IOException {
        /*if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }*/
        System.out.println("Servicio corriendo");
        int portNumber = 4445;

        while (true) {
            try (
                    ServerSocket proxyServerSocket = new ServerSocket(portNumber);
                    Socket brokerSocket = proxyServerSocket.accept();
                    PrintWriter toBroker =
                            new PrintWriter(brokerSocket.getOutputStream(), true);
                    BufferedReader fromBroker = new BufferedReader(
                            new InputStreamReader(brokerSocket.getInputStream()));
            ) {

                String data, answer;

                while ((data = fromBroker.readLine()) != null) {
                    System.out.println("Proveyendo Servicio de pasteles.");
                    unpack(data);
                    //graficar

                    answer = "Votos registrados";
                    toBroker.println(answer);
                }


            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            } catch (ErrorSolicitudExcepcion errorSolicitudExcepcion) {
                System.out.println(errorSolicitudExcepcion.getMessage());
            }
        }
    }

    public void unpack(String data) throws ErrorSolicitudExcepcion {
        String[] candidatos = data.split(",");
        for (int i = 0; i < candidatos.length; i++) {
            String[] datosCandidato = candidatos[i].split("-");
            if (datosCandidato.length % 3 != 0) {
                throw new ErrorSolicitudExcepcion("Error: Los datos estan incompletos.");
            }
            Candidatos candidato = new Candidatos(Integer.parseInt(datosCandidato[0]), datosCandidato[1], Integer.parseInt(datosCandidato[2]));
            this.participantes.add(candidato);
        }
    }
}
