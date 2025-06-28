package gui;

import modelos.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalFrame extends JFrame {

    public MenuPrincipalFrame(Usuario usuario) {
        setTitle("Sistema ASESORS - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());


        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(Color.BLACK);
        panelIzquierdo.setPreferredSize(new Dimension(300, 450));

        ImageIcon icon = new ImageIcon(getClass().getResource("/gui/logo.png")); // ruta al logo
        JLabel lblLogo = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(280, 180, Image.SCALE_SMOOTH)));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelIzquierdo.add(lblLogo, BorderLayout.CENTER);

        JLabel lblPie = new JLabel("Seguridad de cuentas de Redes Sociales", SwingConstants.CENTER);
        lblPie.setForeground(Color.LIGHT_GRAY);
        lblPie.setFont(new Font("Arial", Font.PLAIN, 12));
        panelIzquierdo.add(lblPie, BorderLayout.SOUTH);

        // Panel derecho (opciones)
        JPanel panelDerecho = new JPanel(null);
        panelDerecho.setBackground(new Color(230, 230, 235));

        JLabel lblTitulo = new JLabel(":: Sistema ASESORS - Seguridad de Cuentas RRSS ::");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(30, 30, 400, 20);
        panelDerecho.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Seleccione una opción para comenzar a operar:");
        lblSubtitulo.setBounds(30, 60, 300, 20);
        panelDerecho.add(lblSubtitulo);

        int y = 100;
        int alto = 35;
        int ancho = 200;
        int espacio = 10;

        JButton btnAgregarCliente = new JButton("Agregar cliente");
        btnAgregarCliente.addActionListener(e -> {
            new AgregarClienteFrame().setVisible(true);
        });
        btnAgregarCliente.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnAgregarCliente);


        y += alto + espacio;
        JButton btnListarClientes = new JButton("Listar clientes");
        btnListarClientes.addActionListener(e -> {
            new ListarClientesFrame().setVisible(true);
        });

        btnListarClientes.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnListarClientes);

        y += alto + espacio;
        JButton btnEliminarCliente = new JButton("Eliminar cliente");
        btnEliminarCliente.addActionListener(e -> {
            new EliminarClienteFrame().setVisible(true);
        });
        btnEliminarCliente.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnEliminarCliente);

        y += alto + espacio;
        JButton btnGestionRRSS = new JButton("Gestión cuentas RRSS");
        btnGestionRRSS.addActionListener(e -> {
            new GestionCuentasRRSSFrame().setVisible(true);
        });

        btnGestionRRSS.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnGestionRRSS);

        y += alto + espacio;
        JButton btnDiagnosticos = new JButton("Gestión diagnósticos");
        btnDiagnosticos.addActionListener(e -> {
            new GestionDiagnosticosFrame().setVisible(true);
        });
        btnDiagnosticos.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnDiagnosticos);

        y += alto + espacio;
        JButton btnReportes = new JButton("Reportes de Seguridad");
        btnReportes.addActionListener(e -> {
            new ReporteGeneralFrame().setVisible(true);
        });
        btnReportes.setBounds(30, y, ancho, alto);
        panelDerecho.add(btnReportes);

        y += alto + espacio + 10;
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(70, y, 120, 35);
        btnSalir.setBackground(Color.BLACK);
        btnSalir.setForeground(Color.WHITE);
        panelDerecho.add(btnSalir);

        JLabel lblNota = new JLabel("# Se recomienda listar los ID de cuenta al operar #", SwingConstants.CENTER);
        lblNota.setFont(new Font("Arial", Font.PLAIN, 10));
        lblNota.setBounds(10, y + 50, 280, 20);
        panelDerecho.add(lblNota);

        // Acción para salir
        btnSalir.addActionListener(e -> System.exit(0));

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }
}