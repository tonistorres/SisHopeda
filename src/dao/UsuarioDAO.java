package dao;

import dto.UsuarioDTO;
import exceptions.PersistenciaException;
import jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO implements GenericDAO<UsuarioDTO> {
    @Override
    public List<UsuarioDTO> listarTodos() throws PersistenciaException {
        List<UsuarioDTO> listaDeUsuarios = new ArrayList<>();
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuariosbancodados order by usuario";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
//                usuarioDTO.setBancoDadosDto(resultSet.getString("banco_dados"));
//                usuarioDTO.setUrlDto(resultSet.getString("url"));
                listaDeUsuarios.add(usuarioDTO);
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listaDeUsuarios;
    }

    public List<UsuarioDTO> filtrarUsuarioPesqRapida(String pesquisarUsuarios) throws PersistenciaException {
        List<UsuarioDTO> listaDeUsuario = new ArrayList<UsuarioDTO>();
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuariosbancodados WHERE usuario LIKE '%" + pesquisarUsuarios + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                listaDeUsuario.add(usuarioDTO);
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listaDeUsuario;
    }
    public UsuarioDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {
        UsuarioDTO usuarioDTO = null;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuariosbancodados where iduser=" + codigo;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                return usuarioDTO;
            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }
        return null;
    }

    @Override
    public void inserir(UsuarioDTO usuarioDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "INSERT INTO tbusuariosbancodados(usuario,senha,banco_dados,url) VALUES(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDTO.getUsuarioDto());
            statement.setString(2, usuarioDTO.getSenhaDto());
            statement.execute();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(UsuarioDTO usuarioDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "UPDATE tbusuariosbancodados SET usuario=?,senha=?,banco_dados=?,url=? WHERE iduser=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDTO.getUsuarioDto());
            statement.setString(2, usuarioDTO.getSenhaDto());
            statement.setInt(5, usuarioDTO.getIdUserDto());
            statement.execute();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(UsuarioDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "DELETE FROM tbusuariosbancodados WHERE iduser=?";
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdUserDto());
            statement.execute();
            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
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
    public UsuarioDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioDTO buscarPor(UsuarioDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(UsuarioDTO usuarioDTO) throws PersistenciaException {
        boolean usuarioDuplicado = false;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuariosbancodados where usuario='" + usuarioDTO.getUsuarioDto() + "'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioDuplicado = true;
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
    public UsuarioDTO filtrarAoClicar(UsuarioDTO modelo) throws PersistenciaException {
        UsuarioDTO usuarioDTO = null;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuariosbancodados WHERE usuario LIKE'%" + modelo.getUsuarioDto() + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                return usuarioDTO;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    public UsuarioDTO logar(UsuarioDTO usuarioDTO) throws PersistenciaException {
       
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbusuarios WHERE login=? AND senha=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuarioDTO.getLoginDto());
            statement.setString(2, usuarioDTO.getSenhaDto());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setCelularDto(resultSet.getString("celular_principal"));
                usuarioDTO.setCpfDto(resultSet.getString("CPF"));
                usuarioDTO.setEmailDto(resultSet.getString("email_principal"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                return usuarioDTO;
            }
           
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }
        return null;
    }

}
