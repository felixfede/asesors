package gui;

import dao.ClienteDAO;
import dao.CuentaRRSSDAO;
import dao.DiagnosticoDAO;
import modelos.Cliente;
import modelos.CuentaRRSS;
import modelos.Diagnostico;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteGeneralFrame extends JFrame {

    private JTextPane txtReporte;
    private JTextField txtIdCliente;

    public ReporteGeneralFrame() {
        setTitle("Reporte General de Seguridad");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Estado de Seguridad de Cuentas por Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(50, 20, 600, 25);
        add(lblTitulo);

        JLabel lblId = new JLabel("ID del cliente:");
        lblId.setBounds(60, 60, 120, 25);
        add(lblId);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(180, 60, 100, 25);
        add(txtIdCliente);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(300, 60, 100, 25);
        add(btnBuscar);

        JButton btnVerTodo = new JButton("Ver todos");
        btnVerTodo.setBounds(420, 60, 120, 25);
        add(btnVerTodo);

        txtReporte = new JTextPane();
        txtReporte.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtReporte);
        scrollPane.setBounds(50, 100, 600, 330);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(300, 440, 100, 30);
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());
        btnBuscar.addActionListener(e -> generarReporteFiltrado());
        btnVerTodo.addActionListener(e -> generarReporteCompleto());
    }

    private void generarReporteFiltrado() {
        ClienteDAO clienteDAO = new ClienteDAO();
        CuentaRRSSDAO cuentaDAO = new CuentaRRSSDAO();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();

        StyledDocument doc = txtReporte.getStyledDocument();
        doc.removeUndoableEditListener(null);
        txtReporte.setText("");
        StyleContext sc = new StyleContext();

        Style verde = sc.addStyle("verde", null);
        StyleConstants.setForeground(verde, new Color(0, 128, 0));

        Style rojo = sc.addStyle("rojo", null);
        StyleConstants.setForeground(rojo, Color.RED);

        Style amarillo = sc.addStyle("amarillo", null);
        StyleConstants.setForeground(amarillo, Color.ORANGE.darker());

        Style normal = sc.addStyle("normal", null);
        StyleConstants.setForeground(normal, Color.BLACK);

        try {
            int idFiltro = Integer.parseInt(txtIdCliente.getText().trim());
            Cliente cliente = clienteDAO.buscarPorId(idFiltro);
            if (cliente == null) {
                try {
                    doc.insertString(doc.getLength(), "No se encontró un cliente con ese ID.\n", rojo);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                return;
            }
            List<Cliente> unico = new ArrayList<>();
            unico.add(cliente);
            generarParaClientes(unico, doc, sc);

        } catch (NumberFormatException e) {
            try {
                doc.insertString(doc.getLength(), "ID de cliente inválido.\n", rojo);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void generarReporteCompleto() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.listarClientes();

        StyledDocument doc = txtReporte.getStyledDocument();
        txtReporte.setText("");
        StyleContext sc = new StyleContext();

        Style verde = sc.addStyle("verde", null);
        StyleConstants.setForeground(verde, new Color(0, 128, 0));

        Style rojo = sc.addStyle("rojo", null);
        StyleConstants.setForeground(rojo, Color.RED);

        Style amarillo = sc.addStyle("amarillo", null);
        StyleConstants.setForeground(amarillo, Color.ORANGE.darker());

        Style normal = sc.addStyle("normal", null);
        StyleConstants.setForeground(normal, Color.BLACK);

        generarParaClientes(clientes, doc, sc);
    }

    private void generarParaClientes(List<Cliente> clientes, StyledDocument doc, StyleContext sc) {
        CuentaRRSSDAO cuentaDAO = new CuentaRRSSDAO();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();

        Style verde = sc.getStyle("verde");
        Style rojo = sc.getStyle("rojo");
        Style amarillo = sc.getStyle("amarillo");
        Style normal = sc.getStyle("normal");

        try {
            for (Cliente cliente : clientes) {
                List<CuentaRRSS> cuentas = cuentaDAO.listarCuentasPorCliente(cliente.getId());
                int total = cuentas.size();
                int seguras = 0;
                int comprometidas = 0;
                int sinDiagnostico = 0;

                ArrayList<String> cuentasComprometidas = new ArrayList<>();

                for (CuentaRRSS cuenta : cuentas) {
                    List<Diagnostico> diags = diagnosticoDAO.listarDiagnosticosPorCuenta(cuenta.getId());
                    if (!diags.isEmpty()) {
                        Diagnostico ult = diags.get(0);
                        if (ult.getResultado().equals("segura")) {
                            seguras++;
                        } else if (ult.getResultado().equals("comprometida")) {
                            comprometidas++;
                            cuentasComprometidas.add(cuenta.getNombreCuenta());
                        }
                    } else {
                        sinDiagnostico++;
                    }
                }

                doc.insertString(doc.getLength(), "Cliente: " + cliente.getNombreCliente() + "\n", normal);
                doc.insertString(doc.getLength(), "Total de cuentas: " + total + "\n", normal);
                doc.insertString(doc.getLength(), "Cuentas marcadas como seguras: " + seguras + "\n", verde);
                doc.insertString(doc.getLength(), "Cuentas comprometidas: " + comprometidas + "\n", rojo);
                doc.insertString(doc.getLength(), "Cuentas sin diagnóstico: " + sinDiagnostico + "\n", amarillo);

                if (!cuentasComprometidas.isEmpty()) {
                    doc.insertString(doc.getLength(), " - Detalle cuentas comprometidas:\n", rojo);
                    for (String nombre : cuentasComprometidas) {
                        doc.insertString(doc.getLength(), "   * " + nombre + "\n", rojo);
                    }
                }

                doc.insertString(doc.getLength(), "\n-----------------------------------------------\n\n", normal);
            }
            txtReporte.setCaretPosition(0);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}