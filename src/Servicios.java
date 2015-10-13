/**
 * Created by Melissa on 04/10/2015.
 */
public class Servicios {
    String nombre, host;
    int puerto;

    public Servicios(String nombre, String host, int puerto) {
        this.nombre = nombre;
        this.host = host;
        this.puerto = puerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
