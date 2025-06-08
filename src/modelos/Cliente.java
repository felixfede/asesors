package modelos;

public class Cliente {
    private int id;
    private String nombreCliente;
    private String correoContacto;

    public Cliente() {}

    public Cliente(int id, String nombreCliente, String correoContacto) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.correoContacto = correoContacto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getCorreoContacto() { return correoContacto; }
    public void setCorreoContacto(String correoContacto) { this.correoContacto = correoContacto; }
}
