package br.ifce.ppd.multi;

/**
 * Classe: Usuario.java
 * Define um usuário da aplicação
 * @author Tiago Malveira
 * 
 */

import java.net.Socket;

public class Usuario {
	private Socket socket;
	private String nome;
	private int id;	
	
	public Usuario(Socket socket, String nome, int id) {
		this.socket = socket;
		this.nome = nome;
		this.id = id;
	}
		
	public Socket getSocket() {
		return socket;
	}
        
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
        
	public String getNome() {
		return nome;
	}
        
	public void setNome(String nome) {
		this.nome = nome;
	}
        
	public int getId() {
		return id;
	}
        
	public void setId(int id) {
		this.id = id;
	}
}
