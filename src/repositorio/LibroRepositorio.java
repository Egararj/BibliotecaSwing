package repositorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public void guardarLibros() throws FileNotFoundException, IOException {
		
		ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream("libros.dat"));
		oos.writeObject(libros);
		oos.close();
		
	}

	public Libro agregarLibro(Libro libro) throws FileNotFoundException, IOException{
		
		cargarLibros();
		libros.add(libro);
		guardarLibros();
		
		return libro;
	}
}
