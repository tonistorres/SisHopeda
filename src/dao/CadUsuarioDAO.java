package dao;

import dto.CadUsuarioDTO;
import exceptions.PersistenciaException;
import jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class CadUsuarioDAO implements GenericDAO<CadUsuarioDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<CadUsuarioDTO> listarTodos() throws PersistenciaException {

        List<CadUsuarioDTO> listaCadUsuarios = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios order by login";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CadUsuarioDTO cadUsuarioDTO = new CadUsuarioDTO();

                cadUsuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                cadUsuarioDTO.setLoginDto(resultSet.getString("login"));
                cadUsuarioDTO.setSenhaDto(resultSet.getString("senha"));
                cadUsuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                cadUsuarioDTO.setCelularDto(resultSet.getString("celular_principal"));

                /**
                 * Adiciona na listaCadUsuarios todos os dados capturado pelo
                 * laço e adicionado no objeto cadUsuarioDTO
                 */
                listaCadUsuarios.add(cadUsuarioDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return listaCadUsuarios;

    }

    public List<CadUsuarioDTO> filtrarUsuarioPesqRapida(String pesquisarUsuarios) throws PersistenciaException {

        List<CadUsuarioDTO> listaCadUsuario = new ArrayList<CadUsuarioDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE login LIKE '%" + pesquisarUsuarios + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CadUsuarioDTO cadUsuarioDTO = new CadUsuarioDTO();

                cadUsuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                cadUsuarioDTO.setLoginDto(resultSet.getString("login"));
                cadUsuarioDTO.setSenhaDto(resultSet.getString("senha"));
                cadUsuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                listaCadUsuario.add(cadUsuarioDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaCadUsuario;
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Esse método que recebe como parâmetro um numero do tipo inteiro irá fazer
     * uma pesquisa no Banco de Dados Infoq na tabela de usuaios e em seguida
     * retorna o usuario encontrado caso exista, pois, trata-se de um método com
     * retorno do tipo CadUsuarioDTO e que recebe como parâmetro um número inteiro
     * vindo de uma JTable
     */
    public CadUsuarioDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        CadUsuarioDTO cadUsuarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios where iduser=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                cadUsuarioDTO = new CadUsuarioDTO();

                cadUsuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                cadUsuarioDTO.setLoginDto(resultSet.getString("login"));
                cadUsuarioDTO.setSenhaDto(resultSet.getString("senha"));
                cadUsuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                return cadUsuarioDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    @Override
    public void inserir(CadUsuarioDTO cadUsuarioDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbusuarios(login,senha,perfil) VALUES(?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cadUsuarioDTO.getLoginDto());
            statement.setString(2, cadUsuarioDTO.getSenhaDto());
            statement.setString(3, cadUsuarioDTO.getPerfilDto());
           
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(CadUsuarioDTO cadUsuarioDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
			 * Esse recurso das interrogações na estrutura do código SQL é utilizada para
			 * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
			 * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
			 * SQL
             */
            String sql = "UPDATE tbusuarios SET login=?,senha=?,perfil=? WHERE iduser=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cadUsuarioDTO.getLoginDto());
            statement.setString(2, cadUsuarioDTO.getSenhaDto());
            statement.setString(3, cadUsuarioDTO.getPerfilDto());

            statement.setInt(4, cadUsuarioDTO.getIdUserDto());
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
    public void deletar(CadUsuarioDTO obj) throws PersistenciaException {
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
            String sql = "DELETE FROM tbusuarios WHERE iduser=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, obj.getIdUserDto());
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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na Deleção do Dado\nErro:" + ex.getMessage());

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
    public CadUsuarioDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CadUsuarioDTO buscarPor(CadUsuarioDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(CadUsuarioDTO cadUsuarioDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean usuarioDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbusuarios where cpf='" + cadUsuarioDTO.getCpfDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                usuarioDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return usuarioDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return usuarioDuplicado;

    }

    @Override
    public CadUsuarioDTO filtrarAoClicar(CadUsuarioDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        CadUsuarioDTO cadUsuarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE login LIKE'%" + modelo.getLoginDto()+ "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
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
                cadUsuarioDTO = new CadUsuarioDTO();
                cadUsuarioDTO.setIdUserDto(resultSet.getInt("iduser")); // 1º campo na tabela usuario
                cadUsuarioDTO.setLoginDto(resultSet.getString("login"));//4º campo na tabela usuario
                cadUsuarioDTO.setSenhaDto(resultSet.getString("senha"));//5º campo na tabela usuario
                cadUsuarioDTO.setPerfilDto(resultSet.getString("perfil"));//6º campo na tabela usuario

                return cadUsuarioDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }
  

}
