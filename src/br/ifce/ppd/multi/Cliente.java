package br.ifce.ppd.multi;

/**
 * Classe: Cliente.java
 * Código do Cliente da aplicação
 * @author malveira
 * 
 */

import br.ifce.ppd.view.Principal;
import br.ifce.utils.Protocolo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {  
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;

    //Thread cliente
    private class ClienteLerThread extends Thread {
       
           @Override
           public void run() {
               try {
                       String data = in.readUTF();
                       System.out.println("Mensagem Recebida: "+data);

                       while (!data.equals(Protocolo.CHAT_SAI)) {           
                               System.out.println(data);
                               tratarMensagemRecebida(data);
                               data = in.readUTF();
                               System.out.println("Mensagem Recebida: "+data);
                       }
                       tratarMensagemRecebida(data);
               } catch (Exception e) {
                       System.out.println("Cliente Exceção Thread Ler");
                       System.out.println("" + e.getMessage());
                       e.printStackTrace();
               }
               super.run();
           }
    }
     
    /**
    * Trata mensagem recebida do Servidor
    *
    * @param msg           mensagem recebida
    * @return              void
    */
    public void tratarMensagemRecebida(String msg){        
        String comando = msg.substring(0, 8); //Pega o comando 
        String payLoad;
        
        switch (comando){
            
            case Protocolo.CHAT_INS:
                payLoad = msg.replaceFirst(Protocolo.CHAT_INS, "");
                Principal.insereListaChat(payLoad);
                break;
            
            case Protocolo.CHAT_MSG:
                payLoad = msg.replaceFirst(Protocolo.CHAT_MSG, "");
                Principal.escreveMensagemChat(payLoad);                
                break;
            
            case Protocolo.CHAT_NOT:                
                payLoad = msg.replaceFirst(Protocolo.CHAT_NOT, "");
                Principal.escreveMensagemChat(payLoad);
                break;
                
            case Protocolo.CHAT_SAI:
                //Desconectar um cliente
                System.out.println("Cliente saiu: " + comando);
                break;
            
            case Protocolo.CHAT_REM:
                //Desconectar um cliente
                payLoad = msg.replaceFirst(Protocolo.CHAT_REM, "");
                Principal.removeListaChat(payLoad);
                break;    
                
            default:
                System.out.println("Case Default - MSG Recebida: " + comando);                
        }        
    }
        
    /**
    * Conecta ao servidor
    *
    * @return              void
    */
    public void conectar(){
        try {
            InetAddress endereco = InetAddress.getByName("localhost");
            cliente = new Socket(endereco, 1024);
            in =  new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());
            new ClienteLerThread().start();
            
        } catch (Exception e) {
        }
    }
    
    /**
    * Envia mensagem de chat para o Servidor
    *
    * @param protocolo     protocolo usado na mensagem (classe Protocolo) 
    * @param msg           mensagem a ser enviada
    * @return              void
    */
    public void enviarMensagemChat(String protocolo,String msg){
        try {
            out.writeUTF(protocolo+msg);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
