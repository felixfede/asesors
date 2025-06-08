package modelos;

public class CuentaRRSS {
    private int id;
    private int idCliente;
    private String plataforma;
    private String nombreCuenta;
    private boolean activo;

    public CuentaRRSS() {}

    public CuentaRRSS(int id, int idCliente, String plataforma, String nombreCuenta, boolean activo) {
        this.id = id;
        this.idCliente = idCliente;
        this.plataforma = plataforma;
        this.nombreCuenta = nombreCuenta;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public String getNombreCuenta() { return nombreCuenta; }
    public void setNombreCuenta(String nombreCuenta) { this.nombreCuenta = nombreCuenta; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
