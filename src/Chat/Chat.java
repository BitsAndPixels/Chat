package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat("global","usuario");
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
	public Chat(String modo,String usuario) {
		setTitle(modo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 393, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(29, 27, 307, 168);
		contentPane.add(textArea);
		
		
		JScrollPane scrollableText = new JScrollPane(textArea);
		   
	    scrollableText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollableText.setBounds(29, 27, 307, 168);
	    contentPane.add(scrollableText);
	    
	    JButton btnEnviar = new JButton("Enviar");
	    btnEnviar.setBounds(239, 213, 97, 25);
	    contentPane.add(btnEnviar);
	    
	    textField = new JTextField();
	   
	    textField.setBounds(29, 213, 206, 25);
	    contentPane.add(textField);
	    textField.setColumns(10);
	    
	    
	   
	    btnEnviar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		if (modo == "global"){
	    			textArea.setText(textArea.getText()+'\n'+usuario+": "+ textField.getText());
	    			textField.setText("");
	    		}
	    		else
	    		{
	    			textArea.setText(textArea.getText()+'\n'+ textField.getText());
	    			textField.setText("");
	    		}
	    			
	    			
	    	}
	    });
	    
	    
	    textField.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

            	if (modo == "global"){
	    			textArea.setText(textArea.getText()+'\n'+usuario+": "+ textField.getText());
	    			textField.setText("");
	    		}
	    		else
	    		{
	    			textArea.setText(textArea.getText()+'\n'+ textField.getText());
	    			textField.setText("");
	    		}
	    		

            }});
	    
	    
	}
}
