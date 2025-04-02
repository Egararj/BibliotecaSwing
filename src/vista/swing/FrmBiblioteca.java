package vista.swing;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Libro;
import servicio.LibroServicio;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FrmBiblioteca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel, panelLibros, panelMantenimientoLibro,panelNavegador;
	private ImageIcon nuevo, editar, borrar, guardar, deshacer, primero, izquierda, derecha, ultimo;
	private JButton btnNuevo, btnEditar, btnBorrar, btnGuardar, btnDeshacer;
	private JLabel lblIdLibro, lblTitulo, lblAutor, lblEditorial, lblIsbn, lblFecha, lblFecha2;
	private JTextField textIdLibro, textTitulo, textAutor, textEditorial, textIsbn, textFecha;
	private JCheckBox chcPrestado;
	private JButton btnPrimero, btnIzquierda, btnDerecha, btnFinal;
	private boolean b, libroNuevo;
	private int puntero, tamaño;
	List<Libro>libros= new ArrayList<Libro>();

	public FrmBiblioteca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 621);
		puntero = 0;
		b = true;
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		
		
		definirVentana();
		definirEventos();
		
		LibroServicio libroServicio= new LibroServicio();
		
		try {
			libros = libroServicio.obtenerTodos();
			libroServicio=null;
		}catch (Exception e) {
			System.out.println("Error en libro servicio frm");
		}
		
		habilitarPanelDeLibros(!b);
		habilitarPanelDeMantenimiento(b);
		habilitarPanelNavegador(b);
		limpiarCajasDeTexto();
		mostrarLibro(puntero);
		this.setVisible(true);
	}



	private void mostrarLibro(int puntero) {

		Libro libro = libros.get(puntero);
		textAutor.setText(libro.getAutor());
		textTitulo.setText(libro.getTitulo());
		textEditorial.setText(libro.getEditorial());
		if(libro.getFechaDevolucion() != null)
		textFecha.setText(libro.getFechaDevolucion().toString());
		textIsbn.setText(libro.getIsbn());
		if(libro.isPrestado())
		chcPrestado.setSelected(true);
		else
		chcPrestado.setSelected(false);
		
	}



	private void limpiarCajasDeTexto() {

		textIdLibro.setText("");
		textTitulo.setText("");
		textAutor.setText("");
		textEditorial.setText("");
		textIsbn.setText("");
		textFecha.setText("");
		chcPrestado.setSelected(false);
		
	}


	private void habilitarPanelNavegador(boolean b) {
		
		btnPrimero.setEnabled(b);
		btnIzquierda.setEnabled(b);
		btnDerecha.setEnabled(b);
		btnFinal.setEnabled(b);
		
	}


	private void habilitarPanelDeMantenimiento(boolean b) {
		
		btnNuevo.setEnabled(b);
		btnEditar.setEnabled(b);
		btnBorrar.setEnabled(b);
		btnGuardar.setEnabled(!b);
		btnDeshacer.setEnabled(!b);
		
	}


	private void habilitarPanelDeLibros(boolean b) {

		textIdLibro.setEditable(b);
		textTitulo.setEditable(b);
		textAutor.setEditable(b);
		textEditorial.setEditable(b);
		textIsbn.setEditable(b);
		textFecha.setEditable(b);
		chcPrestado.setEnabled(b);
		
	}


	private void definirEventos() {
		
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				puntero = 0;
				mostrarLibro(puntero);
			}
		});
		
		btnIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(puntero - 1 >= 0) {
					puntero--;
					mostrarLibro(puntero);
				}
				
			}
		});
		
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tamaño = libros.size();
				if(puntero + 1 < tamaño) {
					puntero++;
					mostrarLibro(puntero);
				}
				
			}
		});
		
		btnFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tamaño = libros.size();
				puntero = tamaño-1;
				mostrarLibro(puntero);
				
			}
		});
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				libroNuevo = true;
				limpiarCajasDeTexto();
				habilitarPanelNavegador(false);
				habilitarPanelDeMantenimiento(false);
				habilitarPanelDeLibros(true);
				
			}
		});
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				libroNuevo = false;
				habilitarPanelNavegador(false);
				habilitarPanelDeMantenimiento(false);
				habilitarPanelDeLibros(true);

			}
		});
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(libroNuevo) {
					LibroServicio libroServicio = new LibroServicio();
					try {
						libroServicio.agregarLibro(textIsbn.getText(), textTitulo.getText(), textAutor.getText(), textEditorial.getText());
					} catch (CamposVaciosException | IsbnException | IOException e1) {
						System.out.println(e1.getMessage());
					}
					habilitarPanelNavegador(true);
					habilitarPanelDeMantenimiento(true);
					
					try {
						libros = libroServicio.obtenerTodos();
						libroServicio=null;
					}catch (Exception e1) {
						System.out.println("Error en libro servicio frm");
						habilitarPanelNavegador(true);
						habilitarPanelDeMantenimiento(true);
						habilitarPanelDeLibros(false);
						puntero = 0;
						mostrarLibro(puntero);
					}
					puntero = 0;
					mostrarLibro(puntero);

				}else{
					
				}
				
			}
		});
		
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				habilitarPanelNavegador(true);
				habilitarPanelDeMantenimiento(true);
				habilitarPanelDeLibros(false);
				puntero = 0;
				mostrarLibro(puntero);
				
			}
		});
	}


	private void definirVentana() {
		
		panelMantenimientoLibro = new JPanel();
		panelMantenimientoLibro.setBounds(42, 29, 503, 110);
		panel.add(panelMantenimientoLibro);
		panelMantenimientoLibro.setLayout(null);
		
		nuevo = new ImageIcon("imagenes/botonAgregar.jpg");
		btnNuevo = new JButton(nuevo);
		btnNuevo.setBounds(10, 11, 88, 84);
		panelMantenimientoLibro.add(btnNuevo);
		
		editar = new ImageIcon("imagenes/botonEditar.jpg");
		btnEditar = new JButton(editar);
		btnEditar.setBounds(108, 11, 88, 84);
		panelMantenimientoLibro.add(btnEditar);
		
		borrar = new ImageIcon("imagenes/borrar.jpg");
		btnBorrar = new JButton(borrar);
		btnBorrar.setBounds(206, 11, 88, 83);
		panelMantenimientoLibro.add(btnBorrar);
		
		guardar = new ImageIcon("imagenes/botonGuardar.jpg");
		btnGuardar = new JButton(guardar);
		btnGuardar.setBounds(304, 11, 88, 83);
		btnGuardar.setEnabled(false);
		panelMantenimientoLibro.add(btnGuardar);
		
		deshacer = new ImageIcon("imagenes/botonDeshacer.jpg");
		btnDeshacer = new JButton(deshacer);
		btnDeshacer.setBounds(402, 11, 88, 83);
		btnDeshacer.setEnabled(false);
		panelMantenimientoLibro.add(btnDeshacer);
		
		panelLibros = new JPanel();
		panelLibros.setBounds(42, 161, 503, 224);
		panel.add(panelLibros);
		panelLibros.setLayout(null);
		
		lblIdLibro = new JLabel("Id Libro:");
		lblIdLibro.setBounds(10, 11, 88, 14);
		panelLibros.add(lblIdLibro);
		
		textIdLibro = new JTextField();
		textIdLibro.setBounds(108, 8, 86, 20);
		panelLibros.add(textIdLibro);
		textIdLibro.setColumns(10);
		
		lblTitulo = new JLabel("Título:");
		lblTitulo.setBounds(10, 42, 88, 14);
		panelLibros.add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setBounds(108, 39, 251, 20);
		panelLibros.add(textTitulo);
		textTitulo.setColumns(10);
		
		lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 73, 46, 14);
		panelLibros.add(lblAutor);
		
		textAutor = new JTextField();
		textAutor.setBounds(108, 70, 251, 20);
		panelLibros.add(textAutor);
		textAutor.setColumns(10);
		
		lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(10, 104, 88, 14);
		panelLibros.add(lblEditorial);
		
		textEditorial = new JTextField();
		textEditorial.setBounds(108, 101, 251, 20);
		panelLibros.add(textEditorial);
		textEditorial.setColumns(10);
		
		lblIsbn = new JLabel("Isbn:");
		lblIsbn.setBounds(10, 135, 46, 14);
		panelLibros.add(lblIsbn);
		
		textIsbn = new JTextField();
		textIsbn.setBounds(108, 132, 251, 20);
		panelLibros.add(textIsbn);
		textIsbn.setColumns(10);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 166, 46, 14);
		panelLibros.add(lblFecha);
		
		textFecha = new JTextField();
		textFecha.setBounds(108, 163, 133, 20);
		panelLibros.add(textFecha);
		textFecha.setColumns(10);
		
		lblFecha2 = new JLabel("aaaa-mm-dd");
		lblFecha2.setBounds(251, 166, 108, 14);
		panelLibros.add(lblFecha2);
		
		chcPrestado = new JCheckBox("Prestado");
		chcPrestado.setBounds(1, 187, 97, 23);
		panelLibros.add(chcPrestado);
		
		panelNavegador = new JPanel();
		panelNavegador.setBounds(42, 396, 249, 71);
		panel.add(panelNavegador);
		panelNavegador.setLayout(null);
		
		primero = new ImageIcon("imagenes/navPri.jpg");
		btnPrimero = new JButton(primero);
		btnPrimero.setBounds(10, 11, 48, 46);
		panelNavegador.add(btnPrimero);
		
		izquierda = new ImageIcon("imagenes/navIzq.jpg");
		btnIzquierda = new JButton(izquierda);
		btnIzquierda.setBounds(68, 11, 48, 46);
		panelNavegador.add(btnIzquierda);
		
		derecha = new ImageIcon("imagenes/navDer.jpg");
		btnDerecha = new JButton(derecha);
		btnDerecha.setBounds(126, 11, 48, 46);
		panelNavegador.add(btnDerecha);
		
		ultimo = new ImageIcon("imagenes/navUlt.jpg");
		btnFinal = new JButton(ultimo);
		btnFinal.setBounds(184, 11, 48, 46);
		panelNavegador.add(btnFinal);
		
		
	}
}
