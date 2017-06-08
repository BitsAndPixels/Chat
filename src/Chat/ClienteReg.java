package Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteReg extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNick;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ClienteReg frame = new ClienteReg();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteReg(JButton boton , JList list) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 301, 212);
		contentPane = new JPanel();
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		
		JButton btnNewButton = new JButton("Aceptar");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boton.setText(textField.getText());
				boton.setEnabled(false);
				setVisible(false);
				dispose(); 
			}
		});
		
		
		
		
		
		btnNewButton.setBounds(91, 92, 97, 25);
		btnNewButton.setEnabled(false);
		contentPane.add(btnNewButton);
		
		ListModel model = list.getModel();


		textField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {

				for(int i=0;i<model.getSize();i++){
					if(model.getElementAt(i).equals(textField.getText())){
					textField.setBackground(Color.RED);
					i=model.getSize();
					btnNewButton.setEnabled(false);
					}
					else
					{
					textField.setBackground(Color.WHITE);
					btnNewButton.setEnabled(true);
					}

					}
				if (textField.getText().equals("")){
					btnNewButton.setEnabled(false);
				}
				

			}
		});
		
		
	
		
	
		textField.setBounds(64, 57, 157, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
	
		
		lblNick = new JLabel("Nick");
		lblNick.setBounds(119, 24, 89, 20);
		contentPane.add(lblNick);
		
		
			
		
		
	}
}
