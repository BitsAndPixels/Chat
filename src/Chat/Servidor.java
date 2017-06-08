package Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	
		private static final ArrayList<ClientThread> ListaCliente = null;
		private static int numerocliente;
	 	private ChatServidor Ventana; 
		private ArrayList<ClientThread> ListaClientes;
		private int puerto;
		private boolean escuchando;
		

		public Servidor(int puerto) {
			this(puerto, null);
		}
		
		public Servidor(int puerto, ChatServidor V) {
			this.Ventana = V;
			this.puerto = puerto;
			ListaClientes = new ArrayList<ClientThread>();
		}
		
		public void start() {
			escuchando = true;
			
			try 
			{
				ServerSocket socketServidor = new ServerSocket(puerto);
				while(escuchando) 
				{
					Ventana.MostrarEnEstado("Esperando conexiones");
					Socket socketCliente = socketServidor.accept();  
					
					if(!escuchando)
						break;
					ClientThread cliente = new ClientThread(socketCliente);  
					ListaClientes.add(cliente);									
					cliente.start();
				}
				
				try {
					socketServidor.close();
					for(int i = 0; i < ListaClientes.size(); ++i) {
						ClientThread tc = ListaCliente.get(i);
						try {
						tc.entradac.close();
						tc.salidac.close();
						tc.socket.close();
						}
						catch(IOException ioE) {
							
						}
					}
				}
				catch(Exception e) {
					
					Ventana.MostrarEnEstado("Error , se cerraran las conexiones");
				}
			}
			
			catch (IOException e) {
				Ventana.MostrarEnEstado("Error en el socket, se cerraran las conexiones");
				

			}
		}		
	    
		protected void stop() {
			escuchando = false;
		
			try {
				new Socket("localhost", puerto);
			}
			catch(Exception e) {
				
			}
		}
		
		private synchronized void global(String m)  {
		
			String mensaje = m;
			
			Ventana.MostrarEnEstado(mensaje);
			
			for(int i = ListaClientes.size(); --i >= 0;) {
				ClientThread ct = ListaClientes.get(i);
				if(!ct.writeMsg(mensaje)) {
					ListaClientes.remove(i);
					Ventana.MostrarEnEstado("Se ha desconectado:"+ct.usuario);
				}
			}
		}

		synchronized void remove(int c) {
			for(int i = 0; i < ListaClientes.size(); ++i) {
				ClientThread ct = ListaClientes.get(i);
				if(ct.id == c) {
					ListaClientes.remove(i);
					return;
				}
			}
		}
		
	

		
		class ClientThread extends Thread {
			
			Socket socket;
			ObjectInputStream entradac;
			ObjectOutputStream salidac;
			int id;
			String usuario;
			Mensaje m;
			
			ClientThread(Socket socket) {
				id = ++numerocliente;
				this.socket = socket;
				
				try
				{
					salidac = new ObjectOutputStream(socket.getOutputStream());
					entradac  = new ObjectInputStream(socket.getInputStream());
					usuario = (String) entradac.readObject();
					Ventana.MostrarEnEstado("Se ha conectado:"+usuario);
				}
				catch (IOException e) {
					Ventana.MostrarEnEstado("Error al crear las entradas/salidas");
					return;
				}
				
				catch (ClassNotFoundException e) {
				}
	           
			}

			public void run() {
				boolean escuchar = true;
				while(escuchar) {
					try {
						m = (Mensaje) entradac.readObject();
					}
					catch (IOException e) {
						Ventana.MostrarEnEstado("Error al crear las entradas del cliente:"+usuario);
						break;				
					}
					catch(ClassNotFoundException e2) {
						break;
					}
					
					String mensaje = m.getMensaje();

					switch(m.getTipo()) {

					case Mensaje.PRIVADO:
						// crear un metodo para el mensaje privado 
						break;
					case Mensaje.GLOBAL:
					
							global(usuario + ": " + mensaje);
				
				
						break;
					case Mensaje.LOGOUT:
						Ventana.MostrarEnEstado("El cliente:"+usuario+" se desconectara");
						escuchar = false;
						break;
					case Mensaje.INTEGRANTES:
						
						//crear metodo para mostrar esta lista
						for(int i = 0; i < ListaClientes.size(); ++i) {
							ClientThread ct = ListaClientes.get(i);
							writeMsg((i+1) + ") " + ct.usuario );
						}
						break;
					}
				}
				remove(id);
				close();
			}
			
			private void close() {
				try {
					if(salidac != null) salidac.close();
				}
				catch(Exception e) {}
				try {
					if(entradac != null) entradac.close();
				}
				catch(Exception e) {};
				try {
					if(socket != null) socket.close();
				}
				catch (Exception e) {}
			}

			
			private boolean writeMsg(String mensaje) {
				if(!socket.isConnected()) {
					close();
					return false;
				}
				try {
					salidac.writeObject(mensaje);
				}
				catch(IOException e) {
					Ventana.MostrarEnEstado("Error al enviar el mensaje al cliente:"+usuario);
				}
				return true;
			}
		}
	
}
