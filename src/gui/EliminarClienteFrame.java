package gui;

import dao.ClienteDAO;
import dao.CuentaRRSSDAO;
import modelos.CuentaRRSS;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EliminarClienteFrame extends JFrame {

    private JTextField txtId;
    private JLabel lblEstado;

    public EliminarClienteFrame() {
        setTitle("Eliminar cliente");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Eliminar cliente por ID", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(70, 20, 260, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID del cliente:");
        lblId.setBounds(50, 70, 120, 20);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 70, 150, 25);
        add(txtId);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(130, 110, 120, 30);
        add(btnEliminar);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(30, 150, 330, 25);
        lblEstado.setForeground(Color.RED);
        add(lblEstado);

        btnEliminar.addActionListener(e -> eliminarCliente());
    }

    private void eliminarCliente() {
        String idTexto = txtId.getText().trim();
        try {
            int id = Integer.parseInt(idTexto);
            CuentaRRSSDAO cuentaDAO = new CuentaRRSSDAO();
            List<CuentaRRSS> cuentas = cuentaDAO.listarCuentasPorCliente(id);

            if (!cuentas.isEmpty()) {
                lblEstado.setForeground(Color.RED);
                lblEstado.setText("El cliente tiene cuentas asociadas. No se puede eliminar.");
                return;
            }

            ClienteDAO dao = new ClienteDAO();
            boolean eliminado = dao.eliminarCliente(id);
            if (eliminado) {
                lblEstado.setForeground(new Color(0, 128, 0));
                lblEstado.setText("Cliente eliminado correctamente.");
            } else {
                lblEstado.setForeground(Color.RED);
                lblEstado.setText("No se encontró el cliente con ese ID.");
            }
        } catch (NumberFormatException ex) {
            lblEstado.setText("El ID debe ser un número entero.");
        }
    }
}
