package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoUtil {

    private static ConexaoUtil connexaoUtil;

    public static ConexaoUtil getInstance() {
        /* Se conexãoUtil igual a nula */
        if (connexaoUtil == null) {
            /* Pegamos esse objeto e Criamos uma Nova conexão */
            connexaoUtil = new ConexaoUtil();
        }
        /*
         * caso contrário retornamos sempre a conexaoUtil do objeto requisitado, tanto a
         * já existente se for o caaso , ou uma Nova conexão se não houver conexão
         */
        return connexaoUtil;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3308/inovec87_sisseg?autoReconnect=true&useSSL=false", "root", "1020");

    }

    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("conectado sisHospeda");
        return DriverManager.getConnection("jdbc:mysql://localhost:3308/inovec87_sisseg?autoReconnect=true&useSSL=false", "root", "1020");

    }

    //************************************************//
    //CONEXÃO COM BANCO DE DADOS NAS NUVENS UOL HOST //
    //**********************************************//
//  
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//  
//        Class.forName("com.mysql.jdbc.Driver");
//        return DriverManager.getConnection("jdbc:mysql://segsis.mysql.uhserver.com:3306/segsis", "datorres", "Pmaa2111791@2@");
//
//    }
    public boolean ConexaoVerificaEstado() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            //**********************************************
            //CORRIGINDO ERRO DE SSL NA CLASSE DE CONEXÃO// 
            //********************************************
            //FÓRUM DE DISCURSÃO:https://qastack.com.br/programming/34189756/warning-about-ssl-connection-when-connecting-to-mysql-database
            //O URL da sua conexão deve se parecer com o abaixo,                       //
            //jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false     //
            //Isso desativará o SSL e também suprimirá os erros de SSL.              //
            //*************************************************************************
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3308/inovec87_sisseg?autoReconnect=true&useSSL=false", "root", "1020");
            System.out.println("1 - Conexão Verifica Estado CAMADA DAO:"+ conexao);
            conexao.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            e.getMessage();

        }
        return false;

    }

    
    public String returnDadosConexao() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            String BD="inovec87_sisseg";
            //**********************************************
            //CORRIGINDO ERRO DE SSL NA CLASSE DE CONEXÃO// 
            //********************************************
            //FÓRUM DE DISCURSÃO:https://qastack.com.br/programming/34189756/warning-about-ssl-connection-when-connecting-to-mysql-database
            //O URL da sua conexão deve se parecer com o abaixo,                       //
            //jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false     //
            //Isso desativará o SSL e também suprimirá os erros de SSL.              //
            //*************************************************************************
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:"+3308+"/"+BD+"?autoReconnect=true&useSSL=false", "root", "1020");
            System.out.println("1 - Conexão Verifica Estado CAMADA DAO:"+ conexao);
            conexao.close();

            return "Container Dockerizado MYSQL\n"
                    + "Localhost Port:"+3308+"\n Banco Dados:"+BD;
            
        } catch (Exception e) {
            e.printStackTrace();

            e.getMessage();

        }
        return "Não foi Possível estabelecer conexão com o BD";

    }
    
    
    
    
    
    
    
   //************************************************//
    //CONEXÃO COM BANCO DE DADOS NAS NUVENS UOL HOST //
    //**********************************************//
//    
//    public boolean ConexaoVerificaEstado() {
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection conexao = DriverManager.getConnection("jdbc:mysql://segsis.mysql.uhserver.com:3306/segsis", "datorres", "Pmaa2111791@2@");
//
//            conexao.close();
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return false;
//
//    }
//


    
}
