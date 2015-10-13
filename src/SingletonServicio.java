import java.util.ArrayList;

/**
 * Created by Melissa on 12/10/2015.
 */
public class SingletonServicio {
    private static final SingletonServicio instance = new SingletonServicio();

    private SingletonServicio() {

    }

    public static SingletonServicio getInstance() {
        return instance;
    }

    ArrayList<Servicios> servicios = new ArrayList<>();
    public void darDeAltaServicio(String nombre, String hostName, int portNumber){
        Servicios servicio = new Servicios(nombre, hostName, portNumber);
        servicios.add(servicio);
    }



}
