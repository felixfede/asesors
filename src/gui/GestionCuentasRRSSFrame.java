package gui;

import dao.CuentaRRSSDAO;
import modelos.CuentaRRSS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionCuentasRRSSFrame extends JFrame {

    public GestionCuentasRRSSFrame() {
        setTitle("Gestión de Cuentas de Redes Sociales");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Cuentas RRSS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 300, 25);
        add(lblTitulo);

        JButton btnAgregar = new JButton("Agregar cuenta");
        btnAgregar.setBounds(150, 70, 200, 35);
        add(btnAgregar);

        JButton btnListar = new JButton("Listar cuentas de un cliente");
        btnListar.setBounds(150, 120, 200, 35);
        add(btnListar);

        JButton btnEliminar = new JButton("Eliminar cuenta");
        btnEliminar.setBounds(150, 170, 200, 35);
        add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(200, 230, 100, 30);
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        // Boton para abrir pantalla AgregarCuentaRRSS
        btnAgregar.addActionListener(e -> new AgregarCuentaRRSSFrame().setVisible(true));

        // Boton para abrir pantalla ListarCuentasPorCliente
        btnListar.addActionListener(e -> new ListarCuentasPorClienteFrame().setVisible(true));

        // Boton para abrir pantalla EliminarCuentasPorCliente
        btnEliminar.addActionListener(e -> new EliminarCuentaRRSSFrame().setVisible(true));
    }
}

class AgregarCuentaRRSSFrame extends JFrame {

    private JTextField txtIdCliente;
    private JTextField txtPlataforma;
    private JTextField txtNombreCuenta;
    private JLabel lblEstado;

    public AgregarCuentaRRSSFrame() {
        setTitle("Agregar Cuenta RRSS");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Agregar nueva cuenta RRSS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(70, 20, 260, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID del cliente:");
        lblId.setBounds(40, 70, 120, 20);
        add(lblId);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(170, 70, 170, 25);
        add(txtIdCliente);

        JLabel lblPlataforma = new JLabel("Plataforma:");
        lblPlataforma.setBounds(40, 110, 120, 20);
        add(lblPlataforma);

        txtPlataforma = new JTextField();
        txtPlataforma.setBounds(170, 110, 170, 25);
        add(txtPlataforma);

        JLabel lblNombre = new JLabel("Nombre de la cuenta:");
        lblNombre.setBounds(40, 150, 140, 20);
        add(lblNombre);

        txtNombreCuenta = new JTextField();
        txtNombreCuenta.setBounds(170, 150, 170, 25);
        add(txtNombreCuenta);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 190, 120, 30);
        add(btnGuardar);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(30, 230, 330, 25);
        lblEstado.setForeground(Color.RED);
        add(lblEstado);

        btnGuardar.addActionListener(e -> guardarCuenta());
    }

    private void guardarCuenta() {
        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText().trim());
            String plataforma = txtPlataforma.getText().trim();
            String nombreCuenta = txtNombreCuenta.getText().trim();

            CuentaRRSS cuenta = new CuentaRRSS();
            cuenta.setIdCliente(idCliente);
            cuenta.setPlataforma(plataforma);
            cuenta.setNombreCuenta(nombreCuenta);
            cuenta.setActivo(true);

            CuentaRRSSDAO dao = new CuentaRRSSDAO();
            boolean exito = dao.agregarCuenta(cuenta);

            if (exito) {
                lblEstado.setForeground(new Color(0, 128, 0));
                lblEstado.setText("Cuenta agregada exitosamente.");
                txtIdCliente.setText("");
                txtPlataforma.setText("");
                txtNombreCuenta.setText("");
            } else {
                lblEstado.setForeground(Color.RED);
                lblEstado.setText("Error al agregar cuenta.");
            }

        } catch (NumberFormatException e) {
            lblEstado.setText("El ID del cliente debe ser un número.");
        }
    }
}

class ListarCuentasPorClienteFrame extends JFrame {

    private JTextField txtIdCliente;
    private JTable tabla;
    private DefaultTableModel modelo;

    public ListarCuentasPorClienteFrame() {
        setTitle("Listar Cuentas por Cliente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Listar cuentas de un cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(150, 20, 300, 25);
        add(lblTitulo);

        JLabel lblIdCliente = new JLabel("ID del cliente:");
        lblIdCliente.setBounds(50, 70, 120, 20);
        add(lblIdCliente);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(170, 70, 120, 25);
        add(txtIdCliente);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(310, 70, 100, 25);
        add(btnBuscar);

        String[] columnas = {"ID Cuenta", "Plataforma", "Nombre de cuenta", "Activa"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(50, 120, 480, 180);
        add(scroll);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(240, 320, 100, 30);
        add(btnCerrar);

        btnBuscar.addActionListener(e -> listarCuentas());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void listarCuentas() {
        modelo.setRowCount(0);
        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText().trim());
            CuentaRRSSDAO dao = new CuentaRRSSDAO();
            java.util.List<CuentaRRSS> cuentas = dao.listarCuentasPorCliente(idCliente);

            if (cuentas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron cuentas para el ID especificado.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

            for (CuentaRRSS c : cuentas) {
                Object[] fila = {
                        c.getId(),
                        c.getPlataforma(),
                        c.getNombreCuenta(),
                        c.isActivo() ? "Sí" : "No"
                };
                modelo.addRow(fila);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de cliente inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class EliminarCuentaRRSSFrame extends JFrame {

    private JTextField txtIdCuenta;
    private JLabel lblEstado;

    public EliminarCuentaRRSSFrame() {
        setTitle("Eliminar Cuenta RRSS");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Eliminar cuenta por ID de RRSS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(70, 20, 260, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("Ingresar ID RRSS:");
        lblId.setBounds(50, 70, 120, 20);
        add(lblId);

        txtIdCuenta = new JTextField();
        txtIdCuenta.setBounds(170, 70, 150, 25);
        add(txtIdCuenta);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(130, 110, 120, 30);
        add(btnEliminar);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(30, 150, 330, 25);
        lblEstado.setForeground(Color.RED);
        add(lblEstado);

        btnEliminar.addActionListener(e -> eliminarCuenta());
    }

    private void eliminarCuenta() {
        String idTexto = txtIdCuenta.getText().trim();
        try {
            int id = Integer.parseInt(idTexto);
            CuentaRRSSDAO dao = new CuentaRRSSDAO();
            boolean eliminado = dao.eliminarCuenta(id);
            if (eliminado) {
                lblEstado.setForeground(new Color(0, 128, 0));
                lblEstado.setText("Cuenta eliminada correctamente.");
            } else {
                lblEstado.setForeground(Color.RED);
                lblEstado.setText("No se encontró una cuenta con ese ID.");
            }
        } catch (NumberFormatException ex) {
            lblEstado.setText("El ID debe ser un número entero.");
        }
    }
}

