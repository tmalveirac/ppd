package br.ifce.ppd.multi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;


public class Servidor {
	
	private static Vector<Usuario> usuarioVector = new Vector<Usuario>();
	private static Vector<String> conversaVector = new Vector<String>();
	
	private static class ServidorThread extends Thread {

		private Socket cliente;
		private DataInputStream in;
		private DataOutputStream out;

		public ServidorThread (Socket cliente) {
			this.cliente = cliente;
			
			try {
				in = new DataInputStream(cliente.getInputStream());
				out = new DataOutputStream(cliente.getOutputStream());
				
				
				System.out.println("Tamanho da msg antigas: " + conversaVector.size());
				
				//Envia toda a conversa antiga:
				for (String s : conversaVector){
					out.writeUTF(s);
				}
				
				//Se há mensagens antigas
				if (conversaVector.size()>0){
					out.writeUTF("---Mensagens antigas acima---");
					out.writeUTF("Bem vindo " + usuarioBySocket(usuarioVector, cliente).getNome() + "!");
				}
				else{
					out.writeUTF("Bem vindo " + usuarioBySocket(usuarioVector, cliente).getNome() + "!");
				}
				
			} catch (Exception e) {
				System.out.println("Servidor Exceção construtor");
				System.out.println("" + e.getMessage());
			}
		}
		
		
		//Envia tudo que recebe para todos os nós
		public void sendChatForAll(String msg){
			
			for (Usuario u : usuarioVector){
				try {
					String nome = usuarioBySocket(usuarioVector,this.cliente).getNome();
					String mensagem = new String(nome + " Enviou: "  + msg);
					conversaVector.add(mensagem);
					//System.out.println("Conversa Vector size = " + conversaVector.size());
					
					//Envia, menos para mim
					if (!u.getSocket().equals(this.cliente)){
						DataOutputStream outData = new DataOutputStream(u.getSocket().getOutputStream());
						
						outData.writeUTF(mensagem);
						//outData.close();
					}
				} catch (Exception e) {
					System.out.println("Servidor Exceção sendChatForAll()");
					System.out.println("" + e.getMessage());
				}
			}
		}

		@Override
		public void run() {

			try {

				while (true) {
					String data = in.readUTF();
					System.out.println(usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + data);
					
					//Envia para todos
					sendChatForAll(data);
				}

			} catch (Exception e) {
				System.out.println("Servidor Exceção Thread receive");
				System.out.println("" + e.getMessage());

				//Caso o cliente tenha desconectado, remover da lista
				try {
					in.close();
					out.close();
					cliente.close();
				} catch (IOException e1) {
					System.out.println("Servidor Exceção Thread receive - Fecha fluxo");
					e1.printStackTrace();
				}
				
				removeUsuario(usuarioVector, cliente);
			}

			super.run();

		}
	}
	
	//Remove um socket da lista, devolvendo true. Caso contrário, devolve false
	public static boolean removeUsuario (Vector<Usuario> usuarios, Socket socket){
		
		int usuarioRemovido=-1;
		int i=0;
		
		for (Usuario u : usuarios){
			if (u.getSocket().equals(socket)){
				usuarioRemovido=i;
				break;
			}
			i++;
		}
		
		
		if (usuarioRemovido!=-1){
			usuarios.remove(usuarioRemovido);
			return true;
		}
		
		return false;
	}
	
	//Retorna um Usuário relacionado ao socket, caso exista. Caso contrário, devolve null
	public static Usuario usuarioBySocket (Vector<Usuario> usuarios, Socket socket){
		
		for (Usuario u : usuarios){
			if (u.getSocket().equals(socket)){
				return u;
			}
		}
		
		return null;		
	}
	
	
	//------------------------------------------------
	
	public static void main(String[] args) {
		
		try {

			ServerSocket servidor = new ServerSocket(1024);
			System.out.println("Esperando conexão");
			
			try {
				int id = 0;
				
				while (true) {
					Socket socket = servidor.accept();
					System.out.println("Aceitou conexao com: " + socket.getInetAddress().toString());			
					
					DataInputStream in = new DataInputStream(socket.getInputStream());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					
					//Solicita nome ao usuário
					
					System.out.println("Solicitou Login");
					//out.writeUTF("Informe seu login: ");
					String nome = in.readUTF();
					//out.writeUTF("Bem vindo " + nome);
					
					Usuario u = new Usuario(socket, nome, id++);
					
					usuarioVector.add(u);
					
					ServidorThread c1 = new ServidorThread(socket);
					c1.start();
					
				}
				
			} catch (Exception e) {
				System.out.println("Servidor Exceção main()");
				System.out.println("" + e.getMessage());
			}

		} catch (Exception e) {
			System.out.println("Servidor Exceção main");
			System.out.println("" + e.getMessage());
		}
	}

	
	
	public static Vector<Usuario> getUsuarioVector() {
		return usuarioVector;
	}

	public static void setUsuarioVector(Vector<Usuario> usuarioVector) {
		Servidor.usuarioVector = usuarioVector;
	}
	

}
