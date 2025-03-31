package repositorio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import modelo.Libro;

public class LibroRepositorio {
	private static final String ARCHIVO_LIBROS = "libros.dat";
	List<Libro>libros;

	public LibroRepositorio() {
		
	}
	
	public List<Libro> cargarLibros() {
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_LIBROS))){
			libros = (List<Libro>) ois.readObject();
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("No se encontraron libros previos, iniciando con una lista vacía.");
		}
		return libros;
	}

}
