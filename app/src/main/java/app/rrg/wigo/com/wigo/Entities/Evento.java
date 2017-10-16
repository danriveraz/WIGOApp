package app.rrg.wigo.com.wigo.Entities;

/**
 * Created by danri on 15/10/2017.
 */

public class Evento {
    private int id;
    private String nombre;
    private String descipcion;
    private String hora;
    private String fecha;
    private String precio;
    private String direccion;
    //private String imagen;

    public Evento(){}

    public Evento(String nombre, String descipcion, String hora, String fecha, String precio, String direccion){
        this.nombre = nombre;
        this.descipcion = descipcion;
        this.hora = hora;
        this.fecha = fecha;
        this.precio = precio;
        this.descipcion = descipcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descipcion + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", precio='" + precio + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
