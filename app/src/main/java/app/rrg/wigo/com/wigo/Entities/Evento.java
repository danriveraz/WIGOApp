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
    private String imagen;

    public Evento(){}

    public Evento(String nombre, String descripcion, String hora, String fecha, String precio, String direccion, int creador, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
        this.fecha = fecha;
        this.precio = precio;
        this.direccion = direccion;
        this.creador = creador;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCreador() {
        return creador;
    }

    public void setCreador(int creador) {
        this.creador = creador;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
