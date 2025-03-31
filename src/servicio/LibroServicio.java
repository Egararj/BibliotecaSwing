package servicio;

import java.util.List;

import interfaces.ILibro;
import modelo.Libro;
import repositorio.LibroRepositorio;

public class LibroServicio implements ILibro {

	public LibroServicio() {
	}

	@Override
	public Libro agregarLibro(String isbn, String titulo, String autor, String editorial) {
		
		return null;
	}

	@Override
	public List<Libro> obtenerTodos() {
		
		LibroRepositorio libroRepositorio = new LibroRepositorio();
		List<Libro> libros = libroRepositorio.cargarLibros();
		libroRepositorio = null;
		return libros;
	}

}
