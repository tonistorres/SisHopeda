package bo;

import dao.CadUsuarioDAO;
import dto.CadUsuarioDTO;
import dto.CadUsuarioDTO;
import exceptions.NegocioException;
import exceptions.PersistenciaException;
import exceptions.ValidacaoException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CadUsuarioBO {

    CadUsuarioDAO cadUsuarioDAO = new CadUsuarioDAO();

    /**
     * Formatador de Datas
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void cadastrar(CadUsuarioDTO cadUsuarioDTO) throws NegocioException {

        try {

            CadUsuarioDAO cadUsuarioDAO = new CadUsuarioDAO();

            cadUsuarioDAO.inserir(cadUsuarioDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void UsuarioBO(CadUsuarioDTO cadUsuarioDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        CadUsuarioDAO cadUsuarioDAO;
        try {
            cadUsuarioDAO = new CadUsuarioDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            cadUsuarioDAO.atualizar(cadUsuarioDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirPorCodigoBO(int codigo) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        CadUsuarioDAO cadUsuarioDAO;

        try {

            cadUsuarioDAO = new CadUsuarioDAO();
            cadUsuarioDAO.deletarPorCodigoTabela(codigo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(CadUsuarioDTO cadUsuarioDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        CadUsuarioDAO cadUsuarioDAO;
        try {
            cadUsuarioDAO = new CadUsuarioDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            cadUsuarioDAO.deletar(cadUsuarioDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    public String ValidarForcaSenha(String senha) throws ValidacaoException {

        String strRetorno = null;

        if (senha == null || senha.equals("")) {

            strRetorno = "Campo Senha Obrigatório.";

            throw new ValidacaoException("Campo SENHA é obrigatório.");

        } else if (senha.length() != 8) {

            strRetorno = "Campo senha deve conter exatamente 8 caracter.";
            throw new ValidacaoException("Campo senha deve conter \nexatamente 8 caracter.");
        } else {

            char c;

            //criando dois cotadores e inicializando-os
            int ctalfa = 0;
            int ctnum = 0;
            int ctcarctEspecial = 0;

            //laço de repetição for agora 
            for (int i = 0; i < senha.length(); i++) {

                c = senha.charAt(i);//i-ésimo caractere da strin s

                // verificando se é um caractere alfabético
                if (((c >= 'A') && (c <= 'Z'))
                        || ((c >= 'a') && (c <= 'z'))) {
                    ctalfa = ctalfa + 1;
                    System.out.printf("%c ---> %da. letra\n", c, ctalfa);
                } // verificando se é um caractere numérico
                else if ((c >= '0') && (c <= '9')) {
                    ctnum = ctnum + 1;
                    System.out.printf("%c ---> %do. número\n", c, ctnum);
                } else {
                    ctcarctEspecial = ctcarctEspecial + 1;
                    System.out.printf("%c\n", c);
                }
            }

            if (ctalfa > 0 && ctnum > 0 && ctcarctEspecial > 0) {
                strRetorno = "Senha Forte";
                return strRetorno;
            } else if ((ctalfa > 0 && ctnum > 0) || (ctalfa > 0 && ctcarctEspecial > 0) || (ctnum > 0 && ctcarctEspecial > 0)) {
                strRetorno = "Senha Mediana";
                return strRetorno;
            } else {
                strRetorno = "Senha Fraca";
                return strRetorno;
            }

        }

    }

    public boolean validarLoginUsuarioEdicao(CadUsuarioDTO usuarioLogin) throws ValidacaoException {

        boolean ehValido = true;

        if (usuarioLogin.getLoginDto() == null || usuarioLogin.getLoginDto().equals("")) {
            ehValido = false;
            throw new ValidacaoException("Campo Login é obrigatório.");

        } else if (usuarioLogin.getLoginDto().length() > 25) {
            ehValido = false;
            throw new ValidacaoException("Campo Login aceita no máximo 25 caracter.");

        } else {

            ehValido = true;

        }
        return ehValido;
    }

    public boolean duplicidadeUsuarios(CadUsuarioDTO cadUsuarioDTO) throws PersistenciaException {

        CadUsuarioDAO cadUsuarioDAO = new CadUsuarioDAO();

        boolean verificaDuplicidade = cadUsuarioDAO.verificaDuplicidade(cadUsuarioDTO);

        return verificaDuplicidade;
    }

    public List<CadUsuarioDTO> listarTodosUsuariosBO() throws PersistenciaException {

        List<CadUsuarioDTO> listaDeUsuariosBO = new ArrayList<>();

        try {

            listaDeUsuariosBO = cadUsuarioDAO.listarTodos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeUsuariosBO;

    }

    /**
     * Método validaNome() é um método público que tem como retorno uma boolean
     * e recebe como parâmetro uma String.Onde será analisado por este método sé
     * o nome é nulo ou tem um número de caracter superior ao permetido no banco
     * de dados.Método que fica alojado na camada BO(Busines Object, camada de
     * negocio)
     *
     */
    public boolean validaNome(CadUsuarioDTO cadUsuarioDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (cadUsuarioDTO.getLoginDto()== null || cadUsuarioDTO.getLoginDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo Login Obrigatório");

        }
        if (cadUsuarioDTO.getLoginDto().length() > 50) {
            ehValido = false;
            throw new ValidacaoException("Campo Login aceita no MAX 50 chars");

        }

        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaEndereco(String endereco) throws ValidacaoException {

        boolean ehValido = true;

        if (endereco == null || endereco.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco Obrigatório");

        }

        if (endereco.length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco aceita no MAX 45 chars");

        }

        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaDtNascimento(String dtNascimento) throws ValidacaoException {

        boolean ehValido = true;

        if (dtNascimento == null || dtNascimento.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo Data Nascimento Obrigatório");

        } else {

            try {
                dateFormat.parse(dtNascimento);
                ehValido = true;
            } catch (ParseException ex) {
                ehValido = false;
                throw new ValidacaoException("Formato inválido de Data! \n Ex:01/01/2001");

            }
        }

        return ehValido;
    }

    public void removeTudo() throws ValidacaoException {

        try {

            cadUsuarioDAO.deletarTudo();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:" + e.getMessage());
            throw new ValidacaoException(e.getMessage());

        }

    }

    public void atualizarBO(CadUsuarioDTO cadUsuarioDTO) {

        try {

            cadUsuarioDAO.atualizar(cadUsuarioDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
  

}//aqui fim da classe 
