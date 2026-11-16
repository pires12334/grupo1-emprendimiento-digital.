package gimnasio.modelo;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoSocios {

    // Archivo donde se guardan los socios y lista en memoria
    private final String rutaArchivo = "socios.csv";
    private List<Socio> listaSocios;

    public ArchivoSocios() {
        this.listaSocios = new ArrayList<>();
        cargarDesdeCSV();
    }

    // 1. Validar datos y añadir un nuevo registro en socios.csv mediante CSVWriter
    public void registrarSocio(Socio nuevoSocio) throws DatoInvalido, SocioDuplicado {
        if (nuevoSocio == null || nuevoSocio.getDni() <= 0 || nuevoSocio.getNombre() == null || nuevoSocio.getNombre().trim().isEmpty()) {
            throw new DatoInvalido("El DNI y el nombre son obligatorios.");
        }
        if (buscarSocio(nuevoSocio.getDni()) != null) {
            throw new SocioDuplicado("Ya existe un socio con el DNI " + nuevoSocio.getDni());
        }

        listaSocios.add(nuevoSocio);
        guardarEnCSV();
    }

    // 2. Leer socios.csv mediante CSVReader y buscar un socio por su número (DNI)
    public Socio buscarSocio(int dni) {
        cargarDesdeCSV(); // Asegura leer los datos actualizados desde el archivo
        for (Socio s : listaSocios) {
            if (s.getDni() == dni) {
                return s;
            }
        }
        return null;
    }

    // 3. Actualizar los datos de un cliente existente y guardar los cambios en el archivo
    public void modificarSocio(Socio socioModificado) throws SocioNoEncontrado {
        boolean encontrado = false;
        for (int i = 0; i < listaSocios.size(); i++) {
            if (listaSocios.get(i).getDni() == socioModificado.getDni()) {
                listaSocios.set(i, socioModificado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new SocioNoEncontrado("No existe un socio con DNI " + socioModificado.getDni());
        }
        guardarEnCSV();
    }

    // 4. Quitar un socio del registro y actualizar socios.csv
    public void eliminarSocio(int dni) throws SocioNoEncontrado {
        boolean eliminado = listaSocios.removeIf(s -> s.getDni() == dni);
        if (!eliminado) {
            throw new SocioNoEncontrado("No existe un socio con DNI " + dni);
        }
        guardarEnCSV();
    }

    // 5. Consultar y mostrar por consola todo el padrón registrado
    public void listarSocios() {
        cargarDesdeCSV();
        if (listaSocios.isEmpty()) {
            System.out.println("No hay socios registrados en el padrón.");
            return;
        }
        for (Socio s : listaSocios) {
            System.out.println(s.toString());
        }
    }

    // 6. Sumar o descontar pases disponibles y persistir el cambio en disco
    public void actualizarPases(int dni, int cantidadPases) throws SocioNoEncontrado, DatoInvalido {
        Socio socio = buscarSocio(dni);
        if (socio == null) {
            throw new SocioNoEncontrado("No existe un socio con DNI " + dni);
        }
        int nuevosPases = socio.getPasesDisponibles() + cantidadPases;
        if (nuevosPases < 0) {
            throw new DatoInvalido("El socio no tiene suficientes pases disponibles.");
        }
        socio.setPasesDisponibles(nuevosPases);
        modificarSocio(socio);
    }

    // 7. Sincronizar la colección de objetos Socio desde el archivo físico usando CSVReader
    public void cargarDesdeCSV() {
        listaSocios.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo aún no existe, la lista inicia vacía
        }

        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] fila;
            while ((fila = reader.readNext()) != null) {
                if (fila.length == 5) {
                    int dni = Integer.parseInt(fila[0]);
                    String nombre = fila[1];
                    double valorCuota = Double.parseDouble(fila[2]);
                    int pasesDisponibles = Integer.parseInt(fila[3]);
                    LocalDate fechaVencimiento = LocalDate.parse(fila[4]);

                    listaSocios.add(new Socio(dni, nombre, valorCuota, pasesDisponibles, fechaVencimiento));
                }
            }
        } catch (IOException | CsvValidationException | NumberFormatException | DateTimeParseException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    // 8. Persistir toda la colección en socios.csv usando CSVWriter
    public void guardarEnCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivo))) {
            for (Socio s : listaSocios) {
                String[] fila = {
                    String.valueOf(s.getDni()),
                    s.getNombre(),
                    String.valueOf(s.getValorCuota()),
                    String.valueOf(s.getPasesDisponibles()),
                    String.valueOf(s.getFechaVencimiento())
                };
                writer.writeNext(fila);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    // 9. Importar socios desde otro archivo CSV (por ejemplo, una planilla vieja del gimnasio).
    //    Cada fila válida se registra; las filas con error o DNI repetido se saltean y se avisa.
    public int importarSocios(String rutaImportar) throws IOException, CsvValidationException {
        int importados = 0;
        try (CSVReader reader = new CSVReader(new FileReader(rutaImportar))) {
            String[] fila;
            while ((fila = reader.readNext()) != null) {
                try {
                    Socio socio = new Socio(Integer.parseInt(fila[0]), fila[1],
                            Double.parseDouble(fila[2]), Integer.parseInt(fila[3]),
                            LocalDate.parse(fila[4]));
                    registrarSocio(socio);
                    importados++;
                } catch (DatoInvalido | SocioDuplicado e) {
                    System.out.println("Fila salteada: " + e.getMessage());
                } catch (RuntimeException e) {
                    System.out.println("Fila con formato incorrecto salteada.");
                }
            }
        }
        return importados;
    }

    // 10. Exportar a un CSV los socios que se quedaron sin pases
    public int exportarSinPases(String rutaExportar) throws IOException {
        cargarDesdeCSV();
        int exportados = 0;
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaExportar))) {
            for (Socio s : listaSocios) {
                if (s.getPasesDisponibles() == 0) {
                    writer.writeNext(new String[] {
                        String.valueOf(s.getDni()),
                        s.getNombre(),
                        String.valueOf(s.getValorCuota()),
                        String.valueOf(s.getPasesDisponibles()),
                        String.valueOf(s.getFechaVencimiento())
                    });
                    exportados++;
                }
            }
        }
        return exportados;
    }
}
