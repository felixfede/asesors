package app;

import dao.ClienteDAO;
import modelos.Cliente;
import java.util.List;
import java.util.Scanner;
import dao.CuentaRRSSDAO;
import modelos.CuentaRRSS;
import dao.DiagnosticoDAO;
import modelos.Diagnostico;
import java.util.Set;
import java.util.HashSet;
import dao.UsuarioDAO;
import modelos.Usuario;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static Usuario usuarioLogueado = null;

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final CuentaRRSSDAO cuentaDAO = new CuentaRRSSDAO();
    private static final DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
    private static void iniciarSesion() {
        while (usuarioLogueado == null) {
            System.out.print("Ingrese su usuario: ");
            String username = scanner.nextLine();

            System.out.print("Ingrese su Contraseña: ");
            String password = scanner.nextLine();

            usuarioLogueado = usuarioDAO.login(username, password);

            if (usuarioLogueado == null) {
                System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.\n");
            }
        }

        System.out.println("Bienvenido, " + usuarioLogueado.getNombreUsuario()
                + " | Rol: " + usuarioLogueado.getRol() + "\n");
    }

    public static void main(String[] args) {
        iniciarSesion();

        int opcion;
        do {
            System.out.println("\n.:: Sistema ASESORS - Seguridad de Cuentas RRSS ::.");
            System.out.println("\n####################################################################");
            System.out.println("### Se recomienda listar los ID de cuentas al operar los menues  ###");
            System.out.println("####################################################################");
            System.out.println("\n1. Agregar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Gestión de cuentas de RRSS");
            System.out.println("5. Gestión de diagnósticos");
            System.out.println("6. Ver reporte de estado de seguridad por cliente");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> agregarCliente();
                case 2 -> listarClientes();
                case 3 -> eliminarCliente();
                case 4 -> menuCuentasRRSS();
                case 5 -> menuDiagnosticos();
                case 6 -> mostrarEstadoPorCliente();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void agregarCliente() {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo de contacto: ");
        String correo = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombreCliente(nombre);
        cliente.setCorreoContacto(correo);

        boolean resultado = clienteDAO.agregarCliente(cliente);
        System.out.println(resultado ? "Cliente agregado correctamente." : "Error al agregar cliente.");
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\nListado de clientes:");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombreCliente() + ", Correo: " + c.getCorreoContacto());
            }
        }
    }

    private static void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean eliminado = clienteDAO.eliminarCliente(id);
        System.out.println(eliminado ? "Cliente eliminado." : "No se pudo eliminar el cliente.");
    }

    private static void menuCuentasRRSS() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Cuentas de RRSS ---");
            System.out.println("1. Agregar cuenta a cliente");
            System.out.println("2. Listar cuentas de un cliente");
            System.out.println("3. Eliminar cuenta");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> agregarCuenta();
                case 2 -> listarCuentasPorCliente();
                case 3 -> eliminarCuenta();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void agregarCuenta() {
        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Plataforma (Facebook, Instagram, Twitter, LinkedIn,TikTok, otro.): ");
        String plataforma = scanner.nextLine();

        System.out.print("Nombre de la cuenta: ");
        String nombreCuenta = scanner.nextLine();

        CuentaRRSS cuenta = new CuentaRRSS();
        cuenta.setIdCliente(idCliente);
        cuenta.setPlataforma(plataforma);
        cuenta.setNombreCuenta(nombreCuenta);
        cuenta.setActivo(true);

        boolean resultado = cuentaDAO.agregarCuenta(cuenta);
        System.out.println(resultado ? "Cuenta agregada correctamente." : "Error al agregar cuenta.");
    }

    private static void listarCuentasPorCliente() {
        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("\nCuentas del cliente \"" + cliente.getNombreCliente() + "\":");

        List<CuentaRRSS> cuentas = cuentaDAO.listarCuentasPorCliente(idCliente);
        System.out.println("Se recuperaron " + cuentas.size() + " cuentas del cliente.");

        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas para este cliente.");
            return;
        }

        for (CuentaRRSS c : cuentas) {
            System.out.println("ID: " + c.getId() + ", Plataforma: " + c.getPlataforma()
                    + ", Nombre: " + c.getNombreCuenta() + ", Activa: " + (c.isActivo() ? "Sí" : "No"));
        }
    }


    private static void eliminarCuenta() {
        System.out.print("ID de la cuenta a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean eliminado = cuentaDAO.eliminarCuenta(id);
        System.out.println(eliminado ? "Cuenta eliminada." : "No se pudo eliminar la cuenta.");
    }

    private static void menuDiagnosticos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Diagnósticos ---");
            System.out.println("1. Registrar diagnóstico a cuenta");
            System.out.println("2. Ver diagnósticos de una cuenta");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> registrarDiagnostico();
                case 2 -> verDiagnosticosDeCuenta();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void registrarDiagnostico() {
        System.out.print("ID de la cuenta: ");
        int idCuenta = Integer.parseInt(scanner.nextLine());

        System.out.print("### ATENCION: Solo es admitido como respuesta las opciones mostradas entre parentesis ###");
        System.out.print("\n¿Se cambió la contraseña en los ultimos 180 dias? responda (si/no): ");
        boolean cambioPassword = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("¿La cuenta tiene 2FA activado (segundo factor de autenticacion - token)? responda (si/no): ");
        boolean tiene2FA = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("¿La cuenta presento actividad sospechosa? responda (si/no): ");
        boolean actividadSospechosa = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Indique un estado de condicion para la cuenta (segura / comprometida / indeterminado): ");
        String resultado = scanner.nextLine();

        System.out.print("Observaciones (campo libre para informar observaciones): ");
        String observaciones = scanner.nextLine();

        Diagnostico diag = new Diagnostico();
        diag.setIdCuenta(idCuenta);
        diag.setCambioPassword(cambioPassword);
        diag.setTiene2FA(tiene2FA);
        diag.setActividadSospechosa(actividadSospechosa);
        diag.setResultado(resultado);
        diag.setObservaciones(observaciones);
        diagnosticoDAO.guardarODiagnostico(diag); //lo uso para guardar o actualizar el diagnostico

        boolean ok = diagnosticoDAO.registrarDiagnostico(diag);
        System.out.println(ok ? "Diagnóstico registrado correctamente." : "Error al registrar diagnóstico.");
    }

    private static void verDiagnosticosDeCuenta() {
        System.out.print("Ingrese el ID de la cuenta del cliente: ");
        int idCuenta = Integer.parseInt(scanner.nextLine());

        CuentaRRSS cuenta = cuentaDAO.buscarPorId(idCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        Cliente cliente = clienteDAO.buscarPorId(cuenta.getIdCliente());
        String nombreCliente = (cliente != null) ? cliente.getNombreCliente() : "Desconocido";

        System.out.println("\nDiagnósticos para la cuenta:");
        System.out.println("Cuenta: \"" + cuenta.getNombreCuenta() + "\" | Plataforma: " + cuenta.getPlataforma());
        System.out.println("Cliente: " + nombreCliente);

        List<Diagnostico> lista = diagnosticoDAO.listarDiagnosticosPorCuenta(idCuenta);
        if (lista.isEmpty()) {
            System.out.println("No hay diagnósticos registrados para esta cuenta.");
        } else {
            System.out.println("\nListado de diagnósticos:");
            for (Diagnostico d : lista) {
                System.out.println("Resultado del diagnostico: " + d.getResultado()
                        + " | Password rotada: " + (d.isCambioPassword() ? "Sí" : "No")
                        + " | 2FA en uso: " + (d.isTiene2FA() ? "Sí" : "No")
                        + " | Actividad sospechosa: " + (d.isActividadSospechosa() ? "Sí" : "No")
                        + " | Observaciones: " + d.getObservaciones());

            }
        }
    }

    private static void mostrarEstadoPorCliente() {
        List<Cliente> clientes = clienteDAO.listarClientes();

        // Hago uso de un Set para evitar duplicados
        Set<Integer> procesados = new HashSet<>();

        for (Cliente cliente : clientes) {
            if (procesados.contains(cliente.getId())) continue;
            procesados.add(cliente.getId());

            List<CuentaRRSS> cuentas = cuentaDAO.listarCuentasPorCliente(cliente.getId());
            int totalCuentas = cuentas.size();
            int totalDiagnosticos = 0;
            int comprometidas = 0;
            int seguras = 0;

            for (CuentaRRSS cuenta : cuentas) {
                List<Diagnostico> diagnos = diagnosticoDAO.listarDiagnosticosPorCuenta(cuenta.getId());
                totalDiagnosticos += diagnos.size();

                if (!diagnos.isEmpty()) {
                    Diagnostico ultimo = diagnos.get(0);
                    String resultado = ultimo.getResultado();
                    if (resultado.equals("comprometida")) {
                        comprometidas++;
                    } else if (resultado.equals("segura")) {
                        seguras++;
                    }
                }
            }

            System.out.println("\nCliente: " + cliente.getNombreCliente());
            System.out.println("  Total cuentas: " + totalCuentas);
            System.out.println("  Diagnósticos realizados: " + totalDiagnosticos);
            System.out.println("  Comprometidas: " + comprometidas);
            System.out.println("  Seguras: " + seguras);
        }
    }

}
