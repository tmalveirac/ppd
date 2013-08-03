package br.ifce.utils;

/**
 * Classe: Figura.java
 * Define o componente Figura geométrica
 * @author Tiago Malveira
 * 
 */

import br.ifce.ppd.control.Cliente;
import br.ifce.ppd.view.Principal;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Figura extends JLabel{
    
    private ImageIcon imageIcon;
    private JPanel jPanel;
    private Cliente cliente;
    private int id;
    
    public Figura (ImageIcon imageIcon, int id, int x, int y){
        this.imageIcon = imageIcon;
        this.jPanel = Principal.pnlAreaEdicao;
        this.id=id;
        setIcon(imageIcon);
        //setBounds(10, 10, 50, 50);
        setBounds(x, y, 50, 50);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void removerFigura(){
        jPanel.remove(Figura.this);
        jPanel.repaint();
    }

    public int getId() {
        return id;
    }
    
}
