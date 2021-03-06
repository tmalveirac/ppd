package br.ifce.ppd.view;

/**
 * Classe: Principal.java
 * Código View da janela Principal da Aplicação
 * @author Tiago Malveira
 * 
 */

import br.ifce.ppd.control.Cliente;
import br.ifce.utils.Figura;
import br.ifce.utils.FiguraFactory;
import br.ifce.utils.Protocolo;
import br.ifce.utils.TipoFigura;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class Principal extends javax.swing.JFrame {

    public Principal(String login, String servidor, String porta) {
        initComponents();
        inicializar();
        cliente = new Cliente();
        cliente.conectar(servidor,porta);
        cliente.enviarMensagem(Protocolo.CHAT_INS,login);   
        this.login=login;    
        this.setVisible(true);
    }
    
    /**
    * alerta do Servidor se login foi alterado
    *             
    * @param login         novo login
    * @return              void
    */    
    public static void alertaServidorIfLoginAlterado(String login){  
        JOptionPane.showMessageDialog(
        null,
        "Nome de usuário já existe. Seu login foi alterado para: " + login,
        "Atenção",
        JOptionPane.WARNING_MESSAGE);      
    }
    
    /**
    * alerta de servidor inativo
    *             
    * @return              void
    */
    public static void alertaServidorOff(){
        JOptionPane.showMessageDialog(
                null,
                "O servidor não está respondendo. Tente novamente mais tarde.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }

    /**
    * Escreve mensagem no Chat
    *             
    * @param msg    mensagem a ser inserida no chat
    * @return       void
    */
    public static void escreveMensagemChat(String msg) {
        txtAreaChat.append(msg + "\n");
	txtAreaChat.setCaretPosition(txtAreaChat.getDocument().getLength());
    }
    
    /**
    * Insere um nome na lista de login do Chat
    *             
    * @param nome   nome a ser inserido na lista do chat
    * @return       void
    */
    public static void insereListaChat(String nome){
        if (idNomeListaChat(nome) == -1) {
            listModel.addElement(nome);
            listViewChat.setModel(listModel);
        }
        
    }    
    
    /**
    * Remove um nome da lista do Chat
    *             
    * @param nome   nome a ser removido
    * @return       void
    */
    public static void removeListaChat(String nome){
        if (idNomeListaChat(nome)!=-1){
            listModel.remove(idNomeListaChat(nome));
            listViewChat.setModel(listModel);   
        } 
       
    }
    
    /**
    * Identifica o id de um nome na lista do chat
    *             
    * @param    nome   nome a ser buscado na lista de login do chat
    * @return   int   indice i do nome da lista, se existir. -1, caso contrário 
    */
    public static int idNomeListaChat(String nome){
        
        for (int i=0; i<listModel.getSize();i++){
            if (listModel.get(i).toString().equals(nome)){
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlChat = new javax.swing.JPanel();
        txtFieldChat = new javax.swing.JTextField();
        btnEnviarChat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaChat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        listViewChat = new javax.swing.JList();
        pnlElementos = new javax.swing.JPanel();
        labQuadrado = new javax.swing.JLabel();
        labCirculo = new javax.swing.JLabel();
        labTriangulo = new javax.swing.JLabel();
        pnlAreaEdicao = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuSair = new javax.swing.JMenuItem();
        menuSobre = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlChat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chat", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        txtFieldChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldChatActionPerformed(evt);
            }
        });

        btnEnviarChat.setText("Enviar");
        btnEnviarChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarChatActionPerformed(evt);
            }
        });

        txtAreaChat.setEditable(false);
        txtAreaChat.setColumns(10);
        txtAreaChat.setRows(5);
        txtAreaChat.setMinimumSize(new java.awt.Dimension(0, 5));
        jScrollPane1.setViewportView(txtAreaChat);

        jScrollPane2.setViewportView(listViewChat);

        javax.swing.GroupLayout pnlChatLayout = new javax.swing.GroupLayout(pnlChat);
        pnlChat.setLayout(pnlChatLayout);
        pnlChatLayout.setHorizontalGroup(
            pnlChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChatLayout.createSequentialGroup()
                .addComponent(txtFieldChat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviarChat))
            .addGroup(pnlChatLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlChatLayout.setVerticalGroup(
            pnlChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChatLayout.createSequentialGroup()
                .addGroup(pnlChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarChat)))
        );

        pnlElementos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Objetos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        //labQuadrado = new JLabel(br.ifce.utils.FiguraFactory.QUADRADO());
        labQuadrado.setIcon(br.ifce.utils.FiguraFactory.QUADRADO());
        labQuadrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labQuadradoMouseClicked(evt);
            }
        });

        labCirculo.setIcon(br.ifce.utils.FiguraFactory.CIRCULO());
        labCirculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labCirculoMouseClicked(evt);
            }
        });

        labTriangulo.setIcon(br.ifce.utils.FiguraFactory.TRIANGULO());
        labTriangulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labTrianguloMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlElementosLayout = new javax.swing.GroupLayout(pnlElementos);
        pnlElementos.setLayout(pnlElementosLayout);
        pnlElementosLayout.setHorizontalGroup(
            pnlElementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlElementosLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(pnlElementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labQuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlElementosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labTriangulo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        pnlElementosLayout.setVerticalGroup(
            pnlElementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlElementosLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(labQuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(labCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(labTriangulo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pnlAreaEdicao.setBackground(java.awt.Color.white);
        pnlAreaEdicao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Área de Edição", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        pnlAreaEdicao.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                pnlAreaEdicaoMouseMoved(evt);
            }
        });
        pnlAreaEdicao.setLayout(null);

        jMenu1.setText("Arquivo");

        menuSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        menuSair.setText("Sair");
        menuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSairActionPerformed(evt);
            }
        });
        jMenu1.add(menuSair);

        jMenuBar1.add(jMenu1);

        menuSobre.setText("Sobre");
        menuSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSobreActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlElementos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAreaEdicao, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlAreaEdicao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlElementos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarChatActionPerformed
        cliente.enviarMensagem(Protocolo.CHAT_MSG,txtFieldChat.getText()); 
        txtFieldChat.setText("");
    }//GEN-LAST:event_btnEnviarChatActionPerformed

    private void txtFieldChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldChatActionPerformed
       btnEnviarChatActionPerformed(evt);
    }//GEN-LAST:event_txtFieldChatActionPerformed

    private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSairActionPerformed
        //Finaliza o cliente
        sair();
    }//GEN-LAST:event_menuSairActionPerformed

    private void labQuadradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labQuadradoMouseClicked
        //Evento duplo clique para criar a figura
        if (evt.getClickCount() == 2) {  
            cliente.enviarMensagem(Protocolo.IMG_CQUA, "");
        }
    }//GEN-LAST:event_labQuadradoMouseClicked

    private void labCirculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labCirculoMouseClicked
        //Evento duplo clique para criar a figura
        if (evt.getClickCount() == 2) {           
            cliente.enviarMensagem(Protocolo.IMG_CCIR, "");
        }
    }//GEN-LAST:event_labCirculoMouseClicked

    private void labTrianguloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labTrianguloMouseClicked
        //Evento duplo clique para criar a figura
        if (evt.getClickCount() == 2) {
            cliente.enviarMensagem(Protocolo.IMG_CTRI, "");
        }
    }//GEN-LAST:event_labTrianguloMouseClicked

    private void pnlAreaEdicaoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAreaEdicaoMouseMoved
        //Movendo figura
        int x = evt.getX();
        int y = evt.getY();
        String payload = x+":"+y;
        cliente.enviarMensagem(Protocolo.PNL_MOVP, payload);
        System.out.println("Movendo Mouse sobre o Painel");
    }//GEN-LAST:event_pnlAreaEdicaoMouseMoved

    //Menu Sobre
    private void menuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSobreActionPerformed
        JOptionPane.showMessageDialog(null,""
                + "Editor Colaborativo "
                + "Engenharia de Computação - PPD"
                + "Professor: Cidcley Teixeira"
                + "Autor: Tiago Malveira Cavalcante"
                + "","Sobre",JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_menuSobreActionPerformed
    
     /**
    * Envia uma mensagem de saída para o Servidor e termina 
    *             
    * @return       void
    */
    public static void finalizar() {
        JOptionPane.showMessageDialog(
                null,
                "O servidor parou de responder. O programa será finalizado.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
    
    /**
    * Cria a ponta do mouse de cliente que entra na aplicação
    *             
    * @param    login   loging do cliente
    * @return   void   
    */
    public static void criaPontaMouse(String login){
        JLabel l = new JLabel();
        l.setBounds(10, 10, 200, 40);
        l.setIcon(new ImageIcon(Principal.class.getClassLoader()
				.getResource("mouse.png")));
       
        mouseVector.add(l);
        l.setText(login);
        l.setVisible(false);
        pnlAreaEdicao.add(l);
        pnlAreaEdicao.repaint();
    }
    
    /**
    * Remove a ponta do mouse de cliente que sai na aplicação
    *             
    * @param    login   loging do cliente
    * @return   void   
    */
    public static void removePontaMouse(String login){
        int i=0;
        int mouseRemovido=-1;
        
        for (JLabel l : mouseVector){
            if (l.getText().equals(login)){
                pnlAreaEdicao.remove(l);
                mouseRemovido=i;
            }
            i++;
        }
        
        if (mouseRemovido!=-1){
            mouseVector.remove(mouseRemovido);
        }
        
    }
    
    /**
    * Move a ponta do mouse do cliente sobre o painel
    *             
    * @param    login   login do cliente
    * @param    x       posição do eixo X   
    * @param    y       posição do eixo y
    * @return   void   
    */
    public static void movePontaMouseSemFigura(String login, int x, int y){
        for (JLabel l : mouseVector){
            if (l.getText().equals(login)){
                if (x<=15 || y<=18){
                    l.setVisible(false);
                }
                else{
                    if (x>512 || y>455){
                        l.setVisible(false);
                    }
                    else{
                        l.setLocation(x, y);
                        l.setVisible(true);  
                    }                                 
                }
            }
        }
    }
    
    /**
    * Move a ponta do mouse do cliente sobre o painel e figura
    *             
    * @param    login   login do cliente
    * @param    x       posição do eixo X   
    * @param    y       posição do eixo y
    * @return   void   
    */
    public static void movePontaMouseComFigura(String login, int x, int y){
        for (JLabel l : mouseVector){
            if (l.getText().equals(login)){
                l.setLocation(x, y);
                l.setVisible(true);
            }
        }
    }
    
    /**
    * Move a ponta do mouse do cliente sobre o painel e arrastando figura
    *             
    * @param    login   login do cliente
    * @param    x       posição do eixo X   
    * @param    y       posição do eixo y
    * @return   void   
    */
    public static void movePontaMouseComFiguraDrag(String login, int x, int y){
        for (JLabel l : mouseVector){
            if (l.getText().equals(login)){
                
                if (x > pnlAreaEdicao.getBounds().width - 55) {
                    x = pnlAreaEdicao.getBounds().width - 55;
                } else if (x < 5) {
                    x = 5;
                }
                if (y > pnlAreaEdicao.getBounds().height - 55) {
                    y = pnlAreaEdicao.getBounds().height - 55;
                } else if (y < 15) {
                    y = 15;
                }
                
                l.setLocation(x, y);
                l.setVisible(true);
            }
        }
    }
    
    /**
    * Cria uma figura na área de edição
    *             
    * @param    tipoFigura  tipo da figura a ser criada
    * @param    id          identificação da figura
    * @param    x           posição do eixo X   
    * @param    y           posição do eixo y
    * @return   void   
    */
    public static void criarFiguraAreaEdicao(int tipoFigura, int id, int x, int y){
        Figura f;
        
        switch(tipoFigura){        
            case TipoFigura.QUADRADO:
                f = new Figura(FiguraFactory.QUADRADO(), id, x, y);
                pnlAreaEdicao.add(f);
                pnlAreaEdicao.repaint();
                figuraVector.add(f);
                addEventosFigura(f);
                break;
                
            case TipoFigura.CIRCULO:
                f = new Figura(FiguraFactory.CIRCULO(), id, x, y);
                pnlAreaEdicao.add(f);
                pnlAreaEdicao.repaint();
                figuraVector.add(f);
                addEventosFigura(f);
                break;
                
            case TipoFigura.TRIANGULO:
                f = new Figura(FiguraFactory.TRIANGULO(), id, x, y);
                
                pnlAreaEdicao.add(f);
                pnlAreaEdicao.repaint();
                figuraVector.add(f);
                addEventosFigura(f);
                break;
        }
    }  
    
    /**
    * Adiciona eventos às figuras
    *             
    * @param    f       figura 
    * @return   void   
    */
    public static void addEventosFigura(final Figura f){
        f.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu menu = new JPopupMenu();  
                    
                    JMenuItem apagar = new JMenuItem("Apagar");  
                    apagar.addActionListener(new ActionListener() {  
                        @Override
                        public void actionPerformed(ActionEvent ae) {       
                            cliente.enviarMensagem(Protocolo.IMG_REMO, 
                                    ""+f.getId());
                        }  
                    });  
                    menu.add(apagar); 
                    
                    menu.show(f, me.getX(), me.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                System.out.println("SOLTOU MOUSE");
                //Move ponta do mouse enquanto está arrastando
                int x= me.getXOnScreen() + f.getLocation().x;
                int y= me.getYOnScreen() + f.getLocation().y;
                String payload = x+":"+y;
                
                System.err.println("Location x= " + me.getXOnScreen() + " y = " + me.getYOnScreen());
                cliente.enviarMensagem(Protocolo.PNL_MOVP, payload);            
            }

            @Override
            public void mouseEntered(MouseEvent me) {
 
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
        
        f.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent me) {
                int x = (me.getX() + f.getBounds().x) - 10 / 2;
                int y = (me.getY() + f.getBounds().y) - 10 / 2;
                
                //moverFiguraAreaEdicao(f.getId(), x, y); // Se o servidor não manda pra mim
                String payload = f.getId()+":"+x+":"+y;  //Formato: id:x:y: -> 10:2:3 
                cliente.enviarMensagem(Protocolo.IMG_MOVE, payload);                
                
                //Move ponta do mouse enquanto está arrastando
                x= me.getX() + f.getLocation().x;
                y= me.getY() + f.getLocation().y;
                payload = x+":"+y;
                
                System.err.println("Location x= " + me.getXOnScreen() + " y = " + me.getYOnScreen());
                cliente.enviarMensagem(Protocolo.PNL_DRGF, payload);
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                
                int x = me.getX() + f.getLocation().x;
                int y = me.getY() + f.getLocation().y;
                String payload = x+":"+y;
                cliente.enviarMensagem(Protocolo.PNL_MOVF, payload);
                System.out.println("Movendo Mouse sobre uma figura");
            }
        });  
    }
    
    /**
    * Move uma figura na área de edição
    *             
    * @param    id          identificação da figura
    * @param    x           posição do eixo X   
    * @param    y           posição do eixo y
    * @return   void   
    */
    public static void moverFiguraAreaEdicao(int id, int x, int y){
         for (Figura f : figuraVector){
            if (f.getId()==id){
                 
                if (x > pnlAreaEdicao.getBounds().width - 55) {
                    x = pnlAreaEdicao.getBounds().width - 55;
                } else if (x < 5) {
                    x = 5;
                }
                if (y > pnlAreaEdicao.getBounds().height - 55) {
                    y = pnlAreaEdicao.getBounds().height - 55;
                } else if (y < 15) {
                    y = 15;
                }
                
                f.setLocation(x, y);
            }
           
        }
    }
    
    /**
    * Identifica o indice de uma figura no vector de figuras
    *             
    * @param    id          identificação da figura
    * @return   int         índice da figura no Vector, se existir. -1, caso contrário   
    */
    public static int indiceByIdFigura(int id){
        int i=0;
        
        for (Figura f : figuraVector){
            if (f.getId()==id){
                return i;
            }
            i++;
        }
        
        return -1;
    }
    
    /**
    * Remove uma figura na área de edição
    *             
    * @param    id          identificação da figura
    * @return   void   
    */
     public static void removerFiguraAreaEdicao(int id){
        int indiceFiguraRemovida=-1; 
        int i=0;
        
        for (Figura f : figuraVector){
            if (f.getId() == id){
                indiceFiguraRemovida=i;
                break;
            }
            i++;
        }
            
        if (indiceFiguraRemovida!=-1){
            pnlAreaEdicao.remove(figuraVector.get(indiceFiguraRemovida));
            pnlAreaEdicao.repaint();
            figuraVector.remove(indiceFiguraRemovida);
            System.out.println("Removeu imagem da lista " + indiceFiguraRemovida);
        }
    } 
    
    /**
    * Envia uma mensagem de saída para o Servidor e termina 
    *             
    * @return       void
    */
    public void sair() {
        //Envia mensagem SAI para o servidor
        cliente.enviarMensagem(Protocolo.CHAT_SAI,"");
        System.exit(0);
    }
    
    /**
    * Código para Tratar evento Fechar Janela pelo X 
    *             
    * @return       void
    */
    private void inicializar() {
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent we) {
            if (we.getID() == WindowEvent.WINDOW_CLOSING) {
                sair();
            }
            }

            @Override
            public void windowClosed(WindowEvent we) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent we) {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent we) {
                 //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
                //To change body of generated methods, choose Tools | Templates.
            }      
        });   
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarChat;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labCirculo;
    private javax.swing.JLabel labQuadrado;
    private javax.swing.JLabel labTriangulo;
    private static javax.swing.JList listViewChat;
    private javax.swing.JMenuItem menuSair;
    private javax.swing.JMenu menuSobre;
    public static javax.swing.JPanel pnlAreaEdicao;
    private javax.swing.JPanel pnlChat;
    private javax.swing.JPanel pnlElementos;
    private static javax.swing.JTextArea txtAreaChat;
    private javax.swing.JTextField txtFieldChat;
    // End of variables declaration//GEN-END:variables
    private static Cliente cliente;
    private String login;
    //Model utilizado para atualizar lista de logins na tela
    private static DefaultListModel  listModel = new DefaultListModel(); 
    //Vector de figuras na área de edição
    private static Vector<Figura> figuraVector = new Vector<Figura>();
    //Vector de laber que representam os mouses dos cliente ativos na área de edição
    private static Vector<JLabel> mouseVector = new Vector<JLabel>();
}
