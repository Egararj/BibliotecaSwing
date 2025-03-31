package interfaces;

import java.util.List;

import modelo.Libro;

public interface ILibro {

	Libro agregarLibro(String isbn, String titulo, String autor, String editorial);
	
	List<Libro> obtenerTodos();
}
