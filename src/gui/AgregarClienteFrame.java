package gui;

import dao.ClienteDAO;
import modelos.Cliente;

import javax.swing.*;
import java.awt.*;

public class AgregarClienteFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JLabel lblEstado;

    public AgregarClienteFrame() {
        setTitle("Agregar nuevo cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Agregar Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 200, 30);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre del cliente:");
        lblNombre.setBounds(40, 70, 150, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 70, 170, 25);
        add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo de contacto:");
        lblCorreo.setBounds(40, 110, 150, 20);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(180, 110, 170, 25);
        add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 160, 120, 30);
        add(btnGuardar);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(40, 210, 300, 25);
        lblEstado.setForeground(Color.RED);
        add(lblEstado);

        btnGuardar.addActionListener(e -> guardarCliente());
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()) {
            lblEstado.setText("El nombre del cliente no puede estar vacío.");
            return;
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombreCliente(nombre);
        nuevo.setCorreoContacto(correo);

        ClienteDAO dao = new ClienteDAO();
        boolean exito = dao.agregarCliente(nuevo);

        if (exito) {
            lblEstado.setForeground(new Color(0, 128, 0));
            lblEstado.setText("Cliente agregado exitosamente.");
            txtNombre.setText("");
            txtCorreo.setText("");
        } else {
            lblEstado.setForeground(Color.RED);
            lblEstado.setText("Error al agregar cliente.");
        }
    }
}