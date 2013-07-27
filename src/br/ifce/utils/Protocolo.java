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
    
    public static final String IMG_REMO = "IMG_RQUA"; //Remove uma figura    
    public static final String IMG_MOVE = "IMG_MOVE"; //Move uma figura
    
    
}
