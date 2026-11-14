package modelo;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.util.ArrayList;
import java.util.List;
public class ArchivoPagos {

	public void guardarPago(Pago pago) throws IOException {

	    try (CSVWriter writer = new CSVWriter(new FileWriter("datos/pagos.csv", true))) {

	        String[] datos = {
	            pago.getDniSocio(),
	            pago.getFecha().toString(),
	            String.valueOf(pago.getMonto()),
	            pago.getNuevoVencimiento().toString()
	        };

	        writer.writeNext(datos);
	    }
	}
	public List<String[]> leerPagos() throws IOException, CsvValidationException {

	    List<String[]> pagos = new ArrayList<>();

	    try (CSVReader reader = new CSVReader(new FileReader("datos/pagos.csv"))) {

	        String[] fila;

	        while ((fila = reader.readNext()) != null) {
	            pagos.add(fila);
	        }
	    }

	    return pagos;
	}
}