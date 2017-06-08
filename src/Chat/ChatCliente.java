package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.ScrollPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class ChatCliente extends JFrame {
	private String EstadoCliente;
    private JTextArea txtGlobal = new JTextArea();
    private Cliente cliente;
	public String getEstadoCliente() {
		return EstadoCliente;
	}
	private boolean conectado;

	public void setEstadoCliente(String estadoCliente) {
		EstadoCliente = estadoCliente;
	}
	private JPanel contentPane;
	private JButton btnRegistrarse;
	private static JButton btnPrivado;
	private JTextField txtMGlobal;
	private JTextField txtServidor;
	private JTextField txtPuerto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatCliente frame = new ChatCliente();
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
	
	
	public ChatCliente() {
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
	
	    DefaultListModel<String> model = new DefaultListModel<>();
	    JList<String> list = new JList<>( model );
	    model.addElement("jose");
	    model.addElement("pepe");
	    model.addElement("jorge");
	    model.addElement("ana");
	    
	    JScrollPane scrollableList = new JScrollPane(list);
	   
	    scrollableList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollableList.setBounds(35, 56, 179, 169);
	    contentPane.add(scrollableList);
	    
	    JButton btnConectar = new JButton("Conectar");
	    
	    
	    btnConectar.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		String usuario = btnRegistrarse.getText().trim();
				String servidor = txtServidor.getText().trim();
				if(servidor.length() == 0)
					return;
				String puertos = txtPuerto.getText().trim();
				if(puertos.length() == 0)
					return;
				int puerto=0;
				try {
				
					puerto = Integer.parseInt(puertos);
				}
				catch(Exception en) {
					return;
				}

		
				cliente = new Cliente(servidor,puerto,usuario,ChatCliente.this);
				
				if(!cliente.start()) 
					return;
				MostrarEnGlobal("Esta ha logrado conectarse");
				conectado = true;
				btnConectar.setEnabled(false);
				
	    	}
	    });
	    
	    
	    btnConectar.setEnabled(false);
	    btnConectar.setBounds(117, 314, 97, 25);
	    contentPane.add(btnConectar);
	    
	    
	    JLabel lblMiembros = new JLabel("Miembros");
	    lblMiembros.setBounds(90, 27, 77, 16);
	    contentPane.add(lblMiembros);
	    
	    btnRegistrarse = new JButton("Registrarse");
	    btnRegistrarse.addChangeListener(new ChangeListener() {
	    	
	    	
	    	public void stateChanged(ChangeEvent e) {
	    		if(btnRegistrarse.getText().equals("Registrarse")){
	    			btnConectar.setEnabled(false);
	    			btnPrivado.setEnabled(false);
	    		}
	    		else
	    		{
	    			btnConectar.setEnabled(true);
    			btnPrivado.setEnabled(true);
    			}
	    			
	    	}
	    });
	   
	   
	    
	    
	    
	    
	    btnRegistrarse.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent arg0) {
	    		ClienteReg Reg=new ClienteReg(btnRegistrarse,list);
	    		Reg.setVisible(true);
	    	}
	    });
	    
	    
	    btnRegistrarse.setBounds(117, 238, 97, 25);
	    contentPane.add(btnRegistrarse);
	    
	    
	    
	    btnPrivado = new JButton("Privado");
	   
	    
	    btnPrivado.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e) {
	    		if (list.isSelectionEmpty())
	    		{
	    			 JOptionPane.showMessageDialog(null, "No selecciono ningun usuario");
	    		}
	    		else
	    		{
	    		Chat Chat=new Chat(list.getSelectedValue().toString(),btnRegistrarse.getText());
	    		Chat.setVisible(true);
	    		}
	    	}
	    });
	    
	    
	    
	    btnPrivado.setEnabled(false);
	    btnPrivado.setBounds(117, 276, 97, 25);
	    contentPane.add(btnPrivado);
	    
	    JScrollPane scrollPane = new JScrollPane((Component) null);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setBounds(258, 57, 244, 257);
	    contentPane.add(scrollPane);
	    
	    
	    txtGlobal.setEditable(false);
	    scrollPane.setViewportView(txtGlobal);
	    
	    txtMGlobal = new JTextField();
	    txtMGlobal.setBounds(256, 327, 155, 22);
	    contentPane.add(txtMGlobal);
	    txtMGlobal.setColumns(10);
	    
	    JButton btnEnviarG = new JButton("Enviar");
	    
	    
	    
	    btnEnviarG.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		cliente.sendMessage(new Mensaje(Mensaje.GLOBAL,txtMGlobal.getText()));
	    		txtMGlobal.setText("");
	    
	    	}
	    });
	    
	    
	    
	    btnEnviarG.setBounds(423, 327, 79, 25);
	    contentPane.add(btnEnviarG);
	    
	    txtServidor = new JTextField();
	    txtServidor.setText("localhost");
	    txtServidor.setBounds(35, 238, 61, 22);
	    contentPane.add(txtServidor);
	    txtServidor.setColumns(10);
	    
	    txtPuerto = new JTextField();
	    txtPuerto.setText("1500");
	    txtPuerto.setBounds(35, 279, 61, 22);
	    contentPane.add(txtPuerto);
	    txtPuerto.setColumns(10);
	    
	
	   
	      
		
		
		
		 
	}
	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}
	public JButton getBtnPrivado() {
		return btnPrivado;
	}
	
	
	public void MostrarEnGlobal(String m){
		txtGlobal.append(m+"\n");
	}
	
	
}

