package dao;

import conexion.ConexionBD;
import modelos.CuentaRRSS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaRRSSDAO {

    // Agregar una cuenta de red social
    public boolean agregarCuenta(CuentaRRSS cuenta) {
        String sql = "INSERT INTO cuentas_rrss (id_cliente, plataforma, nombre_cuenta, activo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cuenta.getIdCliente());
            stmt.setString(2, cuenta.getPlataforma());
            stmt.setString(3, cuenta.getNombreCuenta());
            stmt.setBoolean(4, cuenta.isActivo());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar cuenta: " + e.getMessage());
            return false;
        }
    }

    // Listar cuentas por ID de cliente
    public List<CuentaRRSS> listarCuentasPorCliente(int idCliente) {
        List<CuentaRRSS> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_rrss WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CuentaRRSS cuenta = new CuentaRRSS(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getString("plataforma"),
                        rs.getString("nombre_cuenta"),
                        rs.getBoolean("activo")
                );
                cuentas.add(cuenta);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar cuentas: " + e.getMessage());
        }

        return cuentas;
    }



    // Eliminar cuenta por ID
    public boolean eliminarCuenta(int idCuenta) {
        String sql = "DELETE FROM cuentas_rrss WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cuenta: " + e.getMessage());
            return false;
        }
    }
    public CuentaRRSS buscarPorId(int idCuenta) {
        String sql = "SELECT * FROM cuentas_rrss WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CuentaRRSS(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getString("plataforma"),
                        rs.getString("nombre_cuenta"),
                        rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cuenta por ID: " + e.getMessage());
        }

        return null;
    }
}
