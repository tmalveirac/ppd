package br.ifce.utils;

/**
 * Classe: Protocolo.java
 * Define o protocolo de comunicação utilizado entre Cliente/Servidor
 * @author malveira
 * 
 */

public class Protocolo {
    
    //Cada comando possui 8 caracteres
    public static final String CHAT_MSG = "CHAT_MSG"; //Uma mensagem normal do chat
    public static final String CHAT_INS = "CHAT_INS"; //Inserir usuario no chat
    public static final String CHAT_REM = "CHAT_REM"; //Remover usuario no chat
    public static final String CHAT_NOT = "CHAT_NOT"; //Notificação do chat
    public static final String CHAT_SAI = "CHAT_SAI"; //Sair do chat
}
