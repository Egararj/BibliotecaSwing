package vista;

import java.io.IOException;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Libro;
import servicio.LibroServicio;
import utilidades.AgregarFichero;
import vista.swing.FrmBiblioteca;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		/*try {
			AgregarFichero.AgregarFichero();
		} catch (CamposVaciosException | IsbnException | IOException e) {
			System.out.println(e.getMessage());
		}*/
		LibroServicio libroServicio = new LibroServicio();
		for(Libro l:libroServicio.obtenerTodos()) {
			System.out.println(l);
		}
		FrmBiblioteca fbiblioteca = new FrmBiblioteca();
	}

}
