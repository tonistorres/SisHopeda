package gui;

import bo.CadUsuarioBO;
import dao.CadUsuarioDAO;
import dto.CadUsuarioDTO;
import exceptions.PersistenciaException;
import jbdc.ConexaoUtil;
import metodostatics.MetodoStaticosUtil;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class VeiwCadUsuario extends javax.swing.JInternalFrame {

    CadUsuarioDTO cadUsuarioDTO = new CadUsuarioDTO();
    CadUsuarioDAO cadUsuarioDAO = new CadUsuarioDAO();
    CadUsuarioBO cadUsuarioBO = new CadUsuarioBO();
    Font f = new Font("Tahoma", Font.ITALIC, 12);
    int flag = 0;

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

    public VeiwCadUsuario() {
        initComponents();
        aoCarregarForm();
        personalizacaoFrontEnd();
        addRowJTable();
        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblUsuarios.getTableHeader().setDefaultRenderer(new util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblUsuarios.setDefaultRenderer(Object.class, new util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tblUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
        tblUsuarios.getTableHeader().setOpaque(false);
        tblUsuarios.getTableHeader().setBackground(new Color(32, 136, 203));
        tblUsuarios.getTableHeader().setForeground(new Color(255, 255, 255));
        tblUsuarios.setRowHeight(25);

    }

    public void aoCarregarForm() {

        this.txtLogin.setEnabled(false);
        this.txtSenhaPS.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);

    }

    public void personalizacaoFrontEnd() {
//        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/barra/superior/forms/lixeira.png")));
//        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/barra/superior/forms/adicionar.png")));
//        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/barra/superior/forms/editar.png")));
//        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/barra/superior/forms/salvar.png")));
//        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/barra/superior/forms/cancel.png")));

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<CadUsuarioDTO> list;

        try {

            list = (ArrayList<CadUsuarioDTO>) cadUsuarioDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getPerfilDto();
                rowData[3] = list.get(i).getCelularDto();
                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(150);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<CadUsuarioDTO> list;

        try {

            list = (ArrayList<CadUsuarioDTO>) cadUsuarioDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getSenhaDto();
                rowData[3] = list.get(i).getPerfilDto();
                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(500);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(380);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(260);

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void salvarAdicoesAteracoesUsuarios() {

        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        cadUsuarioDTO.setLoginDto(txtLogin.getText());
        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        cadUsuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));

        // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
        cadUsuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());

        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo CadUsuarioBO
             */
            cadUsuarioBO = new CadUsuarioBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((cadUsuarioBO.validaNome(cadUsuarioDTO)) == false) {
                txtLogin.setText("");

            } else {

                if ((flag == 1)) {

                    cadUsuarioDTO.setLoginDto(txtLogin.getText());

                    boolean retornoVerifcaDuplicidade = cadUsuarioDAO.verificaDuplicidade(cadUsuarioDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        /**
                         * capturando os campos do Form na Camada Gui e em vez
                         * de adicionar ha uma variável encapsulamos e setamos
                         * como o método set
                         */
                        cadUsuarioDTO.setLoginDto(txtLogin.getText());
                        /**
                         * Observação:Essa é a forma de captuar do form gui um
                         * campo do tipo senha para salva-lo num banco de dados
                         * como uma string
                         */
                        cadUsuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));

                        // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
                        cadUsuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());

                        cadUsuarioBO.cadastrar(cadUsuarioDTO);
                        /**
                         * Após salvar limpar os campos
                         */

                        txtLogin.setText("");
                        txtSenhaPS.setText("");

                        cbPerfilUsuarios.setSelectedItem("");
                        /**
                         * Bloquear campos e Botões
                         */
                        txtIDUsuario.setEnabled(false);

                        txtLogin.setEnabled(false);

                        cbPerfilUsuarios.setEnabled(false);
                        txtSenhaPS.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);

                        btnAdicionar.setEnabled(true);

                        //MensagensUtil.add(TelaUsuarios.this, "Registro Cadastrado com Sucesso!");
                        JOptionPane.showMessageDialog(null, "Registro Cadastrado com Sucesso!");
                        /**
                         * Zera a Linha informativa criada para esse Sistema
                         */
                        lblLinhaInformativa.setText("Linha Informativa");
                        lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));

                        int numeroLinhas = tblUsuarios.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblUsuarios.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();

                        }

                    } else {
                        //JOptionPane.showMessageDialog(TelaUsuarios.this, "Login já cadastrado.\no Sistema Impossibilitou \n a Duplicidade");

                        txtLogin.requestFocus();
                        txtLogin.setBackground(Color.YELLOW);
                        lblLinhaInformativa.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativa.setForeground(Color.RED);
                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    cadUsuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));

                    cadUsuarioDTO.setLoginDto(txtLogin.getText());

                    cadUsuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));
                    cadUsuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    cadUsuarioBO.atualizarBO(cadUsuarioDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtIDUsuario.setText("");

                    txtLogin.setText("");

                    cbPerfilUsuarios.setSelectedItem(null);
                    txtSenhaPS.setText("");
                    txtPesquisa.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtIDUsuario.setEnabled(false);

                    txtLogin.setEnabled(false);

                    cbPerfilUsuarios.setEnabled(false);
                    txtSenhaPS.setEnabled(false);
                    txtPesquisa.setEnabled(true);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

//                    MensagensUtil.add(TelaUsuarios.this, "Edição Salva com Sucesso!");
                    JOptionPane.showMessageDialog(null, "Edição salva  com Sucesso!");
                    int numeroLinhas = tblUsuarios.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tblUsuarios.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equals("Campo nome Obrigatório")) {

            }

            if (e.getMessage().equals("Campo nome aceita no MAX 50 chars")) {

                lblLinhaInformativa.setText("Campo aceita no Máximo a digitação de 50 caracteres");

            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelDadosUsuario = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenhaPS = new javax.swing.JPasswordField();
        lblPerfil = new javax.swing.JLabel();
        cbPerfilUsuarios = new javax.swing.JComboBox<>();
        PanelJTable = new javax.swing.JPanel();
        txtIDUsuario = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnPesquisar = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblCPFUsuario = new javax.swing.JLabel();
        txtCPFPaciente = new javax.swing.JFormattedTextField();
        btnValidaCPF = new javax.swing.JButton();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblNCasa = new javax.swing.JLabel();
        txtNCasa = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        lblPesquisar = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        setClosable(true);
        setTitle("Usuário");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelDadosUsuario.setBackground(java.awt.Color.white);
        PanelDadosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Usuário:"));
        PanelDadosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLogin.setText("Login:");
        PanelDadosUsuario.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txtLogin.setBackground(new java.awt.Color(255, 255, 204));
        txtLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoginKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 100, 30));

        lblSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSenha.setText("Senha:");
        PanelDadosUsuario.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, -1));

        txtSenhaPS.setBackground(new java.awt.Color(255, 255, 204));
        txtSenhaPS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSenhaPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtSenhaPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 80, 30));

        lblPerfil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPerfil.setText("Perfil:");
        PanelDadosUsuario.add(lblPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));

        cbPerfilUsuarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbPerfilUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));
        cbPerfilUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilUsuariosActionPerformed(evt);
            }
        });
        cbPerfilUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPerfilUsuariosKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbPerfilUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 110, 30));

        PanelJTable.setLayout(new java.awt.GridLayout(1, 0));
        PanelDadosUsuario.add(PanelJTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 173, 470, -1));

        txtIDUsuario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIDUsuario.setEnabled(false);
        PanelDadosUsuario.add(txtIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 30));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "LOGIN", "PERFIL", "CELULAR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.getTableHeader().setReorderingAllowed(false);
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setMinWidth(50);
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
            tblUsuarios.getColumnModel().getColumn(1).setMinWidth(150);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblUsuarios.getColumnModel().getColumn(1).setMaxWidth(150);
            tblUsuarios.getColumnModel().getColumn(2).setMinWidth(120);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblUsuarios.getColumnModel().getColumn(2).setMaxWidth(120);
            tblUsuarios.getColumnModel().getColumn(3).setResizable(false);
        }

        PanelDadosUsuario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 430, 90));

        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        PanelDadosUsuario.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 45, 45));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNome.setText("Nome:");
        PanelDadosUsuario.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PanelDadosUsuario.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 220, 30));

        lblCPFUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCPFUsuario.setText("CPF:");
        PanelDadosUsuario.add(lblCPFUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        try {
            txtCPFPaciente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPFPaciente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCPFPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFPacienteFocusLost(evt);
            }
        });
        txtCPFPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFPacienteActionPerformed(evt);
            }
        });
        txtCPFPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFPacienteKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(txtCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 140, 30));

        btnValidaCPF.setPreferredSize(new java.awt.Dimension(32, 32));
        btnValidaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusLost(evt);
            }
        });
        btnValidaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCPFActionPerformed(evt);
            }
        });
        btnValidaCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaCPFKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        lblCidade.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCidade.setText("Cidade:");
        PanelDadosUsuario.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        txtCidade.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PanelDadosUsuario.add(txtCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 140, 30));

        lblBairro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblBairro.setText("Bairro:");
        PanelDadosUsuario.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 130, -1, -1));

        txtBairro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PanelDadosUsuario.add(txtBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 120, 30));

        lblNCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNCasa.setText("Nº");
        PanelDadosUsuario.add(lblNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        txtNCasa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PanelDadosUsuario.add(txtNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 50, 30));

        lblComplemento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblComplemento.setText("Complemento:");
        PanelDadosUsuario.add(lblComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PanelDadosUsuario.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 190, 30));

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatus.setText("Status:");
        PanelDadosUsuario.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PanelDadosUsuario.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 80, 30));

        jPanel2.setBackground(new java.awt.Color(25, 31, 38));
        PanelDadosUsuario.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 460, 50));

        txtPesquisa.setBackground(new java.awt.Color(204, 204, 204));
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 380, 36));

        lblPesquisar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblPesquisar.setText("Pesquisar:");
        PanelDadosUsuario.add(lblPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        getContentPane().add(PanelDadosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 91, 460, 410));

        PanelBotoesManipulacaoBancoDados.setBackground(new java.awt.Color(25, 31, 38));
        PanelBotoesManipulacaoBancoDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        PanelBotoesManipulacaoBancoDados.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 45, 45));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/salvar.png"))); // NOI18N
        btnSalvar.setToolTipText("");
        btnSalvar.setEnabled(false);
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
        });
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarMouseExited(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalvarKeyPressed(evt);
            }
        });
        PanelBotoesManipulacaoBancoDados.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 45, 45));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar.png"))); // NOI18N
        btnEditar.setToolTipText("");
        btnEditar.setEnabled(false);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        PanelBotoesManipulacaoBancoDados.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 45, 45));

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add.png"))); // NOI18N
        btnAdicionar.setToolTipText("");
        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseExited(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        PanelBotoesManipulacaoBancoDados.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 45, 45));

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/lixeira.png"))); // NOI18N
        btnExcluir.setToolTipText("");
        btnExcluir.setEnabled(false);
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        PanelBotoesManipulacaoBancoDados.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 45, 45));
        PanelBotoesManipulacaoBancoDados.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 440, 30));

        getContentPane().add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtSenhaPS.requestFocus();

        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtLoginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyTyped
        /**
         * Analista de Sistemas: Tonis Alberto Torres Ferreira
         * Contribuição:https://www.youtube.com/watch?v=nZCC8fnNfyI Erro:
         * Criando rotinas para controle de erros de usuários mensagem para
         * controlar o máximo de caracter que dever ser lançado para o Banco de
         * Dados. Deste modo evitamos a possibilidade de erro quando o usuário
         * tentar gravar no banco de dados um dado com um numero de caracter
         * maior que o permitido no campo do Banco de Dados evitando o disparo
         * de uma throw exceptions
         */

        /**
         * criamos uma variável de controle do tipo primitivo int como o númeo
         * de caracter de acordo com o especificado no Banco de Dados. Em sguida
         * colocamos uma estrutura de controle onde mostramos o campo que
         * recebera o codigo e acionamos a propriedade length (tamanho) e
         * indicamos o numero de caracter máximo aceito
         */
        int numeroDeCaracter = 10;

        if (txtLogin.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máximo 10 caracteres");// colocamos uma mensagem alertar usuário
            txtLogin.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Login: Máximo 10 caracteres");
        }
    }//GEN-LAST:event_txtLoginKeyTyped

    private void txtSenhaPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            cbPerfilUsuarios.requestFocus();

        }
    }//GEN-LAST:event_txtSenhaPSKeyPressed

    private void txtSenhaPSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyTyped
        /**
         * Analista de Sistemas: Tonis Alberto Torres Ferreira
         * Contribuição:https://www.youtube.com/watch?v=nZCC8fnNfyI Erro:
         * Criando rotinas para controle de erros de usuários mensagem para
         * controlar o máximo de caracter que dever ser lançado para o Banco de
         * Dados. Deste modo evitamos a possibilidade de erro quando o usuário
         * tentar gravar no banco de dados um dado com um numero de caracter
         * maior que o permitido no campo do Banco de Dados evitando o disparo
         * de uma throw exceptions
         */

        /**
         * criamos uma variável de controle do tipo primitivo int como o númeo
         * de caracter de acordo com o especificado no Banco de Dados. Em sguida
         * colocamos uma estrutura de controle onde mostramos o campo que
         * recebera o codigo e acionamos a propriedade length (tamanho) e
         * indicamos o numero de caracter máximo aceito
         */
        int numeroDeCaracter = 8;

        if (txtSenhaPS.getPassword().length > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute
            JOptionPane.showMessageDialog(null, "Máxmimo 8 caracteres");// colocamos uma mensagem alertar usuário
            txtSenhaPS.setBackground(Color.CYAN);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Senha: Máximo 8 caracteres");
        }
    }//GEN-LAST:event_txtSenhaPSKeyTyped

    private void cbPerfilUsuariosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

        }
    }//GEN-LAST:event_cbPerfilUsuariosKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        txtIDUsuario.setText("");

        txtLogin.setText("");
        txtSenhaPS.setText("");

        cbPerfilUsuarios.setSelectedItem(null);

        txtPesquisa.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtIDUsuario.setEnabled(false);

        cbPerfilUsuarios.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenhaPS.setEnabled(false);

        txtPesquisa.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);

        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Cadastro cancelado com sucesso!!");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void desabilitarCampos() {

        txtIDUsuario.setEnabled(false);
        txtLogin.setEnabled(false);
        txtPesquisa.setEnabled(false);
        txtSenhaPS.setEnabled(false);

        cbPerfilUsuarios.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(false);
    }


    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            salvarAdicoesAteracoesUsuarios();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        salvarAdicoesAteracoesUsuarios();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (txtLogin.equals("")) {

            JOptionPane.showMessageDialog(null, "Informação:\n"
                    + "Para que se possa EDITAR é necessário \n"
                    + "que haja um registro selecionado");

        } else {
            /**
             * Quando clicar em Editar essa flag recebe o valor de 2
             */

            flag = 2;

            /**
             * Também irá habilitar nossos campos para que possamos digitar os
             * dados no formulario medicos
             */
            txtLogin.setEnabled(true);
            cbPerfilUsuarios.setEnabled(true);
            txtSenhaPS.setEnabled(true);

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);

        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        flag = 1;

        /**
         * Campos devem ser ativados
         */
        txtLogin.setEnabled(true);
        cbPerfilUsuarios.setEnabled(true);
        txtSenhaPS.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        txtIDUsuario.setVisible(false);

        txtSenhaPS.setText("");
        cbPerfilUsuarios.setSelectedItem(null);
        txtLogin.setText("");

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        txtPesquisa.setEnabled(false);
        btnEditar.setEnabled(false);

       //setar o campo nome Bairro após adicionar

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void acaoExcluirRegistro() {

        int resposta = 0;
        resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja Excluir Resgistro?");

        if (resposta == JOptionPane.YES_OPTION) {
            cadUsuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            cadUsuarioBO.ExcluirBO(cadUsuarioDTO);
            /**
             * Após salvar limpar os campos
             */
            txtIDUsuario.setText("");

            txtLogin.setText("");
            txtSenhaPS.setText("");

            cbPerfilUsuarios.setSelectedItem(null);
            btnAdicionar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblUsuarios.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblUsuarios.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);
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

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoExcluirRegistro();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

    }//GEN-LAST:event_txtPesquisaKeyPressed
    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0));
        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/

        try {

            CadUsuarioDTO retorno = cadUsuarioDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getIdUserDto() != null || !retorno.getIdUserDto().equals("")) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                txtIDUsuario.setText(String.valueOf(retorno.getIdUserDto()));

                txtLogin.setText(retorno.getLoginDto());
                txtSenhaPS.setText(retorno.getSenhaDto());

                /**
                 * setSelectedItem para setar uma String que está no Banco de
                 * Dados em um Campo de Combinação em um Form Java
                 */
                cbPerfilUsuarios.setSelectedItem(retorno.getPerfilDto());

                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);
                /**
                 * Habilitar Campos
                 */
                txtPesquisa.setEnabled(true);

                /**
                 * Desabilitar campos
                 */
                /**
                 * Também irá habilitar nossos campos para que possamos digitar
                 * os dados no formulario medicos
                 */
                this.cbPerfilUsuarios.setEnabled(false);
                this.txtLogin.setEnabled(false);
                this.txtSenhaPS.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Resgistro não foi encontrado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
    }


    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoMouseClicked();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void acaoBotaoPesquisar() {

        int numeroLinhas = tblUsuarios.getRowCount();

        if (numeroLinhas > 0) {

            while (tblUsuarios.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

            }

            pesquisarUsuario();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtCPFPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusGained
        txtCPFPaciente.setBackground(Color.YELLOW);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o CPF");

    }//GEN-LAST:event_txtCPFPacienteFocusGained

    private void txtCPFPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusLost
        txtCPFPaciente.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFPacienteFocusLost

    private void txtCPFPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFPacienteActionPerformed

    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        cadUsuarioDTO.setCpfDto(this.txtCPFPaciente.getText());

        try {
            //robo conectado ao servidor google 
            boolean retornoVerifcaDuplicidade = cadUsuarioDAO.verificaDuplicidade(cadUsuarioDTO);//verificar se já existe CNPJ

            if (retornoVerifcaDuplicidade == false) {

                /**
                 * Criamos um contador que será incrementado a medida que
                 * estiver sendo executado na string passando por cada caracter
                 * da mesma e nos dando a posição exata onde se encontra para
                 * que possamos fazer uma intervenção exata.
                 */
                int cont = 0;

                /**
                 * Inicia-se o for que irá percorrer o tamanho total da variável
                 * CNPJ que guarda o valor capturado do campo txtCNPJ
                 */
                for (int i = 0; i < cadUsuarioDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (cadUsuarioDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            cadUsuarioDTO.setCpfDto(cadUsuarioDTO.getCpfDto().replace(cadUsuarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (cadUsuarioDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            cadUsuarioDTO.setCpfDto(cadUsuarioDTO.getCpfDto().replace(cadUsuarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = cadUsuarioDTO.getCpfDto().replace(" ", "");

                /**
                 * A baixo fazemos a aplicação da função que irá validar se o
                 * cnpj é válido ou não isCNPJ
                 */
                boolean recebeCPF = MetodoStaticosUtil.isCPF(cpfTratado);
                /**
                 * se o retorno for verdadeiro CNPJ válido caso contrário CNPJ
                 * Inválido
                 */
                if (recebeCPF == true) {
                    //   JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    acaoCNPJTrue();
                    txtCPFPaciente.setEditable(true);
                    txtCPFPaciente.setBackground(Color.WHITE);

                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setText("Validado com Sucesso.");
                    lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                    txtNome.requestFocus();
                    txtCPFPaciente.setEnabled(false);
                    btnValidaCPF.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CPF Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtCPFPaciente.setBackground(Color.YELLOW);
                    txtCPFPaciente.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtCPFPaciente.requestFocus();
                txtCPFPaciente.setBackground(Color.RED);

            }
        } catch (PersistenciaException ex) {
            //robo conectado servidor google 

            ex.printStackTrace();
            System.out.println("Erro:" + ex.getMessage());

        }

    }

    private void acaoCNPJTrue() {
        // habilitarCamposForm();
        txtNome.setEnabled(true);
    }


    private void txtCPFPacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFPacienteKeyPressed

        if (!txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //this.btnValidaCPF.requestFocus();
                acaoValidaCPFPolindoDados();
            }
        }
        if (txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtCPFPaciente.requestFocus();
                JOptionPane.showMessageDialog(this, "" + "\n Obrigatório CPF.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }
        }
    }//GEN-LAST:event_txtCPFPacienteKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);
        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Click sobre o [BOTÃO] ou Pressione [ENTER]");
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        this.btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoValidaCPFPolindoDados();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidaCPFActionPerformed

    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            //se ele executar usando a ação keyPress desabilita o botão
            btnValidaCPF.setEnabled(false);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {
                acaoValidaCPFPolindoDados();
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }

        }

        if (evt.getKeyCode() == evt.VK_RIGHT) {
//            cbSexo.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCPFPaciente.requestFocus();
        }
    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void cbPerfilUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilUsuariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JPanel PanelJTable;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JComboBox<String> cbPerfilUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCPFUsuario;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JTextField lblLinhaInformativa;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNCasa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBairro;
    public static javax.swing.JFormattedTextField txtCPFPaciente;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtIDUsuario;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNCasa;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JPasswordField txtSenhaPS;
    // End of variables declaration//GEN-END:variables
}
