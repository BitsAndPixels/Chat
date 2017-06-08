package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatServidor extends JFrame {
	private Servidor servidor;
	private String estadoServer;
    private JButton btnConectar = new JButton("Conectar");
    JTextField txtPuerto = new JTextField();
	JTextArea txtEstadoServidor = new JTextArea();



	public String getEstadoServer() {
		return estadoServer;
	}

	public void setEstadoServer(String estadoServer) {
		this.estadoServer = estadoServer;
	}

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServidor frame = new ChatServidor();
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
	public ChatServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtEstadoServidor.setEditable(false);
		txtEstadoServidor.setBounds(1, 1, 307, 284);
		contentPane.add(txtEstadoServidor);
		
		
		JScrollPane scrollableText = new JScrollPane(txtEstadoServidor);
		   
	    scrollableText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollableText.setBounds(60, 100, 307, 400);
	    contentPane.add(scrollableText);
	    
	    txtPuerto = new JTextField();
	    txtPuerto.setHorizontalAlignment(SwingConstants.CENTER);
	    txtPuerto.setText("Puerto");
	    txtPuerto.setBounds(147, 49, 116, 22);
	    contentPane.add(txtPuerto);
	    txtPuerto.setColumns(10);
	    
	    JButton btnConectar = new JButton("Conectar");
	    btnConectar.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		if(servidor != null) {
	    			servidor.stop();
	    			servidor = null;
	    			txtPuerto.setEditable(true);
	    			btnConectar.setText("Conectar");
	    			return;
	    		}
	    		int puerto;
	    		try {
	    			puerto = Integer.parseInt(txtPuerto.getText().trim());
	    		}
	    		catch(Exception er) {
	    			MostrarEnEstado("Error , puerto incorrecto");
	    			return;
	    		}
	    		servidor = new Servidor(puerto, ChatServidor.this);
	    		new ServidorOn().start();
	    		btnConectar.setText("Desconectar");
	    		txtPuerto.setEditable(false);
	    	}
	    });
	    
	    
	    
	    
	    btnConectar.setBounds(166, 524, 97, 25);
	    contentPane.add(btnConectar);
	}
	public void MostrarEnEstado(String m){
		txtEstadoServidor.append(m+"\n");
	}
	
	class ServidorOn extends Thread {
		public void run() {
			servidor.start();       
			btnConectar.setText("Start");
			txtPuerto.setEditable(true);
			MostrarEnEstado("Error en el servidor");
			servidor = null;
		}
	}
	
}
