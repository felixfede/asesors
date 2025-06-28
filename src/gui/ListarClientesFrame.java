package gui;

import dao.ClienteDAO;
import modelos.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarClientesFrame extends JFrame {

    public ListarClientesFrame() {
        setTitle("Listado de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Clientes registrados en el sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Muestro la tabla de clientes
        String[] columnas = {"ID", "Nombre", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        // Obtengo los datos de la BD
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.listarClientes();
        for (Cliente c : lista) {
            Object[] fila = {c.getId(), c.getNombreCliente(), c.getCorreoContacto()};
            modelo.addRow(fila);
        }

        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Boton cerrar para salir del menu
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.add(btnCerrar);
        panel.add(pie, BorderLayout.SOUTH);

        setContentPane(panel);
    }
}
