package br.ifce.ppd.multi;

import br.ifce.ppd.view.Principal;
import br.ifce.utils.Protocolo;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.org.mozilla.javascript.regexp.SubString;


public class Cliente {
    
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;
    private ClienteLerThread thread;
    
    	

    private class ClienteLerThread extends Thread {
        
        volatile boolean flgFinalizar = false;
        
           public ClienteLerThread () {

           }

           @Override
           public void run() {

               try {
                   
                       String data = in.readUTF();

                       while (!data.equals(Protocolo.CHAT_SAI)) {
                               
                               System.out.println(data);
                               
                               tratarMensagemRecebida(data);
                               
                               data = in.readUTF();
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
                //Desconectar cliente
                payLoad = msg.replaceFirst(Protocolo.CHAT_SAI, "");
                Principal.removeListaChat(payLoad);
                desconectar();
                break;
                
            default:
                System.out.println("Case Default - MSG Recebida: " + comando);
                
        }
        
    }
        
    public void conectar(){
        try {
            InetAddress endereco = InetAddress.getByName("localhost");
            cliente = new Socket(endereco, 1024);
            in =  new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());
            thread = new ClienteLerThread();
            thread.start();
        } catch (Exception e) {
        }

    }
    
    public void desconectar(){
        try {
            in.close();
            out.close();
            cliente.close();
        } catch (Exception e) {
        }

    }

    public void enviarMensagemChat(String protocolo,String msg){
        try {
            out.writeUTF(protocolo+msg);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
