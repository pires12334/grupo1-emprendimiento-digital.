package gimnasio;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import gimnasio.modelo.ArchivoAsistencias;
import gimnasio.modelo.ArchivoPagos;
import gimnasio.modelo.ArchivoSocios;
import gimnasio.modelo.DatoInvalido;
import gimnasio.modelo.Pago;
import gimnasio.modelo.Socio;
import gimnasio.modelo.SocioDuplicado;
import gimnasio.modelo.SocioNoEncontrado;

/**
 * Menú de consola que integra socios, pagos y asistencias.
 * Clase principal del proyecto (Run As → Java Application).
 * Responsable: Alan Joel Pires.
 */
public class MenuPrincipal {

    private final Scanner sc = new Scanner(System.in);
    private final ArchivoSocios socios;
    private final ArchivoPagos pagos = new ArchivoPagos();
    private final ArchivoAsistencias asistencias;

    public MenuPrincipal() throws IOException, CsvValidationException {
        socios = new ArchivoSocios();
        asistencias = new ArchivoAsistencias(socios);
    }

    public static void main(String[] args) {
        try {
            new MenuPrincipal().iniciar();
        } catch (IOException | CsvValidationException e) {
            System.out.println("No se pudieron cargar los datos: " + e.getMessage());
        }
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== GIMNASIO DIGITAL =====");
            System.out.println("1. Alta de socio");
            System.out.println("2. Buscar socio");
            System.out.println("3. Modificar socio");
            System.out.println("4. Baja de socio");
            System.out.println("5. Listar socios");
            System.out.println("6. Registrar pago");
            System.out.println("7. Ver pagos");
            System.out.println("8. Registrar ingreso al gimnasio");
            System.out.println("9. Historial de asistencias de un socio");
            System.out.println("10. Exportar socios a CSV");
            System.out.println("11. Importar socios desde CSV");
            System.out.println("0. Salir");
            opcion = leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaSocio();
                    case 2 -> System.out.println(socios.buscarSocio(leerEntero("DNI: ")));
                    case 3 -> modificarSocio();
                    case 4 -> bajaSocio();
                    case 5 -> listarSocios();
                    case 6 -> registrarPago();
                    case 7 -> verPagos();
                    case 8 -> registrarIngreso();
                    case 9 -> historial();
                    case 10 -> exportar();
                    case 11 -> importar();
                    case 0 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida");
                }
            } catch (DatoInvalido e) {
                System.out.println("Dato inválido: " + e.getMessage());
            } catch (SocioDuplicado e) {
                System.out.println("Socio duplicado: " + e.getMessage());
            } catch (SocioNoEncontrado e) {
                System.out.println("Socio no encontrado: " + e.getMessage());
            } catch (IOException | CsvValidationException e) {
                System.out.println("Error con el archivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido: " + e.getMessage());
            }
        } while (opcion != 0);
        sc.close();
    }

    // ---------- opciones del menú ----------

    private void altaSocio() throws DatoInvalido, SocioDuplicado, IOException {
        int dni = leerEntero("DNI: ");
        String nombre = leerTexto("Nombre: ");
        double cuota = leerDecimal("Valor de la cuota: ");
        int pases = leerEntero("Pases iniciales: ");
        LocalDate vence = leerFecha("Vencimiento (AAAA-MM-DD, Enter = dentro de un mes): ");
        socios.registrarSocio(new Socio(dni, nombre, cuota, pases, vence));
        System.out.println("Socio registrado.");
    }

    private void modificarSocio() throws SocioNoEncontrado, DatoInvalido, IOException {
        Socio actual = socios.buscarSocio(leerEntero("DNI del socio a modificar: "));
        System.out.println("Actual: " + actual);
        String nombre = leerTexto("Nuevo nombre (Enter = sin cambios): ");
        String cuota = leerTexto("Nueva cuota (Enter = sin cambios): ");

        Socio modificado = new Socio(actual.getDni(),
                nombre.isBlank() ? actual.getNombre() : nombre,
                cuota.isBlank() ? actual.getValorCuota() : convertirDecimal(cuota),
                actual.getPasesDisponibles(),
                actual.getFechaVencimiento());
        socios.modificarSocio(modificado);
        System.out.println("Socio modificado.");
    }

    private void bajaSocio() throws SocioNoEncontrado, IOException {
        socios.eliminarSocio(leerEntero("DNI del socio a eliminar: "));
        System.out.println("Socio eliminado.");
    }

    private void listarSocios() {
        List<Socio> lista = socios.listarSocios();
        if (lista.isEmpty()) {
            System.out.println("No hay socios registrados.");
        }
        for (Socio s : lista) {
            System.out.println(s);
        }
    }

    private void registrarPago() throws SocioNoEncontrado, DatoInvalido, IOException {
        Socio socio = socios.buscarSocio(leerEntero("DNI: "));
        int pases = leerEntero("Cantidad de pases que compra: ");
        Pago pago = new Pago(socio.getDni(), LocalDate.now(), socio.getValorCuota(), pases);
        pago.aplicarA(socio);
        pagos.guardarPago(pago);
        socios.guardarCambios();
        System.out.println("Pago registrado: " + pago);
        System.out.println("Ahora tiene " + socio.getPasesDisponibles() + " pases.");
    }

    private void verPagos() throws IOException, CsvValidationException {
        List<String[]> filas = pagos.leerPagos();
        if (filas.isEmpty()) {
            System.out.println("No hay pagos registrados.");
        }
        for (String[] fila : filas) {
            System.out.println(String.join(" | ", fila));
        }
    }

    private void registrarIngreso() throws SocioNoEncontrado, DatoInvalido, IOException {
        int quedan = asistencias.registrarIngreso(leerEntero("DNI: "));
        System.out.println("Ingreso registrado. Pases restantes: " + quedan);
    }

    private void historial() throws IOException {
        List<String> lineas = asistencias.historialDe(leerEntero("DNI: "));
        if (lineas.isEmpty()) {
            System.out.println("Sin asistencias registradas.");
        }
        lineas.forEach(System.out::println);
    }

    private void exportar() throws IOException {
        String ruta = leerTexto("Archivo destino (Enter = datos/exportacion_socios.csv): ");
        if (ruta.isBlank()) {
            ruta = "datos/exportacion_socios.csv";
        }
        System.out.println("Se exportaron " + socios.exportar(ruta) + " socios a " + ruta);
    }

    private void importar() throws IOException, CsvValidationException {
        String ruta = leerTexto("Archivo a importar: ");
        System.out.println("Se importaron " + socios.importar(ruta) + " socios nuevos.");
    }

    // ---------- lectura segura de datos por teclado ----------

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.hasNextLine() ? sc.nextLine().trim() : "0";
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            try {
                return convertirDecimal(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número (ej: 15000 o 15000.50).");
            }
        }
    }

    private double convertirDecimal(String texto) {
        return Double.parseDouble(texto.replace(",", "."));
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (texto.isBlank()) {
                return LocalDate.now().plusMonths(1);
            }
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Usá AAAA-MM-DD.");
            }
        }
    }
}
