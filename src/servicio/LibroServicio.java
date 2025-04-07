package servicio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import interfaces.ILibro;
import modelo.Libro;
import repositorio.LibroRepositorio;

public class LibroServicio implements ILibro {

	public LibroServicio() {
	}

	@Override
	public Libro agregarLibro(String isbn, String titulo, String autor, String editorial) throws CamposVaciosException, IsbnException, FileNotFoundException, IOException {
		
		Libro libro = new Libro(isbn,titulo,autor,editorial);
		LibroRepositorio libroRepositorio = new LibroRepositorio();
		libroRepositorio.agregarLibro(libro);
		
		return libro;
	}

	@Override
	public List<Libro> obtenerTodos() {
		
		LibroRepositorio libroRepositorio = new LibroRepositorio();
		List<Libro> libros = libroRepositorio.cargarLibros();
		libroRepositorio = null;
		return libros;
	}

	@Override
	public Libro editarLibro(String isbn, String fechaDevolucion, boolean prestado) throws FileNotFoundException, IOException {
		
		LibroRepositorio libroRepositorio = new LibroRepositorio();
		List<Libro> libros = libroRepositorio.cargarLibros();
		Libro libro = libroRepositorio.buscarLibro(libros, isbn);
		libro = libroRepositorio.editarLibro(libro, fechaDevolucion, prestado);
		libroRepositorio.guardarLibroEditado(libros, libro);
		libroRepositorio.guardarLibros();
		
		return null;
	}

	@Override
	public Libro borrarLibro(int puntero) throws FileNotFoundException, IOException {
		
		LibroRepositorio libroRepositorio = new LibroRepositorio();
		List<Libro> libros = libroRepositorio.cargarLibros();
		libros.remove(puntero);
		libroRepositorio.guardarLibros();
		return null;
	}

}
