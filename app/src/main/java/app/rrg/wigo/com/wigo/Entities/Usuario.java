package app.rrg.wigo.com.wigo.Entities;

/**
 * Created by danri on 11/10/2017.
 */

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private String celular;
    private String nombreEmpresa;
    private String contrasena;
    private String foto;

    public Usuario(){}

    public Usuario(String nombre, String correo, String direccion, String telefono, String celular, String nombreEmpresa, String contrasena, String foto) {
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.nombreEmpresa = nombreEmpresa;
        this.contrasena = contrasena;
        this.foto = foto;
    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getFoto() {return foto;}

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='"        + nombre        + '\'' +
                ", correo='"        + correo        + '\'' +
                ", nombre='"        + direccion     + '\'' +
                ", telefono='"      + telefono      + '\'' +
                ", celular='"       + celular       + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", contrasena='"    + contrasena    + '\'' +
                ", foto='"          + foto          + '\'' +
                '}';
    }
}
