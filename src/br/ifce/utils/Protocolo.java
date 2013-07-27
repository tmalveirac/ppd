package br.ifce.utils;

/**
 * Classe: Protocolo.java
 * Define o protocolo de comunicação utilizado entre Cliente/Servidor
 * @author Tiago Malveira
 * 
 */

public class Protocolo {
    
    //Cada comando possui 8 caracteres
    
    //Comandos relativos ao Chat
    public static final String CHAT_MSG = "CHAT_MSG"; //Uma mensagem normal do chat
    public static final String CHAT_INS = "CHAT_INS"; //Inserir usuario no chat
    public static final String CHAT_REM = "CHAT_REM"; //Remover usuario no chat
    public static final String CHAT_NOT = "CHAT_NOT"; //Notificação do chat
    public static final String CHAT_SAI = "CHAT_SAI"; //Sair do chat
    
    
    //Comandos relativos à edição de imagens
    public static final String IMG_CQUA = "IMG_CQUA"; //Cria um quadrado
    public static final String IMG_CTRI = "IMG_CTRI"; //Cria um triângulo
    public static final String IMG_CCIR = "IMG_CCIR"; //Cria um círculo
    
    public static final String IMG_RQUA = "IMG_RQUA"; //Remove um quadrado
    public static final String IMG_RTRI = "IMG_RTRI"; //Remove um triângulo
    public static final String IMG_RCIR = "IMG_RCIR"; //Remove um círculo
    
    public static final String IMG_MQUA = "IMG_MQUA"; //Move um quadrado
    public static final String IMG_MTRI = "IMG_MTRI"; //Move um triângulo
    public static final String IMG_MCIR = "IMG_MCIR"; //Move um círculo
    
}
