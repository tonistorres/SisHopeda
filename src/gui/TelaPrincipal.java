package gui;

import jbdc.ConexaoUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import metodostatics.SerialUtils;

public class TelaPrincipal extends javax.swing.JFrame {

    Connection connection;// variável globla 

    gui.VeiwCadUsuario VeiwCadUsuario;
    gui.VeiwCapturaInfoMaquina VeiwCapturaInfoMaquina;
    /**
     * Código Mestre Chimura
     */
    private static VeiwCadUsuario instance = null;

    public static VeiwCadUsuario getInstance() {

        if (instance == null) {

            instance = new VeiwCadUsuario();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    //Hugo vasconcelos Aula 25 - verificando Janelas Abertas
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555436?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555438?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555444?start=0
    //agroa criamos o método que irá fazer a verificaçao e passamos como parâmetros um obj do tipo Object 
    public boolean estaFechado(Object obj) {

        /**
         * Criei um objeto chamado ativo do tipo JInternalFrame que está
         * inicializado como um array (array é um objeto que guarda vários
         * elementos detro) Pergunta-se: quais são os objetos todas as janelas
         * que são carregadas pelo objeto carregador. Bem o objeto ativo irá
         * guardar todas as janelas que estão sendo abertas no sistema
         */
        JInternalFrame[] ativo = DesktopPanelPrincipal.getAllFrames();
        boolean fechado = true;
        int cont = 0;

        while (cont < ativo.length && fechado) {
            if (ativo[cont] == obj) {
                fechado = false;

            }
            cont++;
        }
        return fechado;
    }

    public TelaPrincipal() {
        initComponents();
         personalizarFrontEnd();
      capturaTecla();

        try {
            this.connection = ConexaoUtil.getInstance().getConnection();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar se Conectar: \n"
                    + "O sistema não conseguiu comunicação com o Banco MySQL\n"
                    + "Analista de Sistemas Tonis Alberto Torres  Ferreira: \n"
                    + "sisvenda2011@gmail.com | Porfavor tire um Print do Erro\n"
                    + "Sistema será fechado Automaticamente.\n" + ex.getMessage());
            /* Esse método fecha a janela atual */
            System.exit(0);
        }

        //  this.setExtendedState(MAXIMIZED_BOTH);// propriedade para maximizar tela tanto na vertical quanto na horizontal Canal:https://www.youtube.com/watch?v=-Y2rpyIWj9c
        //desabilitando o botão maximizar 
        //https://pt.stackoverflow.com/questions/345879/desativar-botao-maximizar-do-jframe
        this.setResizable(false);

        // Desabilitar o botão fechar de um JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);//desabilita o botão fechar da TelaPrincipal 
    }


private void capturaTecla(){
      //https://www.youtube.com/watch?v=8wmMY7AUtxQ
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                              
                // capturando o código da tecla pressionada pelo usuario 
                int codigo = e.getKeyCode();
                
                // como saber o codigo que eu quero 
                int tecla = KeyEvent.VK_ENTER;
                
               if(tecla==codigo ){
                    System.out.println("Tecla Entrou" +tecla);
                    
               }
                              
            }

        });
}



    private void personalizarFrontEnd() {

        lblImgData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/date.png")));
        lblImgHora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/time.png")));
        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/userNovo.png")));
//        btnMaquina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/note.png")));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/exit.png")));
        lblVerificacao.setForeground(Color.WHITE);
        lblVerificacao.setVisible(false);
        barraProgresso.setVisible(false);
        capturandoData();
        criarDataHoraBarraStatus();
    }

    private void criarDataHoraBarraStatus() {
        //https://www.youtube.com/watch?v=55CgbuWnmNc
        Date dataDoSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        lblStatusData.setText(formato.format(dataDoSistema));

        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblStatusData.setForeground(Color.RED);
        lblStatusData.setFont(f);

        Timer timer = new Timer(1000, new hora());
        timer.start();
    }

    private void capturandoData() {
        /**
         * Esse evento executa o código quando o formulário é aberto
         */
        Date data = new Date();

        /**
         * Dateformat para passar uma formatação para o objeto Date
         */
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
        lblDataSistema.setText(formatador.format(data));

    }

    private void sairAplicacao() {
        /*Evento ao ser clicado executa código*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Sair do Sistema?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (sair == JOptionPane.YES_OPTION) {

            barraProgresso.setVisible(true);
            lblVerificacao.setVisible(true);

            new Thread() {

                public void run() {

                    barraProgresso.setVisible(true);
                    lblVerificacao.setVisible(true);

                    for (int i = 0; i < 101; i++) {

                        try {

                            sleep(50);
                            barraProgresso.setValue(i);

                            if (barraProgresso.getValue() <= 5) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("5% descarrega módulos 1");

                            } else if (barraProgresso.getValue() <= 15) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("15% Descarrega Módulo 2");

                            } else if (barraProgresso.getValue() <= 25) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("25% Descarrega Módulo 3");

                            } else if (barraProgresso.getValue() <= 35) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("35% descarrega Módulo 4");

                            } else if (barraProgresso.getValue() <= 45) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("45% Descarrega Módulo 5");

                            } else if (barraProgresso.getValue() <= 55) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("55% Descarrega Módulo 6");

                            } else if (barraProgresso.getValue() <= 65) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("65% Descarrega Módulo 7");

                            } else if (barraProgresso.getValue() <= 75) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("75% Descarrega Módulo 8");

                            } else if (barraProgresso.getValue() <= 85) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("85% descarrega Módulo 9");

                            } else if (barraProgresso.getValue() <= 95) {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("95% descarrega Módulo 10");

                            } else {
                                lblVerificacao.setForeground(new Color(25,31,38));
                                lblVerificacao.setText("Encerrado com sucesso!");
                                System.exit(0);//sair do sistema
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                        }

                    }
                }
            }.start();// iniciando a Thread

        }

    }

    private void subMenusTornarInvivel() {

        /**
         * Menu Cadastro
         */
        subMenuCapMaquina.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopPanelPrincipal = new javax.swing.JDesktopPane();
        painelBarraStatus = new javax.swing.JPanel();
        lblStatusData = new javax.swing.JLabel();
        lblStatusHora = new javax.swing.JLabel();
        lblImagemData = new javax.swing.JLabel();
        lblImagemHora = new javax.swing.JLabel();
        PanelInfo = new javax.swing.JPanel();
        lblContainerCelular = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        lblNomeCompletoUsuario = new javax.swing.JLabel();
        lblPerfilInfoExterna = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblNomeInfoExterno = new javax.swing.JLabel();
        lblImagemUser = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblJavaInterface = new javax.swing.JLabel();
        lblVerificacao = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        lblImgData = new javax.swing.JLabel();
        lblDataSistema = new javax.swing.JLabel();
        lblImgHora = new javax.swing.JLabel();
        lblHoraDoSistema = new javax.swing.JLabel();
        carregador1 = new gui.Carregador();
        jPanel2 = new javax.swing.JPanel();
        btnUsuario = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnCaptureInfo = new javax.swing.JButton();
        menuSistema = new javax.swing.JMenuBar();
        menuCadastros = new javax.swing.JMenu();
        subMenuCadUsuariosTI = new javax.swing.JMenuItem();
        subMenuCapMaquina = new javax.swing.JMenuItem();
        menuSobre = new javax.swing.JMenu();
        subMenuSobre = new javax.swing.JMenuItem();
        menuSair = new javax.swing.JMenu();
        subMenuSairSistema = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Module: Machine Registration");
        setModalExclusionType(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        DesktopPanelPrincipal.setBackground(new java.awt.Color(9, 81, 107));
        DesktopPanelPrincipal.setForeground(new java.awt.Color(255, 255, 255));

        painelBarraStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelBarraStatus.add(lblStatusData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 0, 180, 30));
        painelBarraStatus.add(lblStatusHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(1510, 0, 200, 30));
        painelBarraStatus.add(lblImagemData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 20, 30));
        painelBarraStatus.add(lblImagemHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 0, 20, 30));

        DesktopPanelPrincipal.add(painelBarraStatus);
        painelBarraStatus.setBounds(0, 870, 1750, 30);

        PanelInfo.setBackground(new java.awt.Color(25, 31, 38));
        PanelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações do Usuário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(102, 211, 236))); // NOI18N
        PanelInfo.setLayout(null);

        lblContainerCelular.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        lblContainerCelular.setForeground(new java.awt.Color(102, 211, 236));
        lblContainerCelular.setText("celular");
        PanelInfo.add(lblContainerCelular);
        lblContainerCelular.setBounds(70, 195, 120, 20);

        lblPerfil.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        lblPerfil.setForeground(new java.awt.Color(102, 211, 236));
        lblPerfil.setText("perfil");
        PanelInfo.add(lblPerfil);
        lblPerfil.setBounds(60, 170, 120, 17);

        lblNomeCompletoUsuario.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        lblNomeCompletoUsuario.setForeground(new java.awt.Color(102, 211, 236));
        lblNomeCompletoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeCompletoUsuario.setText("nomeCompleto");
        PanelInfo.add(lblNomeCompletoUsuario);
        lblNomeCompletoUsuario.setBounds(10, 240, 180, 20);

        lblPerfilInfoExterna.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPerfilInfoExterna.setForeground(new java.awt.Color(102, 211, 236));
        lblPerfilInfoExterna.setText("Perfil:");
        PanelInfo.add(lblPerfilInfoExterna);
        lblPerfilInfoExterna.setBounds(10, 170, 50, 17);

        lblCelular.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblCelular.setForeground(new java.awt.Color(102, 211, 236));
        lblCelular.setText("Celular:");
        PanelInfo.add(lblCelular);
        lblCelular.setBounds(10, 195, 60, 17);

        lblNomeInfoExterno.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblNomeInfoExterno.setForeground(new java.awt.Color(102, 211, 236));
        lblNomeInfoExterno.setText("Nome:");
        PanelInfo.add(lblNomeInfoExterno);
        lblNomeInfoExterno.setBounds(10, 220, 48, 17);

        lblImagemUser.setBackground(new java.awt.Color(102, 211, 236));
        lblImagemUser.setForeground(new java.awt.Color(102, 211, 236));
        lblImagemUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagemUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelInfo.add(lblImagemUser);
        lblImagemUser.setBounds(40, 30, 130, 120);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Robot:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJavaInterface.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJavaInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/javaLogoTelaPrincipal.gif"))); // NOI18N
        jPanel1.add(lblJavaInterface, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, -1));

        lblVerificacao.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        lblVerificacao.setForeground(java.awt.Color.white);
        lblVerificacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 180, 30));

        jPanel1.add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 200, -1));

        lblImgData.setForeground(new java.awt.Color(102, 211, 236));
        lblImgData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImgData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblImgData, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 34, 34));

        lblDataSistema.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDataSistema.setForeground(new java.awt.Color(25, 31, 38));
        lblDataSistema.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDataSistema.setText("Data");
        jPanel1.add(lblDataSistema, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 150, 30));

        lblImgHora.setForeground(new java.awt.Color(102, 211, 236));
        lblImgHora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblImgHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 32, 32));

        lblHoraDoSistema.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHoraDoSistema.setForeground(new java.awt.Color(25, 31, 38));
        lblHoraDoSistema.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoraDoSistema.setText("Hora");
        jPanel1.add(lblHoraDoSistema, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 150, 30));

        PanelInfo.add(jPanel1);
        jPanel1.setBounds(5, 280, 210, 290);

        DesktopPanelPrincipal.add(PanelInfo);
        PanelInfo.setBounds(750, 0, 210, 570);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/userNovo.png"))); // NOI18N
        btnUsuario.setToolTipText("Cadastro do Usuário");
        btnUsuario.setFocusable(false);
        btnUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuario.setPreferredSize(new java.awt.Dimension(42, 42));
        btnUsuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });
        jPanel2.add(btnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/exit.png"))); // NOI18N
        btnSair.setFocusable(false);
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSair.setPreferredSize(new java.awt.Dimension(42, 42));
        btnSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel2.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 40, 40));

        btnCaptureInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/linux.png"))); // NOI18N
        btnCaptureInfo.setPreferredSize(new java.awt.Dimension(42, 42));
        btnCaptureInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureInfoActionPerformed(evt);
            }
        });
        jPanel2.add(btnCaptureInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 40, 40));

        carregador1.add(jPanel2);
        jPanel2.setBounds(0, 0, 750, 42);

        DesktopPanelPrincipal.add(carregador1);
        carregador1.setBounds(0, 0, 750, 570);

        getContentPane().add(DesktopPanelPrincipal);
        DesktopPanelPrincipal.setBounds(0, 0, 960, 570);

        menuSistema.setToolTipText("");
        menuSistema.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        menuCadastros.setBackground(new java.awt.Color(255, 255, 255));
        menuCadastros.setText("Cadastro");
        menuCadastros.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        subMenuCadUsuariosTI.setText("Usuarios Nível Administrativo");
        subMenuCadUsuariosTI.setEnabled(false);
        subMenuCadUsuariosTI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuCadUsuariosTIActionPerformed(evt);
            }
        });
        menuCadastros.add(subMenuCadUsuariosTI);

        subMenuCapMaquina.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_DOWN_MASK));
        subMenuCapMaquina.setText("Cadastrar Maquina ");
        subMenuCapMaquina.setEnabled(false);
        subMenuCapMaquina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuCapMaquinaActionPerformed(evt);
            }
        });
        menuCadastros.add(subMenuCapMaquina);

        menuSistema.add(menuCadastros);

        menuSobre.setText("Ajuda");

        subMenuSobre.setText("Sobre");
        subMenuSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuSobreActionPerformed(evt);
            }
        });
        menuSobre.add(subMenuSobre);

        menuSistema.add(menuSobre);

        menuSair.setText("Sair");
        menuSair.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N

        subMenuSairSistema.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_DOWN_MASK));
        subMenuSairSistema.setText("Sistema");
        subMenuSairSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuSairSistemaActionPerformed(evt);
            }
        });
        menuSair.add(subMenuSairSistema);

        menuSistema.add(menuSair);

        setJMenuBar(menuSistema);

        getAccessibleContext().setAccessibleParent(this);

        setSize(new java.awt.Dimension(962, 632));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void minimizarPrincipalViaCodigo() {
        TelaPrincipal formularioPrincipal = new TelaPrincipal();
        setExtendedState(formularioPrincipal.ICONIFIED);
    }

    public void maximizarPrincipalViaCodigo() {
        TelaPrincipal formularioPrincipal = new TelaPrincipal();
        formularioPrincipal.setVisible(true);
        setExtendedState(formularioPrincipal.MAXIMIZED_BOTH);
    }
    private void subMenuCapMaquinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuCapMaquinaActionPerformed
      
    }//GEN-LAST:event_subMenuCapMaquinaActionPerformed

    private void subMenuSairSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuSairSistemaActionPerformed
        sairAplicacao(); //submenu sair da aplicação
        
    }//GEN-LAST:event_subMenuSairSistemaActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        /**
         * Esse evento executa o código quando o formulário é aberto
         */
        Date data = new Date();

        /**
         * Dateformat para passar uma formatação para o objeto Date
         */
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
        lblDataSistema.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void subMenuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuSobreActionPerformed
        if (FormSobreAplicativo.isOpen()) {
            //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
            JOptionPane.showMessageDialog(null, "Tela ajuda sobre o sistema em uso \n",
                    "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            FormSobreAplicativo telaSobre = FormSobreAplicativo.getInstance();
            telaSobre.setVisible(true);

            DesktopPanelPrincipal.add(telaSobre);
            telaSobre.toFront();
        }
    }//GEN-LAST:event_subMenuSobreActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed
    private void cadUsuarioNivelAdmin() {

        if (estaFechado(VeiwCadUsuario)) {
            VeiwCadUsuario = new VeiwCadUsuario();

            DesktopPanelPrincipal.add(VeiwCadUsuario).setLocation(20, 40);
            VeiwCadUsuario.setTitle("Cadastro Usuário");
            VeiwCadUsuario.show();
        } else {

            VeiwCadUsuario.toFront();
            VeiwCadUsuario.show();

        }

    }


    private void subMenuCadUsuariosTIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuCadUsuariosTIActionPerformed
        cadUsuarioNivelAdmin();
    }//GEN-LAST:event_subMenuCadUsuariosTIActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        cadUsuarioNivelAdmin();
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        sairAplicacao();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnCaptureInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureInfoActionPerformed
  if (estaFechado(VeiwCapturaInfoMaquina)) {
            VeiwCapturaInfoMaquina = new VeiwCapturaInfoMaquina();

            DesktopPanelPrincipal.add(VeiwCapturaInfoMaquina).setLocation(2, 43);
            VeiwCapturaInfoMaquina.setTitle("Captura Info Maquina");
            VeiwCapturaInfoMaquina.show();
        } else {

            VeiwCapturaInfoMaquina.toFront();
            VeiwCapturaInfoMaquina.show();

        }

    }//GEN-LAST:event_btnCaptureInfoActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane DesktopPanelPrincipal;
    private javax.swing.JPanel PanelInfo;
    public static javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnCaptureInfo;
    public static javax.swing.JButton btnSair;
    private javax.swing.JButton btnUsuario;
    private gui.Carregador carregador1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCelular;
    public static javax.swing.JLabel lblContainerCelular;
    private javax.swing.JLabel lblDataSistema;
    private javax.swing.JLabel lblHoraDoSistema;
    private javax.swing.JLabel lblImagemData;
    private javax.swing.JLabel lblImagemHora;
    public static javax.swing.JLabel lblImagemUser;
    private javax.swing.JLabel lblImgData;
    private javax.swing.JLabel lblImgHora;
    private javax.swing.JLabel lblJavaInterface;
    public static javax.swing.JLabel lblNomeCompletoUsuario;
    private javax.swing.JLabel lblNomeInfoExterno;
    public static javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblPerfilInfoExterna;
    private javax.swing.JLabel lblStatusData;
    private javax.swing.JLabel lblStatusHora;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JMenu menuCadastros;
    private javax.swing.JMenu menuSair;
    private javax.swing.JMenuBar menuSistema;
    private javax.swing.JMenu menuSobre;
    private javax.swing.JPanel painelBarraStatus;
    public static javax.swing.JMenuItem subMenuCadUsuariosTI;
    public static javax.swing.JMenuItem subMenuCapMaquina;
    private javax.swing.JMenuItem subMenuSairSistema;
    private javax.swing.JMenuItem subMenuSobre;
    // End of variables declaration//GEN-END:variables

    class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            lblHoraDoSistema.setText(String.format("%1$tH:%1$tM:%1$tS", now));

            Font f = new Font("Tahoma", Font.BOLD, 20);
            lblHoraDoSistema.setForeground(new Color(25,31,28));
            lblHoraDoSistema.setFont(f);

        }

    }
    
    

    
    
}
