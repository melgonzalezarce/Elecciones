/**
 * Created by Melissa on 04/10/2015.
 */


import Cliente.Excepciones.ErrorSolicitudExcepcion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ProxyCliente {

    ArrayList<Candidatos> participantes;

    public void solicitarServicio(String hostName, int portNumber, ArrayList<Candidatos> participantes) {
        this.participantes = participantes;
        /*if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }*/
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromServer = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader mensaje =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServerStream;
            String fromUser;

            while ((fromServerStream = fromServer.readLine()) != null) {
                if (fromServerStream.contains("Error:")) {
                    throw new ErrorSolicitudExcepcion(fromServerStream);
                }
                System.out.println("Server: " + fromServerStream);
                if (fromServerStream.contains("Votos")){
                    break;
                }
                System.out.print(">>");
                fromUser = mensaje.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    fromUser += "/" + pack();
                    toServer.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No conozco el host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede leer/escribir para la conexion de " +
                    hostName);
            System.exit(1);
        } catch (ErrorSolicitudExcepcion errorSolicitudExcepcion) {
            System.out.println("Excepcion: " + errorSolicitudExcepcion.getMessage());
        }
    }

    public String pack() {
        String paquete = "";
        for (Candidatos candidato : participantes) {
            paquete += candidato.id + "-" + candidato.nombre + "-" + candidato.votos + ",";
        }
        return paquete;
    }
}
