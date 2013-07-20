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
    	

    private class ClienteLerThread extends Thread {

           public ClienteLerThread () {

           }

           @Override
           public void run() {

               try {

                       while (true) {
                               String data = in.readUTF();
                               System.out.println(data);
                               
                               tratarMensagemRecebida(data);
                               //Principal.escreveMensagemChat(data);
                       }

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
            new ClienteLerThread().start();
        } catch (Exception e) {
        }

    }

    public void enviarMensagemChat(String msg){
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
