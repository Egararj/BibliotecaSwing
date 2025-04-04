package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Libro;

public interface ILibro {

	Libro agregarLibro(String isbn, String titulo, String autor, String editorial) throws CamposVaciosException, IsbnException, FileNotFoundException, IOException;
	
	List<Libro> obtenerTodos();
	
	Libro editarLibro(String isbn);
	
}