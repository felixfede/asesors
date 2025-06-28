package gui;

import dao.DiagnosticoDAO;
import modelos.Diagnostico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestionDiagnosticosFrame extends JFrame {

    public GestionDiagnosticosFrame() {
        setTitle("Gestión de Diagnósticos");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Diagnósticos de Cuentas RRSS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(50, 20, 400, 25);
        add(lblTitulo);

        JButton btnRegistrar = new JButton("Registrar diagnóstico a cuenta");
        btnRegistrar.setBounds(150, 70, 210, 35);
        add(btnRegistrar);

        JButton btnVer = new JButton("Ver diagnóstico de cuenta");
        btnVer.setBounds(150, 120, 210, 35);
        add(btnVer);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(200, 190, 100, 30);
        add(btnCerrar);

      //defino los Listener de los botones
        btnRegistrar.addActionListener(e -> new RegistrarDiagnosticoFrame().setVisible(true));
        btnVer.addActionListener(e -> new VerDiagnosticoCuentaFrame().setVisible(true));
        btnCerrar.addActionListener(e -> dispose());
    }
}

class RegistrarDiagnosticoFrame extends JFrame {

    private JTextField txtIdCuenta;
    private JCheckBox chkPassword;
    private JCheckBox chk2FA;
    private JCheckBox chkSospechosa;
    private JComboBox<String> cmbResultado;
    private JTextArea txtObservaciones;
    private JLabel lblEstado;

    public RegistrarDiagnosticoFrame() {
        setTitle("Registrar Diagnóstico");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar diagnóstico a una cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(70, 10, 360, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID de la cuenta:");
        lblId.setBounds(40, 50, 150, 20);
        add(lblId);

        txtIdCuenta = new JTextField();
        txtIdCuenta.setBounds(180, 50, 200, 25);
        add(txtIdCuenta);

        chkPassword = new JCheckBox("Se cambió la contraseña");
        chkPassword.setBounds(40, 90, 200, 25);
        add(chkPassword);

        chk2FA = new JCheckBox("Tiene 2FA");
        chk2FA.setBounds(40, 120, 200, 25);
        add(chk2FA);

        chkSospechosa = new JCheckBox("Actividad sospechosa");
        chkSospechosa.setBounds(40, 150, 200, 25);
        add(chkSospechosa);

        JLabel lblResultado = new JLabel("Resultado:");
        lblResultado.setBounds(40, 190, 150, 20);
        add(lblResultado);

        cmbResultado = new JComboBox<>(new String[]{"segura", "comprometida", "indeterminado"});
        cmbResultado.setBounds(180, 190, 200, 25);
        add(cmbResultado);

        JLabel lblObs = new JLabel("Observaciones:");
        lblObs.setBounds(40, 230, 150, 20);
        add(lblObs);

        txtObservaciones = new JTextArea();
        txtObservaciones.setBounds(180, 230, 250, 60);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        add(txtObservaciones);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(180, 310, 120, 30);
        add(btnGuardar);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setBounds(50, 350, 400, 25);
        lblEstado.setForeground(Color.RED);
        add(lblEstado);

        btnGuardar.addActionListener(e -> guardarDiagnostico());
    }

    private void guardarDiagnostico() {
        try {
            int idCuenta = Integer.parseInt(txtIdCuenta.getText().trim());
            boolean cambio = chkPassword.isSelected();
            boolean dosfa = chk2FA.isSelected();
            boolean sospechosa = chkSospechosa.isSelected();
            String resultado = (String) cmbResultado.getSelectedItem();
            String obs = txtObservaciones.getText().trim();

            Diagnostico diag = new Diagnostico();
            diag.setIdCuenta(idCuenta);
            diag.setCambioPassword(cambio);
            diag.setTiene2FA(dosfa);
            diag.setActividadSospechosa(sospechosa);
            diag.setResultado(resultado);
            diag.setObservaciones(obs);

            DiagnosticoDAO dao = new DiagnosticoDAO();
            boolean exito = dao.guardarODiagnostico(diag);

            if (exito) {
                lblEstado.setForeground(new Color(0, 128, 0));
                lblEstado.setText("Diagnóstico registrado correctamente.");
            } else {
                lblEstado.setForeground(Color.RED);
                lblEstado.setText("Error al registrar diagnóstico - verifique si existe el ID de la cuenta.");
            }

        } catch (NumberFormatException e) {
            lblEstado.setText("El ID debe ser un número.");
        }
    }
}
class VerDiagnosticoCuentaFrame extends JFrame {

    private JTextField txtIdCuenta;
    private JTextArea txtResultado;

    public VerDiagnosticoCuentaFrame() {
        setTitle("Ver Diagnóstico de Cuenta");
        setSize(500, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Consultar Diagnóstico de una Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(70, 20, 360, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID de la cuenta:");
        lblId.setBounds(50, 70, 150, 20);
        add(lblId);

        txtIdCuenta = new JTextField();
        txtIdCuenta.setBounds(180, 70, 200, 25);
        add(txtIdCuenta);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(200, 110, 100, 30);
        add(btnBuscar);

        txtResultado = new JTextArea();
        txtResultado.setBounds(50, 160, 380, 120);
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        add(txtResultado);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(200, 290, 100, 30);
        add(btnCerrar);

        btnBuscar.addActionListener(e -> consultarDiagnostico());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void consultarDiagnostico() {
        txtResultado.setText("");
        try {
            int idCuenta = Integer.parseInt(txtIdCuenta.getText().trim());
            DiagnosticoDAO dao = new DiagnosticoDAO();
            List<Diagnostico> lista = dao.listarDiagnosticosPorCuenta(idCuenta);

            if (lista.isEmpty()) {
                txtResultado.setText("No hay diagnósticos registrados para esta cuenta.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Diagnostico d : lista) {
                    sb.append("Resultado del diagnóstico: ").append(d.getResultado()).append("\n")
                            .append("Password rotada: ").append(d.isCambioPassword() ? "Sí" : "No").append("\n")
                            .append("2FA en uso: ").append(d.isTiene2FA() ? "Sí" : "No").append("\n")
                            .append("Actividad sospechosa: ").append(d.isActividadSospechosa() ? "Sí" : "No").append("\n")
                            .append("Observaciones: ").append(d.getObservaciones()).append("\n----------------------------\n");
                }
                txtResultado.setText(sb.toString());
            }

        } catch (NumberFormatException e) {
            txtResultado.setText("El ID debe ser un número válido.");
        }
    }
}