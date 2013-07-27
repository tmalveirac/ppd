/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifce.utils;

import br.ifce.ppd.multi.Cliente;
import br.ifce.ppd.view.Principal;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author malveira
 */
public class Figura extends JLabel{
    
    private ImageIcon imageIcon;
    private JPanel jPanel;
    private Cliente cliente;
    private int id;
    
    public Figura (ImageIcon imageIcon, int id){
        this.imageIcon = imageIcon;
        this.jPanel = Principal.pnlAreaEdicao;
        this.id=id;
        setIcon(imageIcon);
        setBounds(10, 10, 50, 50);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseMotionListener(getMouseMotionListener());
        addMouseListener(getMouseListener());
        
        
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
    
    private MouseListener getMouseListener() {
        MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu menu = new JPopupMenu();  
                    
                    JMenuItem apagar = new JMenuItem("Apagar");  
                    apagar.addActionListener(new ActionListener() {  
                        @Override
                        public void actionPerformed(ActionEvent ae) {  
                            jPanel.remove(Figura.this);
                            jPanel.repaint();
                            
                        }  
                    });  
                    menu.add(apagar); 
                    
                    menu.show(Figura.this, me.getX(), me.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        };
        
        return mouseListener;
    }
    
    public void removerFigura(){
        jPanel.remove(Figura.this);
        jPanel.repaint();
    }

    public int getId() {
        return id;
    }
    
}
