package gui;

import dao.UsuarioDAO;
import modelos.Usuario;
import util.HashUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrameV2 extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel lblEstado;

    public LoginFrameV2() {
        setTitle("ASESORS - Iniciar Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal dividido
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel izquierdo (imagen y branding)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(Color.BLACK);
        panelIzquierdo.setPreferredSize(new Dimension(300, 400));
        panelIzquierdo.setLayout(new BorderLayout());

        // Imagen/logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/gui/logo.png")); // ruta al logo
        JLabel lblLogo = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(290, 200, Image.SCALE_SMOOTH)));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panelIzquierdo.add(lblLogo, BorderLayout.CENTER);

        // Pie de texto
        JLabel lblPie = new JLabel("Seguridad de cuentas de Redes Sociales", SwingConstants.CENTER);
        lblPie.setForeground(Color.LIGHT_GRAY);
        lblPie.setFont(new Font("Arial", Font.PLAIN, 12));
        panelIzquierdo.add(lblPie, BorderLayout.SOUTH);

        // Panel derecho (formulario)
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(230, 230, 235));
        panelDerecho.setLayout(null);

        JLabel lblTitulo = new JLabel("Iniciar Sesion");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(80, 40, 200, 25);
        panelDerecho.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Nombre de usuario:");
        lblUsuario.setBounds(40, 90, 200, 20);
        panelDerecho.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 115, 250, 28);
        panelDerecho.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 160, 200, 20);
        panelDerecho.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 185, 250, 28);
        panelDerecho.add(txtPassword);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(100, 235, 120, 35);
        panelDerecho.add(btnLogin);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(40, 285, 250, 25);
        lblEstado.setForeground(Color.RED);
        panelDerecho.add(lblEstado);

        // Acción del botón
        btnLogin.addActionListener((ActionEvent e) -> validarCredenciales());

        // Agregar subpaneles al panel principal
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(usuario, pass);

        if (u != null) {
            lblEstado.setForeground(new Color(0, 128, 0));
            //lblEstado.setText("Login correcto. Bienvenido " + u.getNombreUsuario());
            new MenuPrincipalFrame(u).setVisible(true);
            dispose(); // Cierra el login

        } else {
            lblEstado.setForeground(Color.RED);
            lblEstado.setText("Usuario o contraseña incorrectos");
        }
    }

}