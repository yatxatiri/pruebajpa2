package com.ciberfarma.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ciberfarma.model.Categorias;
import com.ciberfarma.model.Proveedor;
import com.ciberfarma.model.Usuario;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//import javax.persistence.EntityManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCodigo;
	private JComboBox cboCategorias;
	private JComboBox cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(324, 29, 89, 23);
		contentPane.add(btnRegistrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox();
		cboCategorias.setBounds(122, 70, 86, 22);
		contentPane.add(cboCategorias);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		JLabel lblProveedores = new JLabel("Proveedor:");
		lblProveedores.setBounds(230, 106, 102, 14);
		contentPane.add(lblProveedores);

		cboProveedores = new JComboBox();
		cboProveedores.setBounds(300, 104, 120, 22);
		contentPane.add(cboProveedores);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(324, 63, 89, 23);
		contentPane.add(btnBuscar);

		llenaCombo();
	}

	void llenaCombo() {
		// TODO Auto-generated method stub
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion02");
		jakarta.persistence.EntityManager em = conexion.createEntityManager();	
		
		// sql= "select * from tb_categorias"
		// jpql= "select u from Categorias u"
		
		List<Categorias> list_categorias = em.createQuery("select u from Categorias u").getResultList();
		
		cboCategorias.addItem("Seleccione");
		for (Categorias c : list_categorias) {
			cboCategorias.addItem(c.getDescripcion());
		}
		
		List<Proveedor> list_proveedores = em.createQuery("select u from Proveedor u").getResultList();
		
		cboProveedores.addItem("Seleccione");
		for (Proveedor p : list_proveedores) {
			cboProveedores.addItem(p.getNombre_rs());
		}
		
		em.close();
	}

	void registrar() {
		// TODO Auto-generated method stub
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion02");
		jakarta.persistence.EntityManager em = conexion.createEntityManager();	
		
		Producto p = new Producto();
		p.setId_prod(txtCodigo.getText());
		p.setDes_prod(txtDescripcion.getText());
		p.setStk_prod(Integer.parseInt(txtStock.getText()));
		p.setPre_prod(Double.parseDouble(txtPrecio.getText()));
		p.setCategoria(em.find(Categorias.class, cboCategorias.getSelectedIndex()));
		p.setEst_prod(1);
		p.setProveedor(em.find(Proveedor.class, cboProveedores.getSelectedIndex()));
		
		try {
			em.getTransaction().begin(); // reg,act,eli
			em.merge(p);
			System.out.println("Actualizacion ok");
			em.getTransaction().commit();
		} catch (Exception e){
			System.out.println("Error al Actualizar: " +  e.getMessage());
		}
		
		em.close();
		
		/*		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = conexion.createEntityManager();
		
		//proceso
		Usuario u = new Usuario();
		u.setCod_usua(6);
		u.setNom_usua("Angel Orlando");
		u.setApe_usua("Minaya");
		u.setCorreo("a00@gmail.com");
		u.setClave("1234");
		u.setFna_usua("2000/10/05");
		u.setIdtipo(2);
		u.setEst_usua(1);
		try {
		em.getTransaction().begin(); // reg,act,eli
		em.merge(u);
		System.out.println("Actualizacion ok");
		em.getTransaction().commit();
		} catch (Exception e){
			System.out.println("Error al Actualizar: " +  e.getMessage());
		}
		em.close();*/
	}

	void listado() {
		// TODO Auto-generated method stub
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion02");
		jakarta.persistence.EntityManager em = conexion.createEntityManager();	
		
		List<Producto> list_producto = em.createQuery("select u from Producto u").getResultList();
		txtSalida.setText("");
		
		for (Producto p : list_producto) {
			txtSalida.append(p.getId_prod() + " " + p.getDes_prod() + " " + p.getStk_prod() + " " + p.getPre_prod() + "\n");
		}
		
		em.close();
	}
	
	void buscar() {
		// TODO Auto-generated method stub
		
	}
}
