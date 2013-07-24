/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifce.utils;

import br.ifce.ppd.view.Principal;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author malveira
 */
public class Figura extends JLabel{
    
    private ImageIcon imageIcon;
    private JPanel jPanel;
    
    public Figura (ImageIcon imageIcon){
        this.imageIcon = imageIcon;
        this.jPanel = Principal.pnlAreaEdicao;
        setIcon(imageIcon);
        setBounds(40, 40, 50, 50);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseMotionListener(getMouseMotionListener());
        
    }
    
    private MouseMotionListener getMouseMotionListener() {
        MouseMotionListener mouseMotionListener = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent me) {
                int x = (me.getX() + getBounds().x) - 10 / 2;
                int y = (me.getY() + getBounds().y) - 10 / 2;
                if (x > jPanel.getBounds().width - 55) {
                    x = jPanel.getBounds().width - 55;
                } else if (x < 5) {
                    x = 5;
                }
                if (y > jPanel.getBounds().height - 55) {
                    y = jPanel.getBounds().height - 55;
                } else if (y < 15) {
                    y = 15;
                }
                setLocation(x, y);
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                
            }
        };
        
        return mouseMotionListener;
    }    
    
}
