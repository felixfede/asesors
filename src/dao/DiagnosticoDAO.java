package dao;

import conexion.ConexionBD;
import modelos.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    // Registrar un nuevo diagnóstico
    public boolean registrarDiagnostico(Diagnostico diag) {
        String sql = """
        INSERT INTO diagnosticos 
        (id_cuenta, cambio_password, tiene_2fa, actividad_sospechosa, resultado, observaciones) 
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            resultado = ?,
            cambio_password = ?,
            tiene_2fa = ?,
            actividad_sospechosa = ?,
            observaciones = ?
        """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // INSERT
            stmt.setInt(1, diag.getIdCuenta());
            stmt.setBoolean(2, diag.isCambioPassword());
            stmt.setBoolean(3, diag.isTiene2FA());
            stmt.setBoolean(4, diag.isActividadSospechosa());
            stmt.setString(5, diag.getResultado());
            stmt.setString(6, diag.getObservaciones());

            // UPDATE
            stmt.setString(7, diag.getResultado());
            stmt.setBoolean(8, diag.isCambioPassword());
            stmt.setBoolean(9, diag.isTiene2FA());
            stmt.setBoolean(10, diag.isActividadSospechosa());
            stmt.setString(11, diag.getObservaciones());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // Listar diagnosticos por cuenta
    public List<Diagnostico> listarDiagnosticosPorCuenta(int idCuenta) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM diagnosticos WHERE id_cuenta = ? ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Diagnostico diag = new Diagnostico();
                diag.setId(rs.getInt("id"));
                diag.setIdCuenta(rs.getInt("id_cuenta"));
                diag.setCambioPassword(rs.getBoolean("cambio_password"));
                diag.setTiene2FA(rs.getBoolean("tiene_2fa"));
                diag.setActividadSospechosa(rs.getBoolean("actividad_sospechosa"));
                diag.setResultado(rs.getString("resultado"));
                diag.setObservaciones(rs.getString("observaciones"));


                lista.add(diag);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar diagnósticos: " + e.getMessage());
        }

        return lista;
    }
    public boolean guardarODiagnostico(Diagnostico diag) {
        String sql = """
        INSERT INTO diagnosticos (
            id_cuenta, resultado, cambio_password, tiene_2fa, actividad_sospechosa, observaciones
        )
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            resultado = ?,
            cambio_password = ?,
            tiene_2fa = ?,
            actividad_sospechosa = ?,
            observaciones = ?
        """;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // INSERT
            stmt.setInt(1, diag.getIdCuenta());
            stmt.setString(2, diag.getResultado());
            stmt.setBoolean(3, diag.isCambioPassword());
            stmt.setBoolean(4, diag.isTiene2FA());
            stmt.setBoolean(5, diag.isActividadSospechosa());
            stmt.setString(6, diag.getObservaciones());

            // UPDATE
            stmt.setString(7, diag.getResultado());
            stmt.setBoolean(8, diag.isCambioPassword());
            stmt.setBoolean(9, diag.isTiene2FA());
            stmt.setBoolean(10, diag.isActividadSospechosa());
            stmt.setString(11, diag.getObservaciones());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar diagnóstico: " + e.getMessage());
            return false;
        }
    }
}
