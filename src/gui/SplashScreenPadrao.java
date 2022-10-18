package gui;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class SplashScreenPadrao extends javax.swing.JFrame {
    SplashScreenPadrao splash = this;
    public SplashScreenPadrao() {
        initComponents();
        personalizacaoFrontEnd();
        startThread();
    }

    private void aoCarregar() {
    }

    private void personalizacaoFrontEnd() {
    }

    void startThread() {
        Thread hi = new Thread(new Runnable() {
            @Override
            public void run() {
                Login login;
                try {
                    login = new Login(splash);
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                    dispose();

                } catch (ClassNotFoundException ex) {

                    ex.printStackTrace();
                    System.out.println("Erro Splash:" + ex.getMessage());

                }

            }
        });

        hi.start();//inicie minha barra de progresso 
    }

    public JProgressBar getJProgressBar() {
        return Progresso;
    }

    public JLabel getJLabel() {
        return lblInfoCarregamento;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelProgress = new javax.swing.JPanel();
        lblInfoCarregamento = new javax.swing.JLabel();
        Progresso = new javax.swing.JProgressBar();
        painelInfoLogoEmpresa = new javax.swing.JPanel();
        lblLogoJava = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        painelDeveloper = new javax.swing.JPanel();
        repositDeveloper = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        repositEmail = new javax.swing.JLabel();
        lblGitHub = new javax.swing.JLabel();
        repositInstagram = new javax.swing.JLabel();
        lblInstagram1 = new javax.swing.JLabel();
        repositInstagram1 = new javax.swing.JLabel();
        lblGitHub1 = new javax.swing.JLabel();
        repositInstagram2 = new javax.swing.JLabel();
        lblLogoGitHub = new javax.swing.JLabel();
        lblInstagram = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblLinkedin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("splash_padrao");
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelProgress.setBackground(new java.awt.Color(25, 31, 38));
        painelProgress.setForeground(new java.awt.Color(102, 211, 236));
        painelProgress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInfoCarregamento.setBackground(new java.awt.Color(25, 31, 38));
        lblInfoCarregamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInfoCarregamento.setForeground(new java.awt.Color(102, 211, 236));
        lblInfoCarregamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoCarregamento.setText("Carregando Sistema ...");
        lblInfoCarregamento.setName("lblInfoCarregamento"); // NOI18N
        lblInfoCarregamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoCarregamentoMouseClicked(evt);
            }
        });
        painelProgress.add(lblInfoCarregamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 370, 20));

        Progresso.setBackground(new java.awt.Color(255, 255, 255));
        Progresso.setName("Progresso"); // NOI18N
        painelProgress.add(Progresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 350, 27));

        getContentPane().add(painelProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 370, 100));

        painelInfoLogoEmpresa.setBackground(new java.awt.Color(25, 31, 38));
        painelInfoLogoEmpresa.setForeground(new java.awt.Color(102, 211, 236));
        painelInfoLogoEmpresa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoJava.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoJava.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/info.png"))); // NOI18N
        painelInfoLogoEmpresa.add(lblLogoJava, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 50));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 211, 236));
        jLabel1.setText("Java SE - SisSeg Versão Beta"); // NOI18N
        painelInfoLogoEmpresa.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        getContentPane().add(painelInfoLogoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 80));

        painelDeveloper.setBackground(new java.awt.Color(25, 31, 38));
        painelDeveloper.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Developer:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(102, 211, 236))); // NOI18N
        painelDeveloper.setForeground(new java.awt.Color(102, 211, 236));
        painelDeveloper.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        repositDeveloper.setForeground(new java.awt.Color(102, 211, 236));
        repositDeveloper.setText("Tonis A. Torres Ferreira");
        painelDeveloper.add(repositDeveloper, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 210, -1));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNome.setForeground(new java.awt.Color(102, 211, 236));
        lblNome.setText("Developer:");
        painelDeveloper.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(102, 211, 236));
        lblEmail.setText("Email:");
        painelDeveloper.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 60, -1));

        repositEmail.setForeground(new java.awt.Color(102, 211, 236));
        repositEmail.setText("sisvenda2011@gmail.com");
        painelDeveloper.add(repositEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 220, -1));

        lblGitHub.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGitHub.setForeground(new java.awt.Color(102, 211, 236));
        lblGitHub.setText("Linkedin:");
        painelDeveloper.add(lblGitHub, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        repositInstagram.setForeground(new java.awt.Color(102, 211, 236));
        repositInstagram.setText("www.linkedin.com/in/tonistorres");
        painelDeveloper.add(repositInstagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 250, -1));

        lblInstagram1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInstagram1.setForeground(new java.awt.Color(102, 211, 236));
        lblInstagram1.setText("Instagram:");
        painelDeveloper.add(lblInstagram1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        repositInstagram1.setForeground(new java.awt.Color(102, 211, 236));
        repositInstagram1.setText("@tonistorresferreira");
        painelDeveloper.add(repositInstagram1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 220, -1));

        lblGitHub1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGitHub1.setForeground(new java.awt.Color(102, 211, 236));
        lblGitHub1.setText("gitHub:");
        painelDeveloper.add(lblGitHub1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        repositInstagram2.setForeground(new java.awt.Color(102, 211, 236));
        repositInstagram2.setText("https://github.com/tonistorres");
        painelDeveloper.add(repositInstagram2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 230, -1));

        getContentPane().add(painelDeveloper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 370, 150));

        lblLogoGitHub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoGitHub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/gitHub.png"))); // NOI18N
        getContentPane().add(lblLogoGitHub, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 70, 60));

        lblInstagram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/intagram.png"))); // NOI18N
        getContentPane().add(lblInstagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 70, 60));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/email.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 70, 60));

        lblLinkedin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/linkedin.png"))); // NOI18N
        getContentPane().add(lblLinkedin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 70, 60));

        setSize(new java.awt.Dimension(371, 388));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    String metodo = "Método";

    private void lblInfoCarregamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoCarregamentoMouseClicked
        Login form;
        try {
            form = new Login();
            form.setLocationRelativeTo(form);
            form.toFront();
            form.setVisible(true);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_lblInfoCarregamentoMouseClicked

    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SplashScreenPadrao tela = new SplashScreenPadrao();//Aqui utilizamos orientação objeto: criamo um objetio do tipo tela
                tela.setLocationRelativeTo(null);//setamos uma atributo a esse objeto do tipo setLocationRelativeTo 
                tela.setVisible(true);//Em seguida outro abritubo para torná-la visivel 

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Progresso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGitHub;
    private javax.swing.JLabel lblGitHub1;
    public static javax.swing.JLabel lblInfoCarregamento;
    private javax.swing.JLabel lblInstagram;
    private javax.swing.JLabel lblInstagram1;
    private javax.swing.JLabel lblLinkedin;
    private javax.swing.JLabel lblLogoGitHub;
    private javax.swing.JLabel lblLogoJava;
    private javax.swing.JLabel lblNome;
    private javax.swing.JPanel painelDeveloper;
    private javax.swing.JPanel painelInfoLogoEmpresa;
    private javax.swing.JPanel painelProgress;
    private javax.swing.JLabel repositDeveloper;
    private javax.swing.JLabel repositEmail;
    private javax.swing.JLabel repositInstagram;
    private javax.swing.JLabel repositInstagram1;
    private javax.swing.JLabel repositInstagram2;
    // End of variables declaration//GEN-END:variables
}
