package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

	private ObjectInputStream entradac;		
	private ObjectOutputStream salidac;		
	private ChatCliente Ventana;
	private Socket socket;
	
	private String servidor, usuario;
	private int puerto;

	
	Cliente(String server, int puerto, String usuario) {
		this(server, puerto, usuario, null);
	}

	
	Cliente(String server, int puerto, String usuario, ChatCliente c) {
		this.servidor = server;
		this.puerto = puerto;
		this.usuario = usuario;
		this.Ventana = c;
	}
	
	
	public boolean start() {
		try {
			socket = new Socket(servidor, puerto);
		} 
		catch(Exception ec) {
			Ventana.MostrarEnGlobal("Error al tratar de conectar con el servidor");
			return false;
		}
		Ventana.MostrarEnGlobal("Conexion Establecida :" + socket.getInetAddress() + ":" + socket.getPort());
		try
		{
			entradac  = new ObjectInputStream(socket.getInputStream());
			salidac = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			Ventana.MostrarEnGlobal("Error al tratar de crear las entradas/salidas");
			return false;
		}

		new ListenFromServer().start();
		
		try
		{
			salidac.writeObject(usuario);
		}
		catch (IOException eIO) {
			Ventana.MostrarEnGlobal("Error al tratar de conectarse");
			cerrar();
			return false;
		}
		
		return true;
	}
	void sendMessage(Mensaje m) {
		try {
			salidac.writeObject(m);
		}
		catch(IOException e) {
			Ventana.MostrarEnGlobal("Error al tratar de enviar el mensaje");
		}
	}

	
	private void cerrar() {
		try { 
			if(entradac != null) entradac.close();
		}
		catch(Exception e) {
			
		} 
		try {
			if(salidac != null) salidac.close();
		}
		catch(Exception e) {
			
		} 
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {
			
		} 
		//nose q es esto todavia
		//if(cg != null)
			//cg.connectionFailed();
			
	}
	/*
	public static void main(String[] args) {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";
		switch(args.length) {
			// > javac Client username portNumber serverAddr
			case 3:
				serverAddress = args[2];
			// > javac Client username portNumber
			case 2:
				try {
					portNumber = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
					return;
				}			Ventana.setEstadoCliente("Error al tratar de crear las entradas/salidas");

			// > javac Client username
			case 1: 
				userName = args[0];
			// > java Client
			case 0:
				break;
			// invalid number of arguments
			default:
				System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
			return;
		}
		// create the Client object
		Client client = new Client(serverAddress, portNumber, userName);
		if(!client.start())
			return;
		Scanner scan = new Scanner(System.in);
		// loop forever for message from the user
		while(true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				// break to do the disconnect
				break;
			}
			// message WhoIsIn
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			}
			else {				// default to ordinary message
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}
		// done disconnect
		client.disconnect();	
	}
	*/

	
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					String m = (String) entradac.readObject();
					Ventana.MostrarEnGlobal(m);
				}
				catch(IOException e) {
					Ventana.MostrarEnGlobal("Se ha perdido la conexion");
					
					
					//if(cg != null) 
						//cg.connectionFailed();nose q hace , seguro hay q borrarlo
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
	
	
}
