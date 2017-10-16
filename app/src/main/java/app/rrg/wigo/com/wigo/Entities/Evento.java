package app.rrg.wigo.com.wigo.Entities;

/**
 * Created by danri on 15/10/2017.
 */

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private String hora;
    private String fecha;
    private String precio;
    private String direccion;
    private int creador;
    //private String imagen;

    public Evento(){}

    public Evento(String nombre, String descripcion, String hora, String fecha, String precio, String direccion, int creador){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
        this.fecha = fecha;
        this.precio = precio;
        this.direccion = direccion;
        this.creador = creador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescipcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void setCreador(int creador){
        this.creador = creador;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
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

    public int getCreador(){
        return creador;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", precio='" + precio + '\'' +
                ", direccion='" + direccion + '\'' +
                ", creador='" + creador + '\'' +
                '}';
    }
}
