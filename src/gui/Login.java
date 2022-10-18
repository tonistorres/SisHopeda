package gui;

import bo.LoginBO;
import bo.UsuarioBO;
import dao.LoginDAO;
import dao.UsuarioDAO;
import dto.LoginDTO;
import dto.ReconhecimentoDTO;
import dto.UsuarioDTO;
import jbdc.ConexaoUtil;
import metodostatics.SerialUtils;
import metodostatics.MetodoStaticosUtil;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class Login extends javax.swing.JFrame {

    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    static boolean estado = true;

    ConexaoUtil conecta = new ConexaoUtil();
    LoginBO loginBO = new LoginBO();
    LoginDTO loginDTO = new LoginDTO();
    LoginDAO loginDAO = new LoginDAO();
    UsuarioBO usuarioBO = new UsuarioBO();
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 

    SplashScreenPadrao splash;

    Login(SplashScreenPadrao splash) throws ClassNotFoundException {

        this.splash = splash;
        initComponents();
        aoCarregarForm();
        personalizacaoFrontEnd();
        txtLogin.setEditable(true);
        txtLogin.requestFocus();
        ConexaoUtil conecta = new ConexaoUtil();
        String retornaDadosConexao=conecta.getInstance().returnDadosConexao();

        txtAreaDadosConexao.setText(retornaDadosConexao);
        txtAreaDadosConexao.setWrapStyleWord(true);;
        txtAreaDadosConexao.setVisible(true);
        txtAreaDadosConexao.setForeground(new Color(102,211,236));
        txtAreaDadosConexao.setBackground(new Color(25,31,38));
        
        try {

            conecta.getInstance().getConnection();

            if (conecta != null) {
                lblStatusDaConexao.setText("Conectado");
                lblStatusEspecificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/mysql.png")));
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label
                setProgress(20, "Verificando conexao com MySQL");//barra em 20% 
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Carregamento completado com sucesso!!");//barra em 100%
            } else {
                lblStatusDaConexao.setText("no Conect BD");
                lblStatusEspecificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/noConect.png")));
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            erroViaEmail("Erro no construtor da Classe que \nFaz Teste de Conexão Com Banco\n Form:Login analisar Linhas:79-141 ", ex.getMessage());
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
            System.exit(0);
        }

    }

    private void criandoCaminhoRelatoriosCaminhoAgendaZap() {

        String nomePasta = "C:" + File.separator + "agendaZap";
        File diretorio = new File(nomePasta);

        if (diretorio.exists()) {
            System.out.println("diretorio ja existe:" + diretorio.getPath());
        } else {
            System.out.println("diretorio inexistente criando pasta");
            diretorio.mkdir();
            System.out.println("Pasta Criada:" + diretorio.getPath());
            erroViaEmail("Criando uma Pasta na Unidade C:\n"
                    + " que conterá os relatórios do Agendamento\n"
                    + "exportado transformados em pdf", "criandoCaminhoRelatoriosCaminhoAgendaZap()\n"
                    + "" + diretorio.getPath());
        }
    }

    private void criandoCaminhoRelatoriosEmC() {
        String nomePasta = "C:" + File.separator + "ireport";
        File diretorio = new File(nomePasta);
        if (diretorio.exists()) {
            System.out.println("diretorio ja existe:" + diretorio.getPath());
        } else {
            System.out.println("diretorio inexistente criando pasta");
            diretorio.mkdir();
            System.out.println("Pasta Criada:" + diretorio.getPath());
            erroViaEmail("Criando uma Pasta na Unidade C:\n"
                    + " que conterá os relatórios a serem \n"
                    + "exportadose transformados em pdf", "criandoCaminhoRelatorioEMC()\n"
                    + "" + diretorio.getPath());
        }
    }

    private void erroViaEmail(String mensagemErro, String metodo) {
        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(meuEmail);
            email.setSubject("Metodo:" + metodo);
            email.setMsg("Mensagem:" + mensagemErro + "\n"
            );
            email.addTo(meuEmail);
            email.send();
        } catch (Exception e) {
            erroViaEmail(mensagemErro, metodo);
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
            e.printStackTrace();
        }

    }

    private void aoCarregarForm() {
        txtLogin.requestFocus();
        progressBarraPesquisa.setIndeterminate(true);

    }

    void setProgress(int percent, String informacao
    ) {
        splash.getJLabel().setText(informacao);
        splash.getJProgressBar().setValue(percent);
        try {
            Thread.sleep(1000);//o tempo que o meu ProgressBar vai demorar para percorrer toda barra
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível carregar a inicialização\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanelStatus = new javax.swing.JPanel();
        painelEfeitoTxtLogin = new javax.swing.JPanel();
        txtLogin = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        lblStatusEspecificacao = new javax.swing.JLabel();
        painelEfeitoTxtSenha = new javax.swing.JPanel();
        txtSenha = new javax.swing.JPasswordField();
        painelNuvem = new javax.swing.JPanel();
        lblImgNuvemPos6 = new javax.swing.JLabel();
        lblImgNuvemPos1 = new javax.swing.JLabel();
        lblImgNuvemPos2 = new javax.swing.JLabel();
        lblImgNuvemPos3 = new javax.swing.JLabel();
        lblImgNuvemPos4 = new javax.swing.JLabel();
        lblImgNuvemPos5 = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        lblUsuario = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        lblLogoJava = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblStatusDaConexao = new javax.swing.JLabel();
        lblVerificacao = new javax.swing.JLabel();
        lblLogoGitHub = new javax.swing.JLabel();
        lblInstagram = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblLinkedin = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDadosConexao = new javax.swing.JTextArea();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.white);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelStatus.setBackground(new java.awt.Color(25, 31, 38));
        jPanelStatus.setForeground(new java.awt.Color(25, 31, 38));
        jPanelStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelEfeitoTxtLogin.setBackground(new java.awt.Color(102, 211, 236));
        painelEfeitoTxtLogin.setForeground(java.awt.Color.white);

        txtLogin.setEditable(false);
        txtLogin.setBackground(java.awt.Color.white);
        txtLogin.setName("txtLogin"); // NOI18N
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout painelEfeitoTxtLoginLayout = new javax.swing.GroupLayout(painelEfeitoTxtLogin);
        painelEfeitoTxtLogin.setLayout(painelEfeitoTxtLoginLayout);
        painelEfeitoTxtLoginLayout.setHorizontalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
            .addGroup(painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelEfeitoTxtLoginLayout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );
        painelEfeitoTxtLoginLayout.setVerticalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelEfeitoTxtLoginLayout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );

        jPanelStatus.add(painelEfeitoTxtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 110, 40));

        btnLogin.setBackground(new java.awt.Color(102, 211, 236));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(25, 31, 38));
        btnLogin.setText("Logar");
        btnLogin.setToolTipText("");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnLoginStateChanged(evt);
            }
        });
        btnLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLoginFocusGained(evt);
            }
        });
        btnLogin.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                btnLoginComponentHidden(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });
        jPanelStatus.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 70, 40));

        lblStatusEspecificacao.setBackground(java.awt.Color.white);
        lblStatusEspecificacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusEspecificacao.setForeground(new java.awt.Color(102, 211, 236));
        lblStatusEspecificacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblStatusEspecificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 190, 80));

        painelEfeitoTxtSenha.setBackground(new java.awt.Color(102, 211, 236));
        painelEfeitoTxtSenha.setPreferredSize(new java.awt.Dimension(210, 70));
        painelEfeitoTxtSenha.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSenhaFocusLost(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        painelEfeitoTxtSenha.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 5, 100, 30));

        jPanelStatus.add(painelEfeitoTxtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 110, 40));

        painelNuvem.setBackground(new java.awt.Color(25, 31, 38));
        painelNuvem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(102, 211, 236)), "Nuvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14), new java.awt.Color(102, 211, 236))); // NOI18N
        painelNuvem.setForeground(new java.awt.Color(102, 211, 236));
        painelNuvem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImgNuvemPos6.setForeground(new java.awt.Color(25, 31, 28));
        lblImgNuvemPos6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 50));

        lblImgNuvemPos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 70, 50));

        lblImgNuvemPos2.setBackground(new java.awt.Color(25, 31, 38));
        lblImgNuvemPos2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 70, 50));

        lblImgNuvemPos3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, 50));

        lblImgNuvemPos4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 50));

        lblImgNuvemPos5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 70, 50));

        jPanelStatus.add(painelNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 90, 350));
        jPanelStatus.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 170, -1));

        lblUsuario.setBackground(new java.awt.Color(102, 211, 236));
        lblUsuario.setForeground(new java.awt.Color(102, 211, 236));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/userNovo.png"))); // NOI18N
        lblUsuario.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        lblSenha.setBackground(new java.awt.Color(102, 211, 236));
        lblSenha.setForeground(new java.awt.Color(102, 211, 236));
        lblSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/chave.png"))); // NOI18N
        lblSenha.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 40, 50));

        lblLogoJava.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/info.png"))); // NOI18N
        jPanelStatus.add(lblLogoJava, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 50, 50));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 211, 236));
        jLabel1.setText("SisSeg Versão Beta"); // NOI18N
        jPanelStatus.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        lblStatusDaConexao.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        lblStatusDaConexao.setForeground(new java.awt.Color(102, 211, 236));
        lblStatusDaConexao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblStatusDaConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 230, 30));

        getContentPane().add(jPanelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 370));

        lblVerificacao.setForeground(java.awt.Color.white);
        getContentPane().add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 330, 30));

        lblLogoGitHub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoGitHub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/gitHub.png"))); // NOI18N
        getContentPane().add(lblLogoGitHub, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 70, 60));

        lblInstagram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/intagram.png"))); // NOI18N
        getContentPane().add(lblInstagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 70, 60));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/email.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 70, 60));

        lblLinkedin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/linkedin.png"))); // NOI18N
        getContentPane().add(lblLinkedin, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 60, 60));

        jPanel1.setBackground(new java.awt.Color(25, 31, 38));

        barraProgresso.setBackground(new java.awt.Color(255, 255, 255));

        txtAreaDadosConexao.setColumns(20);
        txtAreaDadosConexao.setFont(new java.awt.Font("Ubuntu", 0, 16)); // NOI18N
        txtAreaDadosConexao.setRows(8);
        jScrollPane1.setViewportView(txtAreaDadosConexao);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 330, 170));

        setSize(new java.awt.Dimension(342, 619));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnLogin.requestFocus();

        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusLost
        estado = false;
        txtSenha.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtSenhaFocusLost

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained

        efeitoCampoTxtSenhaReceberFoco();
        txtLogin.setToolTipText("Digite a SENHA do Usuário");

    }//GEN-LAST:event_txtSenhaFocusGained

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed

    }//GEN-LAST:event_btnLoginKeyPressed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

    }

    public Login() throws ClassNotFoundException {

        initComponents();// inicialização dos componentes

        ConexaoUtil conecta = new ConexaoUtil();

        try {
            conecta.getInstance().getConnection();
            String RecebeDadosConexão = conecta.getInstance().returnDadosConexao();
            System.out.println(RecebeDadosConexão);
            if (conecta != null) {

                lblStatusEspecificacao.setText("Banco Dado Conectado");
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label
                setProgress(20, "Recebendo Dados da Conexão");//barra em 20%
                
                
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Bem vindo ao sistema");//barra em 100%
            } else {
                lblStatusEspecificacao.setText("Banco Dado Desconectado");
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_btnLoginComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginComponentHidden

    private void btnLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLoginFocusGained
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        btnLogin.setEnabled(false);
        if (recebeConexao == true) {
            if (!txtLogin.getText().equals("") && !txtSenha.getText().equals("")) {
                login();
            }
            if (txtLogin.getText().equals("")) {
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite o Usuario");
                txtLogin.requestFocus();
                txtLogin.setText("Usuario?");
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }

            if (txtSenha.getText().equals("")) {
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite a SENHA");
                txtSenha.requestFocus();
                txtSenha.setText("");
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

        } else {

            Font f = new Font("Tahoma", Font.ITALIC, 9);
            lblStatusEspecificacao.setText("Falha na Comunicação");
            txtLogin.requestFocus();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnLoginFocusGained

    private void btnLoginStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnLoginStateChanged

    }//GEN-LAST:event_btnLoginStateChanged

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtSenha.requestFocus();

        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginActionPerformed

    private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost

    }//GEN-LAST:event_txtLoginFocusLost

    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.setToolTipText("Digite NOME do Usuário");
        efeitoCampoTxtLoinReceberFoco();
    }//GEN-LAST:event_txtLoginFocusGained

    private void desabilitarCampos() {
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnLogin.setEnabled(false);
    }

    private void personalizacaoFrontEnd() {

        jPanelStatus.setBackground(new Color(25, 31, 28));
        personalizarTxtCapturaInfoMaquina();
    }

    private void personalizarTxtCapturaInfoMaquina() {
        Font fonteTxtInformacaoMaquina = new Font("Tahoma", Font.ITALIC, 10);

    }

    private void personalizarMaquinaReconhecida() {
        Font f = new Font("Tahoma", Font.BOLD, 15);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);
    }

    private void personalizarMaquinaBloqueada() {
        Font f = new Font("Tahoma", Font.BOLD, 15);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);

    }

