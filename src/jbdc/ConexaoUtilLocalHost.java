package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoUtilLocalHost {

    private static ConexaoUtilLocalHost connexaoUtil;

    public static ConexaoUtilLocalHost getInstance() {
        /* Se conexãoUtil igual a nula */
        if (connexaoUtil == null) {
            /* Pegamos esse objeto e Criamos uma Nova conexão */
            connexaoUtil = new ConexaoUtilLocalHost();
        }
        /*
         * caso contrário retornamos sempre a conexaoUtil do objeto requisitado, tanto a
         * já existente se for o caaso , ou uma Nova conexão se não houver conexão
         */
        return connexaoUtil;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sisseg", "root", "1020");

    }
    
    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("conectado sisHospeda");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sisseg", "root", "1020");

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

            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/sisseg", "root", "1020");

            conexao.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            
            e.getMessage();

        }
        return false;

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
