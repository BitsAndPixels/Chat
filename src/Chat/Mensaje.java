package Chat;

import java.io.Serializable;

public class Mensaje implements Serializable{

	protected static final long serialVersionUID = 1112122200L;
	
	static final int INTEGRANTES = 0, PRIVADO = 1,GLOBAL=2, LOGOUT = 3; // Señala que tipo de mensaje es
	private int tipo;
	private String mensaje;
	
	
	public int getTipo() {
		return tipo;
	}


	public String getMensaje() {
		return mensaje;
	}


	Mensaje(int tipo, String mensaje) {
		this.tipo = tipo;
		this.mensaje = mensaje;
	}
	
}
