import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Melissa on 27/09/2015.
 */
public class MultiServerBrokerThread extends Thread {
    private Socket socket = null;

    public MultiServerBrokerThread(Socket socket) {
        super("MultiServerBrokerThread");
        this.socket = socket;
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Se ha detectado una nueva conexión.");
    }

    public void run() {

        try (
                PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            toClient.println("Que servicio desea?");

            while ((inputLine = fromClient.readLine()) != null) {
                System.out.println(inputLine);
                outputLine = procesarPeticion(inputLine);
                toClient.println(outputLine);
                if (outputLine.contains("Error:") || outputLine.contains("Alta:")) {
                    break;
                }
            }
            System.out.println("Broker cerró conexión");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String procesarPeticion(String inputLine) {
        String output = "Error: No se encontró servicio.";
        String[] inputLineArray = inputLine.split("/");
        String servicioSolicitado = inputLineArray[0];
        String datosEmpaquetados = inputLineArray[1];

        if (servicioSolicitado.contains("alta")) {
            String[] datosServicio = datosEmpaquetados.split("-");
            SingletonServicio.getInstance().darDeAltaServicio(datosServicio[0], datosServicio[1], Integer.parseInt(datosServicio[2]));
            output = "Alta: Su servicio se ha agregado";
            return output;
        }
        for (Servicios servicio : SingletonServicio.getInstance().servicios) {
            if (servicio.nombre.equals(servicioSolicitado)) {
                String[] datosPorCandidato = datosEmpaquetados.split(",");
                for (int i = 0; i < datosPorCandidato.length; i++) {
                    String[] atributoPorCandidato = datosPorCandidato[i].split("-");
                    if (atributoPorCandidato.length % 3 != 0) {
                        output = "Error: Los datos para el servicio que solicitas, estan incompletos.";
                        return output;
                    }
                }
                output = conectarseAServidor(servicio.getHost(), servicio.getPuerto(), datosEmpaquetados);
            }
        }

        return output;
    }

    public String conectarseAServidor(String hostName, int portNumber, String datosEmpaquetados) {
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter toProxyServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromProxyServer = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader mensaje =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromProxyServerStream;
            String fromBroker;


            fromBroker = datosEmpaquetados;
            if (fromBroker != null) {
                System.out.println("Broker: " + fromBroker);
                toProxyServer.println(fromBroker);
            }

            return fromProxyServer.readLine();

        } catch (UnknownHostException e) {
            System.err.println("No conozco el host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede leer/escribir para la conexion de " +
                    hostName);
            System.exit(1);
        }
        return "";
    }

}