//METODO PARA LOGAR 
    public void login() {

        usuarioDTO.setLoginDto(txtLogin.getText());
        usuarioDTO.setSenhaDto(new String(txtSenha.getPassword()));

        try {

            UsuarioDTO resultado = usuarioBO.logarBO(usuarioDTO);

            if (resultado.getPerfilDto().equalsIgnoreCase("ANALISTA")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);
                this.dispose();
                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblPerfil.setForeground(new Color(102, 211, 236));
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(102, 211, 236));
                TelaPrincipal.lblContainerCelular.setText(resultado.getCelularDto());
                TelaPrincipal.lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/perfil.jpeg")));
                this.dispose();

            }

        } catch (Exception ex) {
            ex.printStackTrace();

            if (ex.getMessage().equals("Login Obrigatorio!")) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                txtLogin.requestFocus();
                txtLogin.setBackground(Color.RED);

            }

            if (ex.getMessage().equals("Usuario não cadastrado.")) {

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                txtLogin.setText("");
                txtSenha.setText("");

            }

            if (ex.getMessage().equals("Senha Obrigatorio!")) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtSenha.requestFocus();
                txtSenha.setBackground(Color.RED);
            }

            if (ex.getMessage().equals("Usuario ou Senha Incorretos")) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtLogin.requestFocus();
                txtLogin.setText("");
                txtSenha.setText("");
                txtLogin.setBackground(Color.red);
                txtSenha.setBackground(Color.red);
            }
        }

    }

    private void desabilitarCamposInfo() {

    }

    private void sisSegHospeda() {

        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        System.out.println("2 - Recebe Conexão" + recebeConexao);
        if (recebeConexao) {

            try {

                String recuperaCPU = SerialUtils.getCPUSerialLinux();
                String recupera = SerialUtils.getMotherboardSerialLinux();
                ReconhecimentoDTO recebeComparacaoDto = loginDAO.comparaSereiMotherboard(recupera);

                if (recebeComparacaoDto.getLiberado_bloqueadoDto().trim().equalsIgnoreCase("LIBERADO")) {
                    desabilitarCamposInfo();
                    personalizarMaquinaReconhecida();
                } else {

                    desabilitarCamposInfo();
                    personalizarMaquinaBloqueada();
                    btnLogin.setEnabled(false);
                    txtLogin.setEnabled(false);
                    txtSenha.setEnabled(false);

                }
            } catch (Exception e) {

                String localhost;
                try {
                    localhost = InetAddress.getLocalHost().getHostName();
                    InetAddress[] addr = InetAddress.getAllByName(localhost);
                    for (int i = 0; i < addr.length; i++) {
                        System.out.println(addr[i]);
                        emailCapturandoListaIpsNAOReconhecido(" " + addr[i]);
                    }

                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }

        } else {

            JOptionPane.showMessageDialog(null, "" + "\n Sem Conexão com a INTERNET:\n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
            System.exit(0);
        }

    }

    private void emailCapturandoListaIPSRECONHECIDA(String ips) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinareconhecida@gmail.com";

        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(meuEmail);
            email.setSubject("LISTA IPS" + MetodoStaticosUtil.capturarDataEConverterString());
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "LISTA DE IPS DA MAQUNIDA RECONHECIDA\n"
                    + "------------------------------------------------------------------------\n"
                    + ips
                    + "-------------------------------------------------------------------------\n"
            );

            email.addTo(enviarParaSupervisor);
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }

    private void emailMaquinaReconhecida(int id_reconecimentoSISHOSPEDA, String UsuarioSISHOSPEDA, String nomeDeclarativoDono, String placaMae, String CPU, String HD, String dtHoraAcesso) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinareconhecida@gmail.com";
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(meuEmail);
            email.setSubject("RECONHECIDA" + MetodoStaticosUtil.capturarDataEConverterString());
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "DADOS BUSCADOS NA BASE DE DOS SISHOSPEDA\n"
                    + "------------------------------------------------------------------------\n"
                    + "ID MAQUINA CADASTRADA SISHOSPEDA: " + id_reconecimentoSISHOSPEDA + " " + "Sistema Desenvolvido para:" + nomeDeclarativoDono + "\n"
                    + "Está rodando na maquina: " + UsuarioSISHOSPEDA + "\n"
                    + "-------------------------------------------------------------------------\n"
                    + "    DADOS COLETADOS DA MÁQUINA EM TEMPO DE EXECUÇÃO\n"
                    + "-------------------------------------------------------------------------\n"
                    + "PLACA MAE:" + placaMae + " " + " CPU: " + " " + CPU + " " + " HD: " + HD + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            email.addTo(enviarParaSupervisor);
            email.send();
        } catch (Exception e) {
            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
                System.exit(0);
            }
            e.printStackTrace();
        }
    }

    private void emailCapturandoListaIpsNAOReconhecido(String ips) {
        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";
        String enviarParaSupervisor = "sisjavarobomaquinadesconhecida@gmail.com";
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(meuEmail);
            email.setSubject("LISTA IPS" + MetodoStaticosUtil.capturarDataEConverterString());
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "LISTA DE IPS DESCONHECIDO\n"
                    + "------------------------------------------------------------------------\n"
                    + ips + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            email.addTo(enviarParaSupervisor);
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }

    private void emailMaquinaNAOReconhecida(int id_reconecimentoSISHOSPEDA, String UsuarioSISHOSPEDA, String nomeDeclarativoDono, String placaMae, String CPU, String HD, String dtHoraAcesso) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinadesconhecida@gmail.com";

        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        try {
            email.setFrom(meuEmail);
            email.setSubject("DESCONHECIDA" + MetodoStaticosUtil.capturarDataEConverterString());
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "DADOS BUSCADOS NA BASE DE DOS SISHOSPEDA\n"
                    + "------------------------------------------------------------------------\n"
                    + "ID MAQUINA CADASTRADA SISHOSPEDA: " + id_reconecimentoSISHOSPEDA + " " + "Sistema Desenvolvido para:" + nomeDeclarativoDono + "\n"
                    + "Está rodando na maquina: " + UsuarioSISHOSPEDA + "\n"
                    + "-------------------------------------------------------------------------\n"
                    + "    DADOS COLETADOS DA MÁQUINA EM TEMPO DE EXECUÇÃO\n"
                    + "-------------------------------------------------------------------------\n"
                    + "PLACA MAE:" + placaMae + " " + " CPU: " + " " + CPU + " " + " HD: " + HD + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            email.addTo(enviarParaSupervisor);
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {
                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/imagens/info.png")));
                System.exit(0);
            }
            e.printStackTrace();
        }
    }

    private void efeitoCampoTxtSenhaReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {
                                painelEfeitoTxtSenha.setVisible(true);
                                painelEfeitoTxtSenha.setBackground(new Color(0, 102, 102));
                                txtSenha.setBackground(Color.WHITE);
                                txtSenha.setForeground(Color.BLACK);

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtSenha.setVisible(true);
                                painelEfeitoTxtSenha.setBackground(new Color(204, 0, 51));
                                txtSenha.setBackground(Color.WHITE);
                                txtSenha.setForeground(Color.BLACK);

                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {

                                    public void run() {

                                        for (int i = segundos; i < 61; i++) {

                                            try {

                                                sleep(4000);
                                                barraProgresso.setValue(i);

                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");

                                                    // painelInferior.setVisible(true);
                                                    // barraProgresso.setVisible(true);
                                                    lblVerificacao.setVisible(true);

                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");

                                                } else if (barraProgresso.getValue() == 57) {

                                                    lblVerificacao.setText("65% Descarregado");

                                                } else if (barraProgresso.getValue() == 59) {

                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
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

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }

    //METODO CAMPO TXTLOGIN 
    private void efeitoCampoTxtLoinReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {

                                painelEfeitoTxtLogin.setVisible(true);
                                painelEfeitoTxtLogin.setBackground(new Color(0, 102, 102));

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtLogin.setVisible(true);
                                painelEfeitoTxtLogin.setBackground(new Color(204, 0, 51));

                                lblImgNuvemPos1.setVisible(false);
                            }

                            if (segundos % 2 == 0 && segundos <= 10) {
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 != 0 && segundos <= 10) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 10 && segundos <= 20)) {
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 10 && segundos <= 20)) {
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);

                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 == 0 && (segundos > 20 && segundos <= 30)) {

                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 20 && segundos <= 30)) {

                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 30 && segundos <= 40)) {

                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 30 && segundos <= 40)) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 40 && segundos <= 50)) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 40 && segundos <= 50)) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(false);
                            }

                            if (segundos % 2 == 0 && (segundos > 50 && segundos <= 54)) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(true);
                            }

                            if (segundos % 2 != 0 && (segundos > 50 && segundos <= 54)) {
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(false);
                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {
                                    public void run() {
                                        for (int i = segundos; i < 61; i++) {
                                            try {
                                                sleep(4000);
                                                barraProgresso.setValue(i);
                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");
                                                    lblVerificacao.setVisible(true);
                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");
                                                } else if (barraProgresso.getValue() == 57) {
                                                    lblVerificacao.setText("65% Descarregado");
                                                } else if (barraProgresso.getValue() == 59) {
                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
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

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    ex.getMessage();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImgNuvemPos1;
    private javax.swing.JLabel lblImgNuvemPos2;
    private javax.swing.JLabel lblImgNuvemPos3;
    private javax.swing.JLabel lblImgNuvemPos4;
    private javax.swing.JLabel lblImgNuvemPos5;
    private javax.swing.JLabel lblImgNuvemPos6;
    private javax.swing.JLabel lblInstagram;
    private javax.swing.JLabel lblLinkedin;
    private javax.swing.JLabel lblLogoGitHub;
    private javax.swing.JLabel lblLogoJava;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblStatusDaConexao;
    private javax.swing.JLabel lblStatusEspecificacao;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelEfeitoTxtLogin;
    private javax.swing.JPanel painelEfeitoTxtSenha;
    private javax.swing.JPanel painelNuvem;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTextArea txtAreaDadosConexao;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
