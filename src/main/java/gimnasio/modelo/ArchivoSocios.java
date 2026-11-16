package gimnasio.modelo;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoSocios {

    // Atributos solicitados en la consigna
    private final String rutaArchivo = "socios.csv";
    private List<Socio> listaSocios;

    public ArchivoSocios() {
        this.listaSocios = new ArrayList<>();
        cargarDesdeCSV();
    }

    // 1. Validar datos y añadir un nuevo registro en socios.csv mediante CSVWriter
    public void registrarSocio(Socio nuevoSocio) throws Exception {
        if (nuevoSocio == null || nuevoSocio.getDni() <= 0 || nuevoSocio.getNombre() == null || nuevoSocio.getNombre().trim().isEmpty()) {
            throw new Exception("Dato inválido: el DNI y el nombre son obligatorios."); // Puedes usar tu clase DatoInvalido
        }
        if (buscarSocio(nuevoSocio.getDni()) != null) {
            throw new Exception("Socio duplicado: ya existe un socio con el DNI " + nuevoSocio.getDni()); // Puedes usar tu clase SocioDuplicado
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
    public void modificarSocio(Socio socioModificado) throws Exception {
        boolean encontrado = false;
        for (int i = 0; i < listaSocios.size(); i++) {
            if (listaSocios.get(i).getDni() == socioModificado.getDni()) {
                listaSocios.set(i, socioModificado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("Socio no encontrado con DNI: " + socioModificado.getDni()); // Puedes usar SocioNoEncontrado
        }
        guardarEnCSV();
    }

    // 4. Quitar un socio del registro y actualizar socios.csv
    public void eliminarSocio(int dni) throws Exception {
        boolean eliminado = listaSocios.removeIf(s -> s.getDni() == dni);
        if (!eliminado) {
            throw new Exception("No se puede eliminar: socio no encontrado."); // Puedes usar SocioNoEncontrado
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
    public void actualizarPases(int dni, int cantidadPases) throws Exception {
        Socio socio = buscarSocio(dni);
        if (socio == null) {
            throw new Exception("Socio no encontrado para actualizar pases."); // Puedes usar SocioNoEncontrado
        }
        int nuevosPases = socio.getPasesDisponibles() + cantidadPases;
        if (nuevosPases < 0) {
            throw new Exception("El socio no tiene suficientes pases disponibles."); // Puedes usar DatoInvalido
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

                    listaSocios.add(new Socio(dni, nombre, valorCuota, pasesDisponibles));
                }
            }
        } catch (IOException | CsvValidationException | NumberFormatException e) {
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
                };
                writer.writeNext(fila);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }
}
