package modelos;

public class Diagnostico {
    private int id;
    private int idCuenta;
    private boolean cambioPassword;
    private boolean tiene2FA;
    private boolean actividadSospechosa;
    private String resultado;
    private String observaciones;

    public Diagnostico() {}

    public Diagnostico(int id, int idCuenta, boolean cambioPassword, boolean tiene2FA,
                       boolean actividadSospechosa, String resultado, String observaciones) {
        this.id = id;
        this.idCuenta = idCuenta;
        this.cambioPassword = cambioPassword;
        this.tiene2FA = tiene2FA;
        this.actividadSospechosa = actividadSospechosa;
        this.resultado = resultado;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public boolean isCambioPassword() {
        return cambioPassword;
    }

    public void setCambioPassword(boolean cambioPassword) {
        this.cambioPassword = cambioPassword;
    }

    public boolean isTiene2FA() {
        return tiene2FA;
    }

    public void setTiene2FA(boolean tiene2FA) {
        this.tiene2FA = tiene2FA;
    }

    public boolean isActividadSospechosa() {
        return actividadSospechosa;
    }

    public void setActividadSospechosa(boolean actividadSospechosa) {
        this.actividadSospechosa = actividadSospechosa;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
