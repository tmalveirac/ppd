package br.ifce.ppd.multi;

/**
 * Classe: Cliente.java
 * Código do Cliente da aplicação
 * @author Tiago Malveira
 * 
 */

import br.ifce.ppd.view.Principal;
import br.ifce.utils.Protocolo;
import br.ifce.utils.TipoFigura;
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
                       System.out.println("Cliente Exceção Thread Ler - Cliente Fechado");
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
        String payLoad = msg.replace(comando, "");
        String campo[];
        
        switch (comando){
            
            case Protocolo.CHAT_INS:
//                payLoad = msg.replaceFirst(Protocolo.CHAT_INS, "");
                Principal.insereListaChat(payLoad);
                //Criar Label para ponta do mouse
                Principal.criaPontaMouse(payLoad);
                break;
            
            case Protocolo.CHAT_MSG:
//                payLoad = msg.replaceFirst(Protocolo.CHAT_MSG, "");
                Principal.escreveMensagemChat(payLoad);                
                break;
            
            case Protocolo.CHAT_NOT:                
//                payLoad = msg.replaceFirst(Protocolo.CHAT_NOT, "");
                Principal.escreveMensagemChat(payLoad);
                break;
                
            case Protocolo.CHAT_SAI:
                //Desconectar um cliente
                System.out.println("Cliente saiu: " + comando);
                break;
            
            case Protocolo.CHAT_REM:
                //Excluir um cliente
//                payLoad = msg.replaceFirst(Protocolo.CHAT_REM, "");
                Principal.removeListaChat(payLoad);
                Principal.removePontaMouse(payLoad);
                break;    
                
            case Protocolo.IMG_CQUA:
                campo = payLoad.split(":");
                Principal.criarFiguraAreaEdicao(TipoFigura.QUADRADO, Integer.parseInt(campo[0]),Integer.parseInt(campo[1]),Integer.parseInt(campo[2]));
                break;
                
            case Protocolo.IMG_CCIR:
                campo = payLoad.split(":");
                Principal.criarFiguraAreaEdicao(TipoFigura.CIRCULO, Integer.parseInt(campo[0]),Integer.parseInt(campo[1]),Integer.parseInt(campo[2]));
                break;
                
            case Protocolo.IMG_CTRI:
                campo = payLoad.split(":");
                Principal.criarFiguraAreaEdicao(TipoFigura.TRIANGULO, Integer.parseInt(campo[0]),Integer.parseInt(campo[1]),Integer.parseInt(campo[2]));
                break;
                
            case Protocolo.IMG_REMO:
                Principal.removerFiguraAreaEdicao(Integer.parseInt(payLoad));
                System.out.println("Remover figura id=" + payLoad);
                break;
                
            case Protocolo.IMG_MOVE:
                campo = payLoad.split(":");
                Principal.moverFiguraAreaEdicao(Integer.parseInt(campo[0]),Integer.parseInt(campo[1]),Integer.parseInt(campo[2]));
                System.out.println("Movendo Figura id=" + campo[0]);
                break;
                
            case Protocolo.PNL_MOVP:
                campo = payLoad.split(":");
                Principal.movePontaMouseSemFigura(campo[0],Integer.parseInt(campo[1]), Integer.parseInt(campo[2]));
                break;
                
            case Protocolo.PNL_MOVF:
                campo = payLoad.split(":");
                Principal.movePontaMouseComFigura(campo[0],Integer.parseInt(campo[1]), Integer.parseInt(campo[2]));
                break;
                
            case Protocolo.PNL_DRGF:
                campo = payLoad.split(":");
                Principal.movePontaMouseComFiguraDrag(campo[0],Integer.parseInt(campo[1]), Integer.parseInt(campo[2]));
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
    public void conectar(String servidor, String porta){
        try {
            InetAddress endereco = InetAddress.getByName(servidor);
            cliente = new Socket(endereco, Integer.parseInt(porta));
            in =  new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());           
        } catch (Exception e) {
            //Servidor fora do ar, avisa ao usuário
            Principal.alertaServidorOff();
        } 
        //Se conectou, inicia a thread
        new ClienteLerThread().start();      
    }
    
    public void iniciaThread (){
        new ClienteLerThread().start();
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
    
    
    public void enviarMensagemEdicao(String protocolo,String msg){
        try {
            out.writeUTF(protocolo+msg);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
