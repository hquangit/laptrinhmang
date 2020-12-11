/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author khanh
 */
public class Dao {
    protected Connection conn;
    private String jdbcURL = "jdbc:mysql://localhost:3306/laptrinhmang";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    public Dao() {
        if(conn == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
