package utilidades;

import java.util.ArrayList;
import java.util.List;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;


import modelo.Libro;

public class AgregarFichero {
	
	public static void AgregarFichero() throws CamposVaciosException, IsbnException, IOException {
		
		List<Libro> libros = new ArrayList<Libro>();
		libros.add(new Libro("9788466320535", "Cien años de soledad", "Gabriel García Márquez","Editorial Sudamericana"));
		libros.add(new Libro("9788408167877", "El Principito", "Antoine de Saint-Exupéry", "Editorial Salamandra"));
		libros.add(new Libro("9788497592208", "1984", "George Orwell", "Editorial Debolsillo"));
		Libro libro = new Libro("9788420471839", "La sombra del viento", "Carlos Ruiz Zafón", "Editorial Planeta", LocalDate.of(2025, 03, 27), LocalDate.of(2025, 04, 17), false);
		libros.add(libro);
		libros.add(new Libro("9788497593793", "Orgullo y prejuicio", "Jane Austen", "Editorial Debolsillo"));
		libros.add(new Libro("9788408139249", "El nombre del viento", "Patrick Rothfuss", "Editorial Plaza & Janés"));
		libros.add(new Libro("9788497592437", "Crónica de una muerte anunciada", "Gabriel García Márquez","Editorial Debolsillo"));
		libros.add(new Libro("9788401328510", "Los pilares de la Tierra", "Ken Follett", "Editorial Debolsillo"));
		
		FileOutputStream fos = new FileOutputStream("libros.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(libros);
		oos.close();
	}

}
