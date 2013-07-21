package br.ifce.ppd.multi;

/**
 * Classe: Servidor.java
 * Código do Servidor da aplicação
 * @author malveira
 * 
 */

import br.ifce.ppd.view.Principal;
import br.ifce.utils.Protocolo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
	
        // Vetor que armazena os usuários ativos em uma sessão
	private static Vector<Usuario> usuarioVector = new Vector<Usuario>();
	// Vetor que armazena todas as mensagens do chat
        private static Vector<String> conversaVector = new Vector<String>();
        // Id dos usuários: incremental - Usado para tratar login repetido, concatenando com seu ID
        private static int id = 0;
	
        //Thread Servidor
	private static class ServidorThread extends Thread {

            private Socket cliente;
            private DataInputStream in;
            private DataOutputStream out;

            public ServidorThread (Socket cliente) {
                    this.cliente = cliente;

                    try {
                            //Abre fluxo de entrada e saída do socket cliente
                            in = new DataInputStream(cliente.getInputStream());
                            out = new DataOutputStream(cliente.getOutputStream());

                            //Envia lista de Login
                            enviarMensagemParaTodos(Protocolo.CHAT_INS, "");

                            //Envia toda a conversa antiga:
                            for (String s : conversaVector){
                                    enviarMensagemParaUmUsuario(Protocolo.CHAT_MSG, s);
                            }

                            //Se há mensagens antigas, envia a mensagem abaixo para separar
                            if (conversaVector.size()>0){
                                    enviarMensagemParaUmUsuario(Protocolo.CHAT_MSG, "---Mensagens antigas acima---");

                            }

                            //Envia Notificação para todos
                            String notificacao = usuarioBySocket(usuarioVector, cliente).getNome() + " acabou de entrar.";
                            enviarMensagemParaTodos(Protocolo.CHAT_NOT, notificacao);
                            //Envia a mensagem Bem vindo para o cliente!
                            String bemvindo = "Bem vindo " + usuarioBySocket(usuarioVector, cliente).getNome() + "!";
                            enviarMensagemParaUmUsuario(Protocolo.CHAT_MSG, bemvindo);

                    } catch (Exception e) {
                            System.out.println("Servidor Exceção construtor");
                            System.out.println("" + e.getMessage());
                    }
            }
		
            /**
             * Envia mensagem para um único cliente (relacionado à thread do servidor)
             *
             * @param protocolo     protocolo usado na mensagem (classe Protocolo) 
             * @param msg           mensagem a ser enviada
             * @return              void
             */
            public void enviarMensagemParaUmUsuario(String protocolo, String msg){
                try {
                    out.writeUTF(protocolo+msg);
                    out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                         
               
             /**
             * Envia mensagem para todos os usuários
             *
             * @param protocolo     protocolo usado na mensagem (classe Protocolo) 
             * @param msg           mensagem a ser enviada
             * @return              void
             */
            public void enviarMensagemParaTodos(String protocolo, String msg){                    
                for (Usuario u : usuarioVector){                                              
                    try {                            
                         DataOutputStream outData = new DataOutputStream(u.getSocket().getOutputStream());

                         //Se for mensagem de Logins para popular lista, envia para todos
                         if (protocolo.equals(Protocolo.CHAT_INS)){
                             for (Usuario u2 : usuarioVector) {
                                outData.writeUTF(protocolo+u2.getNome());
                             }
                             continue;
                         }

                         //Se for mensagem de Remoção de logins, envia para todos
                         if (protocolo.equals(Protocolo.CHAT_REM)){                              
                            if (!u.getNome().equals(msg)){
                                outData.writeUTF(protocolo+msg);
                            }                                   
                            continue;
                         }                
                         //Se for mensagem de chat ou notificações, adiciona na lista
                         conversaVector.add(msg);     
                         //Envia
                         outData.writeUTF(protocolo+msg);
                    } catch (IOException ex) {
                        System.out.println("Servidor Exceção enviarMensagemParaTodos()");
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }

            /**
            * Trata a mensagem recebida
            *             
            * @param msg           mensagem recebida
            * @return              void
            */    
            public void tratarMensagemRecebida(String msg){
                String comando = msg.substring(0, 8); //Extrai o comando 
                String payLoad;

                // Trata a mensagem conforme o protocolo
                switch (comando){

                    case Protocolo.CHAT_INS:
                        //Nunca é enviada para o servidor
                        break;

                    case Protocolo.CHAT_MSG:
                        payLoad = msg.replaceFirst(Protocolo.CHAT_MSG, "");
                        String data;
                        data = usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + payLoad;
                        enviarMensagemParaTodos(Protocolo.CHAT_MSG, data);
                        break;

                    case Protocolo.CHAT_NOT:
                            //Nunca é enviada para o servidor
                        break;

                    case Protocolo.CHAT_SAI:                          
                        enviarMensagemParaUmUsuario(Protocolo.CHAT_SAI, "");
                        String notificacao = usuarioBySocket(usuarioVector, cliente).getNome() + " acabou de sair.";
                        enviarMensagemParaTodos(Protocolo.CHAT_NOT, notificacao);
                        enviarMensagemParaTodos(Protocolo.CHAT_REM, usuarioBySocket(usuarioVector, cliente).getNome());
                        removeUsuario(usuarioVector, cliente);
                        System.out.println("Removeu Usuario ");                           
                        break;

                    default:
                        System.out.println("Case Default - MSG Recebida: " + comando);
                }
            }
              
            /**
            * Remove um usuário e encerra a conexão
            *             
            * @param usuarios   coleção de usuários ativos
            * @param socket     socket do usuário a ser removido e encerrado    
            * @return           true, se foi removido, false caso contrário
            */
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

            //Método run da Thread
            @Override
            public void run() {

                    try {
                            String data = in.readUTF();
                            System.out.println("Mensagem Recebida: "+data);
                            //Enquanto não recebe mensagem de saída, escute as mensagens do cliente
                            while (!data.equals(Protocolo.CHAT_SAI)) {		 
                                    System.out.println(usuarioBySocket(usuarioVector, cliente).getNome() + " enviou: " + data);                                        
                                    tratarMensagemRecebida(data);
                                    data = in.readUTF();
                                    System.out.println("Mensagem Recebida: "+data);
                            }                
                            tratarMensagemRecebida(data);
                    } catch (Exception e) {
                            System.out.println("Servidor Exceção Thread receive");
                            System.out.println("" + e.getMessage());
                    }
                    super.run();
            }
	}
        
        /**
        * Retorna um Usuário relacionado ao socket, caso exista
        *             
        * @param usuarios   coleção de usuários ativos
        * @param socket     socket do usuário
        * @return           usuário relacionado a socket se existir, caso contrário, null
        */
	//Retorna um Usuário relacionado ao socket, caso exista. Caso contrário, devolve null
	public static Usuario usuarioBySocket (Vector<Usuario> usuarios, Socket socket){		
		for (Usuario u : usuarios){
			if (u.getSocket().equals(socket)){
				return u;
			}
		}		
		return null;		
	}
	
        public static boolean existeUsuarioByNome(Vector<Usuario> usuarios, String nome){
            for (Usuario u : usuarios){
                if (u.getNome().equals(nome)){
                        return true;
                }
            }		
            return false;
        }
        
	//Método principal do Servidor
	public static void main(String[] args) {		
		try {
			
                    //Abre a conexão 
                    ServerSocket servidor = new ServerSocket(1024);
                    System.out.println("Esperando conexão");
			
                    try {
                            

                            while (true) {
                                    //Espera clientes 
                                    Socket socket = servidor.accept();
                                    System.out.println("Aceitou conexao com: " + socket.getInetAddress().toString());								
                                    DataInputStream in = new DataInputStream(socket.getInputStream());
                                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                                    //Recebe login do usuário			
                                    String nome = in.readUTF();
                                    System.out.println("Mensagem Recebida: "+nome);
                                    nome = nome.replaceFirst(Protocolo.CHAT_INS, "");
                                    Usuario u = new Usuario(socket, nome, id++);
                                    
                                    //verifica se já existe usuário com mesmo nome
                                    //Cria um usuário para esta conexão
                                    if (existeUsuarioByNome(usuarioVector,nome)){
                                        u.setNome(u.getNome()+"_"+u.getId());                                        
                                        //Avisa Cliente seu novo login
                                        Principal.alertaServidorIfLoginAlterado(u.getNome());	
                                    }       
                                    //Add usuário na lista do servidor
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
}