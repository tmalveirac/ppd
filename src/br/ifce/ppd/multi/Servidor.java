package br.ifce.ppd.multi;

import br.ifce.ppd.view.Principal;
import br.ifce.utils.Protocolo;
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
import java.util.logging.Level;
import java.util.logging.Logger;


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
				
				
				//System.out.println("Tamanho da msg antigas: " + conversaVector.size());
				
                                //Envia lista de Login
                                enviarMensagemParaTodos(Protocolo.CHAT_INS, "");
                                
				//Envia toda a conversa antiga:
				for (String s : conversaVector){
					out.writeUTF(Protocolo.CHAT_MSG+s);
				}
				
				//Se há mensagens antigas
				if (conversaVector.size()>0){
					out.writeUTF(Protocolo.CHAT_MSG+"---Mensagens antigas acima---");
					out.writeUTF(Protocolo.CHAT_MSG+"Bem vindo " + usuarioBySocket(usuarioVector, cliente).getNome() + "!");
				}
				else{
					out.writeUTF(Protocolo.CHAT_MSG+"Bem vindo " + usuarioBySocket(usuarioVector, cliente).getNome() + "!");
				}
				
                                //Envia Notificação para todos
                                String notificacao = usuarioBySocket(usuarioVector, cliente).getNome() + " acabou de entrar.";
                                //sendChatForAll(notificacao);
                                enviarMensagemParaTodos(Protocolo.CHAT_NOT, notificacao);
                                
			} catch (Exception e) {
				System.out.println("Servidor Exceção construtor");
				System.out.println("" + e.getMessage());
			}
		}
		
                public void enviarMensagemParaUmUsuario(String protocolo, String msg){
                    try {
                        out.writeUTF(protocolo+msg);
                        out.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                         
                
                public void enviarMensagemParaTodos(String protocolo, String msg){
                    
                    for (Usuario u : usuarioVector){
                       
                        
                        try {
                            
                             
                             DataOutputStream outData = new DataOutputStream(u.getSocket().getOutputStream());
                             
                             //Se for mensagem de Logins para popular lista
                             if (protocolo.equals(Protocolo.CHAT_INS)){
                                 for (Usuario u2 : usuarioVector) {
                                    outData.writeUTF(protocolo+u2.getNome());
                                 }
                                 continue;
                             }
                             
                             //Se for mensagem de Remoção de logins
                             if (protocolo.equals(Protocolo.CHAT_REM)){
                                
                                if (!u.getNome().equals(msg)){
                                    outData.writeUTF(protocolo+msg);
                                }
                                    
                                continue;
                             }
                             
                             
                             //Se for mensagem de chat ou notificações
                             conversaVector.add(msg); 
                             
                             if (!u.getSocket().equals(this.cliente)){
                                 outData.writeUTF(protocolo+msg);
                             }
                             
                             
                           
                         
                        } catch (IOException ex) {
                            System.out.println("Servidor Exceção enviarMensagemParaTodos()");
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
		
		//Envia tudo que recebe para todos os nós
		public void sendChatForAll(String msg){
			
			for (Usuario u : usuarioVector){
				try {
					//String nome = usuarioBySocket(usuarioVector,this.cliente).getNome();
					//String mensagem = new String(nome + " Enviou: "  + msg);
					
                                        //conversaVector.add(mensagem);
					conversaVector.add(msg);
                                        //System.out.println("Conversa Vector size = " + conversaVector.size());
					
					//Envia, menos para mim
					if (!u.getSocket().equals(this.cliente)){
						DataOutputStream outData = new DataOutputStream(u.getSocket().getOutputStream());
						
						outData.writeUTF(Protocolo.CHAT_MSG+msg);
						//outData.close();
					}
				} catch (Exception e) {
					System.out.println("Servidor Exceção sendChatForAll()");
					System.out.println("" + e.getMessage());
				}
			}
		}
                
                
                
                
                public void tratarMensagemRecebida(String msg){

                    String comando = msg.substring(0, 8); //Pega o comando 
                    String payLoad;

                    switch (comando){

                        case Protocolo.CHAT_INS:
                                //Não Utilizado
                            break;

                        case Protocolo.CHAT_MSG:
                            payLoad = msg.replaceFirst(Protocolo.CHAT_MSG, "");
                            String data;
                            data = usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + payLoad;

                            enviarMensagemParaTodos(Protocolo.CHAT_MSG, data);

                            break;

                        case Protocolo.CHAT_NOT:
                                
                            break;
                            
                        case Protocolo.CHAT_SAI:
                            
                            enviarMensagemParaUmUsuario(Protocolo.CHAT_SAI, "");
                            enviarMensagemParaTodos(Protocolo.CHAT_MSG, usuarioBySocket(usuarioVector, cliente).getNome()+ " saiu." );
                            enviarMensagemParaTodos(Protocolo.CHAT_REM, usuarioBySocket(usuarioVector, cliente).getNome());
                            removeUsuario(usuarioVector, cliente);
                            
                            
                            
                          
                            System.out.println("Removeu Usuario ");
                            
                            break;

                        default:
                            System.out.println("Case Default - MSG Recebida: " + comando);

                    }

                }
                
                 //Remove um socket da lista, devolvendo true. Caso contrário, devolve false
                public boolean removeUsuario (Vector<Usuario> usuarios, Socket socket){

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
                                //Caso o cliente tenha desconectado, remover da lista
				try {
					in.close();
					out.close();
					cliente.close();
				} catch (IOException e1) {
					System.out.println("Servidor Exceção Thread receive - Fecha fluxo");
					e1.printStackTrace();
				}
                                
                                return true;

                        }

                        return false;
                }

	
                

		@Override
		public void run() {

			try {
                                String data = in.readUTF();
                                System.out.println("Mensagem Recebida: "+data);
				while (!data.equals(Protocolo.CHAT_SAI)) {
					 
					System.out.println(usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + data);
//					
//                                                                             
//                                        data = usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + data;
//                                        
//					//Envia para todos
//					//sendChatForAll(data);
//                                        enviarMensagemParaTodos(Protocolo.CHAT_MSG, data);
                                        
                                        tratarMensagemRecebida(data);
                                        data = in.readUTF();
                                        System.out.println("Mensagem Recebida: "+data);
				}
                                
                                tratarMensagemRecebida(data);

			} catch (Exception e) {
				System.out.println("Servidor Exceção Thread receive");
				System.out.println("" + e.getMessage());

//				//Caso o cliente tenha desconectado, remover da lista
//				try {
//					in.close();
//					out.close();
//					cliente.close();
//				} catch (IOException e1) {
//					System.out.println("Servidor Exceção Thread receive - Fecha fluxo");
//					e1.printStackTrace();
//				}
//				
//				removeUsuario(usuarioVector, cliente);
                                //Principal.removeListaChat(usuarioBySocket(usuarioVector, cliente).getNome());
			}

			super.run();

		}
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
                                        System.out.println("Mensagem Recebida: "+nome);
					//out.writeUTF("Bem vindo " + nome);
					
                                        nome = nome.replaceFirst(Protocolo.CHAT_INS, "");
                                                
					Usuario u = new Usuario(socket, nome, id++);
					
					usuarioVector.add(u);
					
                                        //Add usuario no chat
                                        //out.writeUTF(Protocolo.CHAT_INS+nome);
                                        
                                        
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
