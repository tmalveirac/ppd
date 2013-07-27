package br.ifce.utils;

/**
 *
 * @author malveira
 */
public class Imagem {
    private int id;
    private int tipoFigura;  
    private int posX;
    private int posY;

    public Imagem(int id, int tipoFigura, int posX, int posY) {
        this.id = id;
        this.tipoFigura = tipoFigura;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoFigura() {
        return tipoFigura;
    }

    public void setTipoFigura(int tipoFigura) {
        this.tipoFigura = tipoFigura;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    
    
    
}
