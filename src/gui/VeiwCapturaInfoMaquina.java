package gui;

import dao.ReconhecimentoDAO;
import dto.ReconhecimentoDTO;
import exceptions.PersistenciaException;
import static gui.TelaPrincipal.DesktopPanelPrincipal;
import jbdc.ConexaoUtil;
import metodostatics.MetodoStaticosUtil;
import metodostatics.SerialUtils;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.SystemColor.desktop;
import java.awt.Toolkit;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dã Torres
 */
public class VeiwCapturaInfoMaquina extends javax.swing.JInternalFrame {

    //*********************************************************   
    //TESTE DE CONECTIVIDADE COM BANCO DE DADOS 
    //Documentação:https://www.youtube.com/watch?v=LoKQvAQpL3w
    //************************************************************
    // inicializando variáveis do tipo int e estáticas
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    static boolean estado = true;
    //************************************************************

    ReconhecimentoDTO reconheceDTO = new ReconhecimentoDTO();
    ReconhecimentoDAO reconheceDAO = new ReconhecimentoDAO();

    int flag = 0;

    //Chimura: A partir de agora, você usará esse método getInstance() toda vez que quiser criar um ViewEstado
    // Mesma coisa foi feita para ViewBairro
    // Também será necessário fazer para os outros
    private static VeiwCapturaInfoMaquina instance = null;

    public static VeiwCapturaInfoMaquina getInstance() {
        if (instance == null) {
            instance = new VeiwCapturaInfoMaquina();
        }
        return instance;
    }

    // Chimura: usado para checar se a ViewBairro já está aberta, no ato de abrí-la na TelaPrincipal
    public static boolean isOpen() {
        return instance != null;
    }

    public VeiwCapturaInfoMaquina() {
        initComponents();
        metodoStartTesteConexao();
        acaoAoCarregar();
       // addRowJTable();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblMaquina.getTableHeader().setDefaultRenderer(new util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblMaquina.setDefaultRenderer(Object.class, new util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        redimensionarTela();
    }

   
    private void redimensionarTela() {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamTela = kit.getScreenSize();
        //Pegando a largura total da tela
        int larg = tamTela.width;
        //Pegando a altura total da tela
        int alt = tamTela.height;
        /*Se você quiser que o seu JFrame ocupe 70% da tela por exemplo 
         multiplique a largura e altura totais por 0,7*/
        int largura = (int) (larg * 0.39);
        int altura = (int) (alt * 0.485);
        //Mandando o JFrame utilizar suas dimensões
        // DesktopPanelPrincipal.setSize(largura, altura);
        this.setSize(largura, altura);

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tblMaquina.getModel();

        ArrayList<ReconhecimentoDTO> list;

        try {

            list = (ArrayList<ReconhecimentoDTO>) reconheceDAO.listarTodos();

            Object rowData[] = new Object[6];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getId_reconhecimentoDto();//id
                rowData[1] = list.get(i).getSerialHdDto();//serial hd
                rowData[2] = list.get(i).getSerialCPUDto();//serial cpu
                rowData[3] = list.get(i).getSerial_placa_maeDto();//serial placa mae
                rowData[4] = list.get(i).getLiberado_bloqueadoDto();//estado
                rowData[5] = list.get(i).getUsuario_responsavel_cadastro();//serial cpu

                model.addRow(rowData);
            }

            tblMaquina.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblMaquina.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMaquina.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(5).setPreferredWidth(150);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormBairro \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void acaoAoCarregar() {

        //botoes
        btnSalvar.setEnabled(false);
        cbSeguranca.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);

        //campos
        limparCampos();
        desabilitarCampos();
        //metodo de captura de usuário do Sistema Operacional
        lblUsuarioResponsavel.setText(MetodoStaticosUtil.usuarioSistemaDaMaquinaCadastrada());
      

    }

    private void acaoBotaoCapturar() throws IOException {

        flag = 1;

        txtRecuperaData.setText(MetodoStaticosUtil.capturarDataEConverterString());
        txtAInformacoesLocais.setText(MetodoStaticosUtil.capturaInformacoesMaquinaLocal());
//        txtHDSerial.setText( );
//        txtSerialCPU.setText(SerialUtils.getHDSerialLinux());
//        txtPlacaMae.setText(SerialUtils.getMotherboardSerialLinux());
//        btnSalvar.setEnabled(true);
        cbSeguranca.setEnabled(true);
    }

