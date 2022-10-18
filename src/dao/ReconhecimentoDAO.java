package dao;

import exceptions.PersistenciaException;
import dto.ReconhecimentoDTO;
import jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * AULA 14: 3 CAMADA DAO(Data Access Object)O padrão de projeto DAO surgiu com a
 * necessidade de separarmos a lógica de negócios da lógica de persistência de
 * dados. Este padrão permite que possamos mudar a forma de persistência sem que
 * isso influencie em nada na lógica de negócio, além de tornar nossas classes
 * mais legíveis. Classes DAO são responsáveis por trocar informações com o SGBD
 * e fornecer operações CRUD e de pesquisas, elas devem ser capazes de buscar
 * dados no banco e transformar esses em objetos ou lista de objetos, fazendo
 * uso de listas genéricas (BOX 3), também deverão receber os objetos, converter
 * em instruções SQL e mandar para o banco de dados.Toda interação com a base de
 * dados se dará através destas classes, nunca das classes de negócio, muito
 * menos de formulários.Se aplicarmos este padrão corretamente ele vai abstrair
 * completamente o modo de busca e gravação dos dados, tornando isso
 * transparente para aplicação e facilitando muito na hora de fazermos
 * manutenção na aplicação ou migrarmos de banco de dados.
 *
 * Também conseguimos centralizar a troca de dados com o SGBD (Sistema
 * Gerenciador de Banco de Dados), teremos um ponto único de acesso a dados,
 * tendo assim nossa aplicação um ótimo design orientado a objeto.
 */
public class ReconhecimentoDAO implements GenericDAO<ReconhecimentoDTO> {

    @Override
    public void inserir(ReconhecimentoDTO reconheceDTO) throws PersistenciaException {

        try {

            /**
             * Dentro do bloco try catch temos um objeto do tipo Connection e
             * criamos um objeto connection que recebe da nossa Classe
             * ConexaoUtil um método getInstance do Padrão Singleton e
             * getConection que é nossa conexão de Fato
             */
            Connection connection = ConexaoUtil.getInstance().getConnection();

          
            /**
             * Esse recurso das interrogações na estrutura do código SQL é
             * utilizada para facilitar o NAO uso do SQL Injection esse é um
             * recurso muito interessante do JDBC que facilitar dessa forma a
             * flexibilidade na montagem do nosso código SQL
             */
            String sql = "INSERT INTO reconhecimentomaquina(dt_hora_conectou,serial_hd,serial_cpu,serial_placa_mae,informacoes_diversas,liberado_bloqueado,usuario_responsavel) VALUES(?,?,?,?,?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, reconheceDTO.getDt_hora_conectouDto());
            statement.setString(2, reconheceDTO.getSerialHdDto());
            statement.setString(3, reconheceDTO.getSerialCPUDto());
            statement.setString(4, reconheceDTO.getSerial_placa_maeDto());
            statement.setString(5, reconheceDTO.getInformacoes_diversasDto());
            statement.setString(6, reconheceDTO.getLiberado_bloqueadoDto());
            statement.setString(7, reconheceDTO.getUsuario_responsavel_cadastro());
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(ReconhecimentoDTO reconheceDTO) throws PersistenciaException {
     
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE reconhecimentomaquina SET liberado_bloqueado=? WHERE id_reconhecimento=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, reconheceDTO.getLiberado_bloqueadoDto());
            statement.setInt(2, reconheceDTO.getId_reconhecimentoDto());
            /**
             * executar o statement
             */
            statement.execute();
            
            
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }


    }

