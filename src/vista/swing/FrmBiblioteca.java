package vista.swing;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Libro;
import servicio.LibroServicio;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class FrmBiblioteca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel, panelLibros, panelMantenimientoLibro, panelNavegador;
	private ImageIcon nuevo, editar, borrar, guardar, deshacer, primero, izquierda, derecha, ultimo;
	private JButton btnNuevo, btnEditar, btnBorrar, btnGuardar, btnDeshacer, btnFiltrar;
	private JLabel lblIdLibro, lblTitulo, lblAutor, lblEditorial, lblIsbn, lblFecha, lblFecha2;
	private JTextField textIdLibro, textTitulo, textAutor, textEditorial, textIsbn, textFecha, textConsulta;
	private JCheckBox chcPrestado;
	private JButton btnPrimero, btnIzquierda, btnDerecha, btnFinal;
	private JComboBox cmbConsulta;
	private boolean b, libroNuevo;
	private int puntero, tamaño;
	DefaultTableModel dtm;
	List<Libro>libros= new ArrayList<Libro>();
	private JTable tableLibros;

	public FrmBiblioteca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1215, 621);
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
		cargarGrid(libros);
		this.setVisible(true);
	}



	private void cargarGrid(List<Libro> libros) {
		
		String[] titulos = {"isbn", "titulo", "autor", "editorial", "fechaDevolucion"};
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(titulos);
		
		for(Libro l: libros) {
			String fecha = "";
			if(l.getFechaDevolucion() != null) {
				fecha = l.getFechaDevolucion().toString();
			}
			Object[] fila = {l.getIsbn(), l.getTitulo(), l.getAutor(), l.getEditorial(), fecha};
			dtm.addRow(fila);
		}
		
	}



	private void mostrarLibro(int puntero) {

		Libro libro = libros.get(puntero);
		textAutor.setText(libro.getAutor());
		textTitulo.setText(libro.getTitulo());
		textEditorial.setText(libro.getEditorial());
		if(libro.getFechaDevolucion() != null)
		textFecha.setText(libro.getFechaDevolucion().toString());
		else
		textFecha.setText("");
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
				textFecha.setEnabled(false);
				chcPrestado.setEnabled(false);
				
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
		
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas borrar el libro?", "Confirmación", JOptionPane.YES_NO_OPTION);			
				if(opcion == JOptionPane.YES_OPTION) {
					LibroServicio libroServicio = new LibroServicio();
					try {
						libroServicio.borrarLibro(puntero);
						libros = libroServicio.obtenerTodos();
					} catch (IOException e1) {
						System.out.println(e1.getMessage());					
					}
					puntero = 0;
					mostrarLibro(puntero);
					cargarGrid(libros);
				}
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
					
					try {
						libros = libroServicio.obtenerTodos();
						libroServicio=null;
					}catch (Exception e1) {
						System.out.println("Error en libro servicio frm");
						
					}
					
					habilitarPanelNavegador(true);
					habilitarPanelDeMantenimiento(true);
					habilitarPanelDeLibros(false);
					puntero = 0;
					mostrarLibro(puntero);
					cargarGrid(libros);
					textFecha.setEnabled(false);
					chcPrestado.setEnabled(false);

				}else{
					LibroServicio libroServicio = new LibroServicio();
					try {
						libroServicio.editarLibro(textIsbn.getText(), textFecha.getText(), chcPrestado.isSelected());
					} catch (IOException e1) {
						System.out.println("Error en libro servicio al editar");
					}
					
					try {
						libros = libroServicio.obtenerTodos();
						libroServicio=null;
					}catch (Exception e1) {
						System.out.println("Error en libro servicio frm");
						
					}
					
					habilitarPanelNavegador(true);
					habilitarPanelDeMantenimiento(true);
					habilitarPanelDeLibros(false);
					puntero = 0;
					mostrarLibro(puntero);
					cargarGrid(libros);
					textFecha.setEnabled(false);
					chcPrestado.setEnabled(false);
					
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
				textFecha.setEnabled(false);
				chcPrestado.setEnabled(false);
				
			}
		});
		
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			 int opcionConsulta = cmbConsulta.getSelectedIndex();
			 LibroServicio libroServicio = new LibroServicio();
			 List<Libro> librosFiltrado = null;
			 switch (opcionConsulta) {
			 
			 case 0:
				 
				 libroServicio = new LibroServicio();
				 libros = libroServicio.obtenerTodos();
				 cargarGrid(libros);

				 break;
				 
			 case 1:
				 
				 libroServicio = new LibroServicio();
				 libros = libroServicio.obtenerTodos();
				 String filtroAutor = textConsulta.getText().toLowerCase();
				 if(filtroAutor == null) {break;}
				 librosFiltrado = libros.stream()
					.filter(libro -> libro.getAutor().toLowerCase().startsWith(filtroAutor))
					.toList();
				 cargarGrid(librosFiltrado);
				 
				 break;
				 
			 case 2:
				 
				 libroServicio = new LibroServicio();
				 libros = libroServicio.obtenerTodos();
				 LocalDate filtroFecha = LocalDate.now();
				 librosFiltrado = libros.stream()
						 .filter(libro -> libro.getFechaDevolucion() != null)
						 .filter(libro -> libro.getFechaDevolucion().isBefore(filtroFecha))
						 .toList(); 
				 cargarGrid(librosFiltrado);
				 
				 break;
			 }
				
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
		chcPrestado.setEnabled(false);
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
		
		cmbConsulta = new JComboBox();
		cmbConsulta.setModel(new DefaultComboBoxModel(new String[] {"todos", "autor", "no devueltos"}));
		cmbConsulta.setBounds(597, 54, 134, 22);
		panel.add(cmbConsulta);
		
		textConsulta = new JTextField();
		textConsulta.setBounds(762, 55, 177, 20);
		panel.add(textConsulta);
		textConsulta.setColumns(10);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(964, 54, 89, 23);
		panel.add(btnFiltrar);
		
		JPanel panelGrid = new JPanel();
		panelGrid.setBounds(597, 87, 592, 400);
		panel.add(panelGrid);
		panelGrid.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelGrid.add(scrollPane, BorderLayout.CENTER);
		
		dtm = new DefaultTableModel();
		tableLibros = new JTable(dtm);
		tableLibros.setEnabled(false);
		scrollPane.setViewportView(tableLibros);
		
		
	}
}
