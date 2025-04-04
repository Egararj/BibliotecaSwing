package repositorio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
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
	
	public Libro editarLibro(Libro libro, String fechaDevolucion, boolean prestado) {
		
		boolean prestadoLibro = libro.isPrestado();		
		if(prestadoLibro == false && prestado == true) {
			LocalDate diezDias = LocalDate.now().plusDays(10);
			libro.setFechaDevolucion(diezDias);
			libro.setPrestado(true);
		}else if(prestadoLibro == true && prestado == false) {
			libro.setFechaDevolucion(null);
			libro.setPrestado(false);
		}else if(prestadoLibro == true && prestado == true) {
			LocalDate tresDias = libro.getFechaDevolucion().plusDays(3);
			libro.setFechaDevolucion(tresDias);
		}
		
		
		return libro;
	}
	
	public Libro buscarLibro(List<Libro> libros, String isbn) {
		Libro libro = null;
		String isbnLista;
		for(Libro l:libros) {
			isbnLista = l.getIsbn();
			if(isbn.equals(isbnLista)) {
				libro = l;
				break;
			}
		}
		
		return libro;
	}
	
	public void guardarLibroEditado(List<Libro> libros, Libro libro) {
		String isbnLibro = libro.getIsbn();
		String isbnLista;
		int contador = 0;
		for(Libro l:libros) {
			isbnLista = l.getIsbn();
			if(isbnLibro.equals(isbnLista)) {
				libros.set(contador, libro);
				break;
			}
			contador++;
		}
		
	}
}
