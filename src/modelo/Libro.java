package modelo;

import java.io.Serializable;
import java.time.LocalDate;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import utilidades.CompruebaISBN;

public class Libro implements Serializable{
	
	private String titulo,autor,editorial,isbn;
	private LocalDate fechaPrestamo, fechaDevolucion;
	private boolean prestado;
	private static final long serialVersionUID = 1L;

	public Libro() {
	}

	public Libro(String titulo, String autor, String editorial, String isbn) throws CamposVaciosException, IsbnException {
		super();
		this.setTitulo(titulo);
		this.setAutor(autor);
		this.setEditorial(editorial);
		this.setIsbn(isbn);
	}
	
	

	public Libro(String titulo, String autor, String editorial, String isbn, LocalDate fechaPrestamo,
			LocalDate fechaDevolucion, boolean prestado) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.isbn = isbn;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.prestado = prestado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws CamposVaciosException {
		if(this.titulo == null) {
			throw new CamposVaciosException();
		}
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) throws CamposVaciosException {
		if(this.autor == null) {
			throw new CamposVaciosException();
		}
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String aditorial) throws CamposVaciosException {
		if(this.editorial == null) {
			throw new CamposVaciosException();
		}
		this.editorial = aditorial;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws CamposVaciosException, IsbnException {
		if(this.isbn == null) {
			throw new CamposVaciosException();
		}
		boolean correcto = true;
		
		correcto = (CompruebaISBN.compruebaISBN(this.isbn));
		if(correcto) this.isbn = isbn;
		else {
			throw new IsbnException();
		}
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", aditorial=" + editorial + ", isbn=" + isbn
				+ ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion + ", prestado=" + prestado
				+ "]";
	}
	
	

	
}