    private void acaoSalvarEdicao() {

        reconheceDTO.setId_reconhecimentoDto(Integer.parseInt(txtId.getText()));
        reconheceDTO.setLiberado_bloqueadoDto((String) cbSeguranca.getSelectedItem());
        try {
            reconheceDAO.atualizar(reconheceDTO);
            limparCampos();
            desabilitarCampos();
            cbSeguranca.setEnabled(false);
            btnSalvar.setEnabled(false);
            btnCapturar.setEnabled(true);

            //atualizar tabela
            int numeroLinhas = tblMaquina.getRowCount();

            if (numeroLinhas > 0) {

                while (tblMaquina.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tblMaquina.getModel()).removeRow(0);

                }

                addRowJTable();

            } else {
                addRowJTable();
            }

            JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso.");

        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
    }

    private void metodoStartTesteConexao() {

        estado = true;

        Thread t;
        t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (milissegundos == 998) {

//                                painelEstadoConexao.setVisible(true);
//                                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/wifiConectadoEfeito.png")));

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
                                
                                if(minutos == 2){
                                                                ConexaoUtil conecta = new ConexaoUtil();
                                boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

                                /**
                                 * // * Site Baixar icone: Contribuição do // *
                                 * Site:https://www.iconfinder.com //
                                 */
                                if (recebeConexao == true) {

//                                    painelEstadoConexao.setVisible(true);
//                                    lblStatusDaConexao.setVisible(true);
                                    //lblStatusEspecificacao.setVisible(true);
//                                    lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/wifiConectado.png")));

                                } else {

                                    //***********************************************************************
                                    //componentes que fazem acesso ao banco na nuvem devem ser desabilitados 
//                                    //
//                                    lblStatusDaConexao.setVisible(true);
//                                    lblStatusEspecificacao.setVisible(true);
//                                    lblStatusEspecificacao.setText("Banco Dado Desconectado");
//                                    lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Wifi_Not_Connected.png")));

                                }


                                
                                }
                                
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            lblMilissegundos.setText(" : " + milissegundos);
                            milissegundos++;
                            lblSegundos.setText(" : " + segundos);
                            lblMinuto.setText(" : " + minutos);
                            lblHora.setText(" : " + horas);

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

    private void acaoSalvarAdicao() {

        reconheceDTO.setDt_hora_conectouDto(txtRecuperaData.getText());
        reconheceDTO.setSerialHdDto(txtHDSerial.getText());
        reconheceDTO.setSerialCPUDto(txtSerialCPU.getText());
        reconheceDTO.setSerial_placa_maeDto(txtPlacaMae.getText());
        reconheceDTO.setLiberado_bloqueadoDto((String) cbSeguranca.getSelectedItem());
        reconheceDTO.setInformacoes_diversasDto(txtAInformacoesLocais.getText());
        reconheceDTO.setUsuario_responsavel_cadastro(lblUsuarioResponsavel.getText());

        try {

            reconheceDAO.inserir(reconheceDTO);
            limparCampos();
            desabilitarCampos();
            btnSalvar.setEnabled(false);
            cbSeguranca.setEnabled(false);
            //atualizar tabela 
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblMaquina.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblMaquina.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblMaquina.getModel()).removeRow(0);
                    }
                    addRowJTable();
                } else {
                    addRowJTable();
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }

            JOptionPane.showMessageDialog(this, "" + "\n Cadastrado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/resolve/informatica/sisseg/imagens/info.png")));
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(this, "Camada GUI:\n " + ex.getMessage());
        }

    }

    private void limparCampos() {
        txtId.setText("");
        txtRecuperaData.setText("");
        txtHDSerial.setText("");
        txtSerialCPU.setText("");
        txtPlacaMae.setText("");
        txtAInformacoesLocais.setText("");
        cbSeguranca.setSelectedItem("null");
    }

    private void desabilitarCampos() {
        txtId.setEnabled(false);
        txtRecuperaData.setEnabled(false);
        txtHDSerial.setEnabled(false);
        txtSerialCPU.setEnabled(false);
        txtPlacaMae.setEnabled(false);
        txtAInformacoesLocais.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSeguranca = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCapturar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAInformacoesLocais = new javax.swing.JTextArea();
        painelColetadoInformaceos = new javax.swing.JPanel();
        txtRecuperaData = new javax.swing.JTextField();
        txtHDSerial = new javax.swing.JTextField();
        lblSerialHD = new javax.swing.JLabel();
        txtSerialCPU = new javax.swing.JTextField();
        lblSerialCPU = new javax.swing.JLabel();
        lblPlacaMae = new javax.swing.JLabel();
        txtPlacaMae = new javax.swing.JTextField();
        lblDataHora = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblUsuarioResponsavel = new javax.swing.JLabel();
        cbSeguranca = new javax.swing.JComboBox();
        lblLiberado = new javax.swing.JLabel();
        painelTimer = new javax.swing.JPanel();
        painelCronometro = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        lblMinuto = new javax.swing.JLabel();
        lblSegundos = new javax.swing.JLabel();
        lblMilissegundos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMaquina = new javax.swing.JTable();

        setClosable(true);
        setResizable(true);
        setTitle("Capturar Info Maquina");
        setPreferredSize(new java.awt.Dimension(630, 860));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSeguranca.setBackground(java.awt.Color.white);
        painelSeguranca.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Barra Ferramentas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
        painelSeguranca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelSeguranca.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 45, 45));

        btnCapturar.setToolTipText("Capturar Informações");
        btnCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarActionPerformed(evt);
            }
        });
        painelSeguranca.add(btnCapturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 45, 45));

        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        painelSeguranca.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 45, 45));

        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        painelSeguranca.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 45, 45));

        painelPrincipal.add(painelSeguranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 450, 80));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Diversas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        txtAInformacoesLocais.setColumns(20);
        txtAInformacoesLocais.setRows(5);
        txtAInformacoesLocais.setOpaque(false);
        jScrollPane1.setViewportView(txtAInformacoesLocais);

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 240, 290));

        painelColetadoInformaceos.setBackground(java.awt.Color.white);
        painelColetadoInformaceos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Colector:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
        painelColetadoInformaceos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtRecuperaData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRecuperaData.setOpaque(false);
        txtRecuperaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecuperaDataActionPerformed(evt);
            }
        });
        painelColetadoInformaceos.add(txtRecuperaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 49, 180, 30));

        txtHDSerial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHDSerial.setOpaque(false);
        painelColetadoInformaceos.add(txtHDSerial, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 180, 30));

        lblSerialHD.setText("Serial HD:");
        painelColetadoInformaceos.add(lblSerialHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, -1, -1));

        txtSerialCPU.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSerialCPU.setOpaque(false);
        painelColetadoInformaceos.add(txtSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 180, 30));

        lblSerialCPU.setText("Serial CPU:");
        painelColetadoInformaceos.add(lblSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        lblPlacaMae.setText("Serial Placa Mãe:");
        painelColetadoInformaceos.add(lblPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 110, -1));

        txtPlacaMae.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlacaMae.setOpaque(false);
        painelColetadoInformaceos.add(txtPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 175, 30));

        lblDataHora.setText("Data/Hora:");
        painelColetadoInformaceos.add(lblDataHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, 176, -1));
        painelColetadoInformaceos.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 30, 30));

        lblUsuarioResponsavel.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        lblUsuarioResponsavel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarioResponsavel.setText("Usuário Responsável");
        painelColetadoInformaceos.add(lblUsuarioResponsavel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 190, 30));

        cbSeguranca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbSeguranca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LIBERADO", "BLOQUEADO" }));
        cbSeguranca.setOpaque(false);
        cbSeguranca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSegurancaActionPerformed(evt);
            }
        });
        painelColetadoInformaceos.add(cbSeguranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 150, 30));

        lblLiberado.setText("Status:");
        painelColetadoInformaceos.add(lblLiberado, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 50, -1));

        painelPrincipal.add(painelColetadoInformaceos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 700, 140));

        painelTimer.setBackground(java.awt.Color.white);
        painelTimer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelCronometro.setBackground(java.awt.Color.white);
        painelCronometro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lblHora.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("00:");

        lblMinuto.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblMinuto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinuto.setText("00:");

        lblSegundos.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblSegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSegundos.setText("00:");

        lblMilissegundos.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblMilissegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMilissegundos.setText("00");

        javax.swing.GroupLayout painelCronometroLayout = new javax.swing.GroupLayout(painelCronometro);
        painelCronometro.setLayout(painelCronometroLayout);
        painelCronometroLayout.setHorizontalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelCronometroLayout.setVerticalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        painelTimer.add(painelCronometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, -1));

        painelPrincipal.add(painelTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 50));

        tblMaquina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "HD", "CPU", "PLACA", "ESTADO", "USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMaquina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMaquinaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMaquina);
        if (tblMaquina.getColumnModel().getColumnCount() > 0) {
            tblMaquina.getColumnModel().getColumn(0).setResizable(false);
            tblMaquina.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblMaquina.getColumnModel().getColumn(1).setResizable(false);
            tblMaquina.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(2).setResizable(false);
            tblMaquina.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(3).setResizable(false);
            tblMaquina.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(4).setResizable(false);
            tblMaquina.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblMaquina.getColumnModel().getColumn(5).setResizable(false);
            tblMaquina.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

        painelPrincipal.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 450, 160));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(0, 0, 732, 513);
    }// </editor-fold>//GEN-END:initComponents

    private void cbSegurancaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSegurancaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSegurancaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            //verificar se maquina ja cadastrada na base de dados
            reconheceDTO.setSerial_placa_maeDto(txtPlacaMae.getText());

            try {
                boolean maquinaCadastrada = reconheceDAO.verificaDuplicidade(reconheceDTO);

                if (flag == 1) {

                    if (maquinaCadastrada == true) {
                        JOptionPane.showMessageDialog(this, "" + "Maquina já Cadastrada\n", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/resolve/informatica/sisseg/imagens/info.png")));
                        limparCampos();
                        desabilitarCampos();
                        btnSalvar.setEnabled(false);
                        cbSeguranca.setEnabled(false);
                    } else {
                        acaoSalvarAdicao();
                    }
                }

                if (flag == 2) {
                    acaoSalvarEdicao();
                }

            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Metodo Salvar: Verificar \nDupicidade"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/resolve/informatica/sisseg/imagens/info.png")));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Sem conectividade", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarActionPerformed
        try {
            acaoBotaoCapturar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnCapturarActionPerformed

    private void txtRecuperaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecuperaDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecuperaDataActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed
    private void mouseClickedTabela() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigoCapturado = (int) tblMaquina.getValueAt(tblMaquina.getSelectedRow(), 0);

        ReconhecimentoDTO modelo;
        try {

            modelo = reconheceDAO.filtrarAoClicarCodio(codigoCapturado);

            if (modelo != null) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */

                txtId.setText(String.valueOf(modelo.getId_reconhecimentoDto()));
                txtRecuperaData.setText(modelo.getDt_hora_conectouDto());
                txtHDSerial.setText(modelo.getSerialHdDto());
                txtSerialCPU.setText(modelo.getSerialCPUDto());
                txtPlacaMae.setText(modelo.getSerial_placa_maeDto());
                txtAInformacoesLocais.setText(modelo.getInformacoes_diversasDto());
                cbSeguranca.setSelectedItem((String) modelo.getLiberado_bloqueadoDto());
                lblUsuarioResponsavel.setText(modelo.getUsuario_responsavel_cadastro());
                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);

                /**
                 * Desabilitar campos
                 */
                desabilitarCampos();

                //APAGAR APARTIR DAQUI
            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar() Camada GUI \n" + ex.getMessage());
        }

    }

    private void tblMaquinaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMaquinaMouseClicked

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            mouseClickedTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Sem Conectividade");
        }


    }//GEN-LAST:event_tblMaquinaMouseClicked

    private void habilitarCampos() {
        this.txtHDSerial.setEnabled(true);
        this.txtSerialCPU.setEnabled(true);
        this.txtPlacaMae.setEnabled(true);

    }
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        flag = 2;

        cbSeguranca.setEnabled(true);
        btnCapturar.setEnabled(false);
        habilitarCampos();
        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void acaoExcluiRegistro() {

        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            reconheceDTO.setId_reconhecimentoDto(Integer.parseInt(txtId.getText()));
            try {
                /**
                 * Chamando o método que irá executar a Edição dos Dados em
                 * nosso Banco de Dados
                 */
                reconheceDAO.deletar(reconheceDTO);
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                ex.getMessage();
            }
            /**
             * Após salvar limpar os campos
             */
            limparCampos();
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblMaquina.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblMaquina.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblMaquina.getModel()).removeRow(0);
                    }
                    addRowJTable();
                } else {
                    addRowJTable();
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }

    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        acaoExcluiRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapturar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbSeguranca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDataHora;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblLiberado;
    private javax.swing.JLabel lblMilissegundos;
    private javax.swing.JLabel lblMinuto;
    private javax.swing.JLabel lblPlacaMae;
    private javax.swing.JLabel lblSegundos;
    private javax.swing.JLabel lblSerialCPU;
    private javax.swing.JLabel lblSerialHD;
    public static javax.swing.JLabel lblUsuarioResponsavel;
    private javax.swing.JPanel painelColetadoInformaceos;
    private javax.swing.JPanel painelCronometro;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSeguranca;
    private javax.swing.JPanel painelTimer;
    private javax.swing.JTable tblMaquina;
    private javax.swing.JTextArea txtAInformacoesLocais;
    private javax.swing.JTextField txtHDSerial;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtPlacaMae;
    private javax.swing.JTextField txtRecuperaData;
    private javax.swing.JTextField txtSerialCPU;
    // End of variables declaration//GEN-END:variables
}
