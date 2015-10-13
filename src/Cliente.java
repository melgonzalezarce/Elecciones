import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Melissa on 30/09/2015.
 */
public class Cliente {
    String hostName;
    int portNumber;
    ArrayList<Candidatos> participantes = new ArrayList<Candidatos>();

    public Cliente(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;

        Candidatos candidatos1 = new Candidatos(1, "Melissa");
        Candidatos candidatos2 = new Candidatos(2, "Romario");
        participantes.add(candidatos1);
        participantes.add(candidatos2);
    }

    public void opciones(int opcion) {
        switch (opcion) {
            case 1:
                agregarCandidatos();
                break;
            case 2:
                verCandidatos();
                break;
            case 3:
                votarPorCandidato();
                break;
            case 4:
                solicitarServicio();
                break;
            case 5:
                verOpciones();
                break;
            default:
                System.out.println("Opcion no encontrada");
                verOpciones();
        }
    }

    public void verOpciones() {
        System.out.println("Opciones: ");
        System.out.println("1.- Agregar candidato ");
        System.out.println("2.- Ver candidatos ");
        System.out.println("3.- Votar por candidato");
        System.out.println("4.- Solicitar servicio");
        System.out.println("5.- Ver opciones");
    }

    public void solicitarServicio() {
        ProxyCliente px = new ProxyCliente();
        px.solicitarServicio(hostName, portNumber, participantes);
    }

    public void agregarCandidatos() {
        System.out.println("Nombre: ");
        Scanner sc = new Scanner(System.in);
        String nombreCandidato = sc.nextLine();

        Candidatos Presi = new Candidatos(participantes.size() + 1, nombreCandidato);
        participantes.add(Presi);

        System.out.println("Candidatos Agregado: " + Presi.nombre);

        opciones(2);
    }

    public void verCandidatos() {
        System.out.println(participantes.toString());
    }

    public void votarPorCandidato() {
        for (Candidatos candidato : participantes) {
            System.out.println(candidato.getId() + ".- " + candidato.nombre);
        }
        System.out.println("Seleccione el numero del candidado para votar");

        Scanner scn = new Scanner(System.in);
        int voto = scn.nextInt();

        for (Candidatos candidato : participantes) {
            if (voto == candidato.id) {
                candidato.votar();
            }
        }
        System.out.println("Has votado");
    }

    public static void main(String[] args) {

        String hostName = "localhost";
        int portNumber = 4444;

        Cliente cliente = new Cliente(hostName, portNumber);

        Scanner scn = new Scanner(System.in);
        System.out.println("Opciones: ");
        System.out.println("1.- Agregar candidato ");
        System.out.println("2.- Ver candidatos ");
        System.out.println("3.- Votar por candidato");
        System.out.println("4.- Solicitar servicio");
        System.out.println("5.- Ver opciones");
        int opcion;
        try {
            while ((opcion = scn.nextInt()) != 0) {
                cliente.opciones(opcion);
                System.out.print(">>");
            }
            System.out.println("Terminamos");

        } catch (InputMismatchException e) {
            System.out.println("Numeros porfavor");
        }
    }
}
