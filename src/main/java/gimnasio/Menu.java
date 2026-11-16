package gimnasio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import gimnasio.archivos.RegistroAsistencias;
import gimnasio.modelo.ArchivoPagos;
import gimnasio.modelo.ArchivoSocios;
import gimnasio.modelo.DatoInvalido;
import gimnasio.modelo.Pago;
import gimnasio.modelo.Socio;

/**
 * Menú principal del sistema Gimnasio Digital.
 * Une los tres módulos: socios (Mateo), pagos (Diego) y asistencias (Alan).
 */
public class Menu {

    // Objetos que manejan cada archivo
    private static Scanner teclado = new Scanner(System.in);
    private static ArchivoSocios archivoSocios = new ArchivoSocios();
    private static ArchivoPagos archivoPagos = new ArchivoPagos();
    private static RegistroAsistencias asistencias = new RegistroAsistencias("asistencias.txt");

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1: altaSocio(); break;
                case 2: buscarSocio(); break;
                case 3: archivoSocios.listarSocios(); break;
                case 4: modificarSocio(); break;
                case 5: bajaSocio(); break;
                case 6: registrarPago(); break;
                case 7: registrarAsistencia(); break;
                case 8: verAsistencias(); break;
                case 9: importarSocios(); break;
                case 10: exportarSinPases(); break;
                case 0: System.out.println("¡Hasta luego!"); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        teclado.close();
    }

    private static void mostrarOpciones() {
        System.out.println("\n===== GIMNASIO DIGITAL =====");
        System.out.println("1. Alta de socio");
        System.out.println("2. Buscar socio");
        System.out.println("3. Listar socios");
        System.out.println("4. Modificar socio");
        System.out.println("5. Baja de socio");
        System.out.println("6. Registrar pago");
        System.out.println("7. Registrar asistencia");
        System.out.println("8. Ver asistencias de un socio");
        System.out.println("9. Importar socios desde un CSV");
        System.out.println("10. Exportar socios sin pases a CSV");
        System.out.println("0. Salir");
    }

    // ---------- Socios (módulo de Mateo) ----------

    private static void altaSocio() {
        int dni = leerEntero("DNI: ");
        System.out.print("Nombre y apellido: ");
        String nombre = teclado.nextLine();
        double cuota = leerDecimal("Valor de la cuota: $");
        int pases = leerEntero("Pases iniciales: ");
        // Un socio nuevo queda pago por un mes desde hoy
        Socio nuevo = new Socio(dni, nombre, cuota, pases, LocalDate.now().plusMonths(1));
        try {
            archivoSocios.registrarSocio(nuevo);
            System.out.println("Socio registrado.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void buscarSocio() {
        Socio socio = archivoSocios.buscarSocio(leerEntero("DNI: "));
        if (socio == null) {
            System.out.println("No existe un socio con ese DNI.");
        } else {
            System.out.println(socio);
        }
    }

    private static void modificarSocio() {
        Socio socio = archivoSocios.buscarSocio(leerEntero("DNI del socio a modificar: "));
        if (socio == null) {
            System.out.println("No existe un socio con ese DNI.");
            return;
        }
        System.out.print("Nuevo nombre (Enter para dejar '" + socio.getNombre() + "'): ");
        String nombre = teclado.nextLine();
        if (!nombre.trim().isEmpty()) {
            socio.setNombre(nombre);
        }
        socio.setValorCuota(leerDecimal("Nuevo valor de cuota: $"));
        try {
            archivoSocios.modificarSocio(socio);
            System.out.println("Socio modificado.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void bajaSocio() {
        try {
            archivoSocios.eliminarSocio(leerEntero("DNI del socio a dar de baja: "));
            System.out.println("Socio dado de baja.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void importarSocios() {
        System.out.print("Ruta del CSV a importar (ej: importar.csv): ");
        String ruta = teclado.nextLine();
        try {
            int cantidad = archivoSocios.importarSocios(ruta);
            System.out.println("Se importaron " + cantidad + " socios.");
        } catch (Exception e) {
            System.out.println("ERROR al importar: " + e.getMessage());
        }
    }

    private static void exportarSinPases() {
        try {
            int cantidad = archivoSocios.exportarSinPases("socios_sin_pases.csv");
            System.out.println("Se exportaron " + cantidad + " socios a socios_sin_pases.csv");
        } catch (Exception e) {
            System.out.println("ERROR al exportar: " + e.getMessage());
        }
    }

    // ---------- Pagos (módulo de Diego) ----------

    private static void registrarPago() {
        Socio socio = archivoSocios.buscarSocio(leerEntero("DNI: "));
        if (socio == null) {
            System.out.println("No existe un socio con ese DNI.");
            return;
        }
        int pases = leerEntero("Cantidad de pases que compra: ");
        double monto = socio.getValorCuota();
        System.out.println("Monto a cobrar: $" + monto);
        try {
            Pago pago = new Pago(String.valueOf(socio.getDni()), LocalDate.now(), monto);
            pago.actualizarVencimiento(socio);                  // Diego: actualiza la fecha de vencimiento
            archivoPagos.guardarPago(pago);                     // Diego: guarda en movimientos.csv
            archivoSocios.modificarSocio(socio);                // guarda la nueva fecha en socios.csv
            archivoSocios.actualizarPases(socio.getDni(), pases); // suma los pases comprados
            System.out.println("Pago registrado. Se acreditaron " + pases + " pases.");
        } catch (DatoInvalido e) {
            System.out.println("ERROR en el pago: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // ---------- Asistencias (módulo de Alan) ----------

    private static void registrarAsistencia() {
        Socio socio = archivoSocios.buscarSocio(leerEntero("DNI: "));
        if (socio == null) {
            System.out.println("No existe un socio con ese DNI.");
            return;
        }
        try {
            asistencias.registrarIngreso(socio);   // valida, anota en asistencias.txt y descuenta el pase
            archivoSocios.modificarSocio(socio);   // guarda los pases nuevos en socios.csv
            System.out.println("Ingreso registrado: " + socio.getNombre()
                    + ". Pases restantes: " + socio.getPasesDisponibles());
        } catch (DatoInvalido e) {
            System.out.println("No puede ingresar: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void verAsistencias() {
        int dni = leerEntero("DNI: ");
        try {
            List<String> ingresos = asistencias.ingresosDe(dni);
            if (ingresos.isEmpty()) {
                System.out.println("Ese socio no tiene ingresos registrados.");
            }
            for (String linea : ingresos) {
                System.out.println(linea);
            }
        } catch (Exception e) {
            System.out.println("ERROR al leer asistencias: " + e.getMessage());
        }
    }

    // ---------- Lectura del teclado ----------

    // Pide un número entero; si el usuario escribe letras, avisa y lo vuelve a pedir
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }

    // Igual que leerEntero, pero para montos con decimales (acepta coma o punto)
    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(teclado.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un monto válido.");
            }
        }
    }
}
