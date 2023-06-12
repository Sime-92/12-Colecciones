import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Socio implements Serializable {
    private String nombre;
    private Date fechaIngreso;

    public Socio(String nombre, Date fechaIngreso) {
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }
}