    @Override
    public void deletar(ReconhecimentoDTO reconheceDTO) throws PersistenciaException {
        
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * O código abaixo é uma pesquisa montada na linguagem estruturada
             * de consulta que tem como funcionalidade buscar no campo
             * nome_medico o nome digitado pelo usuário no FormMedico campo
             * txtPesquisa que foi setado pelo metodo de encapsulamento
             * setPesquisa() contido na camada MedicoDTO e agora recuperado pelo
             * parametro medico que recupera o valor digitado pelo
             * getPesquisa(). OBSERVAÇÃO IMPORTANTE:O operador ILIKE é
             * específico do PostgreSQL e seu comportamento é semelhante ao
             * LIKE. A única diferença é que ele é case-insensitive, ou seja,
             * não diferencia maiúsculas de minúsculas.
             * Fontes:https://pt.stackoverflow.com/questions/96926/como-fazer-consulta-sql-que-ignora-mai%C3%BAsculas-min%C3%BAsculas-e-acentos
             */
            /**
             * 12min
             */
            String sql = "DELETE FROM reconhecimentomaquina WHERE id_reconhecimento=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, reconheceDTO.getId_reconhecimentoDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            //  ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO:O Registro não pode ser DELETADO por um dos motivos:\n"
                    + "1º)Falha da comunicação entre o Banco de Dados e Aplicação;"
                    + "\n2º)Ou Estado selecionado pode conter Cidades relacionadas a ele."
            );

        }

    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ReconhecimentoDTO> listarTodos() throws PersistenciaException {

        List<ReconhecimentoDTO> listaMaquinas = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM reconhecimentomaquina";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ReconhecimentoDTO reconheceDTO = new ReconhecimentoDTO();

                reconheceDTO.setId_reconhecimentoDto(resultSet.getInt("id_reconhecimento"));
                reconheceDTO.setSerialHdDto(resultSet.getString("serial_hd"));
                reconheceDTO.setDt_hora_conectouDto(resultSet.getString("dt_hora_conectou"));
                reconheceDTO.setSerialCPUDto(resultSet.getString("serial_cpu"));
                reconheceDTO.setSerial_placa_maeDto(resultSet.getString("serial_placa_mae"));
                reconheceDTO.setInformacoes_diversasDto(resultSet.getString("informacoes_diversas"));
                reconheceDTO.setLiberado_bloqueadoDto(resultSet.getString("liberado_bloqueado"));
                reconheceDTO.setUsuario_responsavel_cadastro(resultSet.getString("usuario_responsavel"));

                
                listaMaquinas.add(reconheceDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaMaquinas;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReconhecimentoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReconhecimentoDTO buscarPor(ReconhecimentoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(ReconhecimentoDTO reconhecimentoDto) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean maquinaDuplicada = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM reconhecimentomaquina where serial_placa_mae='" + reconhecimentoDto.getSerial_placa_maeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                maquinaDuplicada = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return maquinaDuplicada;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + e.getMessage());
        }
        return maquinaDuplicada;

    }

    
     public ReconhecimentoDTO filtrarAoClicarCodio(int codigo) throws PersistenciaException {
    
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        ReconhecimentoDTO reconheceDTO = null;

        try {

            
            Connection connection = ConexaoUtil.getInstance().getConnection();

        
           //String sql = "SELECT *FROM tbusuarios where id_reconhecimento='" + modelo.getId_reconhecimentoDto()+ "'";
           String sql = "SELECT *FROM reconhecimentomaquina where id_reconhecimento=" + codigo;

        
            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

       
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

        
                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                reconheceDTO = new ReconhecimentoDTO();
                reconheceDTO.setId_reconhecimentoDto(resultSet.getInt("id_reconhecimento"));
                reconheceDTO.setDt_hora_conectouDto(resultSet.getString("dt_hora_conectou"));
                reconheceDTO.setSerialHdDto(resultSet.getString("serial_hd"));
                reconheceDTO.setSerialCPUDto(resultSet.getString("serial_cpu"));
                reconheceDTO.setSerial_placa_maeDto(resultSet.getString("serial_placa_mae"));
                reconheceDTO.setInformacoes_diversasDto(resultSet.getString("informacoes_diversas"));
                reconheceDTO.setLiberado_bloqueadoDto(resultSet.getString("liberado_bloqueado"));
                reconheceDTO.setUsuario_responsavel_cadastro(resultSet.getString("usuario_responsavel"));
                return reconheceDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();

            JOptionPane.showMessageDialog(null, "Erro metodo novo" + e.getMessage());
        }

        return null;

    }

    @Override
    public ReconhecimentoDTO filtrarAoClicar(ReconhecimentoDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
    
   