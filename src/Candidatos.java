import java.util.Scanner;

/**
 * Created by Melissa on 30/09/2015.
 */
public class Candidatos {

    int id, votos;
    String nombre;

    public Candidatos(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.votos = 0;
    }

    public Candidatos(int id, String nombre, int votos) {
        this.id = id;
        this.votos = votos;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Nombre: " + nombre + " - " + votos;
    }

    public int getVotos() {
        return votos;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void votar() {
        this.votos++;
    }
}
