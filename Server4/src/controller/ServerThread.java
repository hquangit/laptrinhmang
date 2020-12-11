/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author khanh
 */
public class ServerThread{
    public static ArrayList<ServerControl> clients = new ArrayList<>();

    public ServerThread() {
    }

    public static void main(String[] args) {
    //    ServerSocket server = new ServerSocket(8888);
        while (true) {            
            try{
                int port = 8888;
                ServerSocket server = new ServerSocket(port);
                while(!Thread.currentThread().isInterrupted()){
                    Socket client = server.accept();
                    System.out.println("Da vao thread");
                    ServerControl sc = new ServerControl(client);
                    System.out.println("new Server controller");
                    clients.add(sc);
                    new Thread(sc).start();
                }
                
                Thread.sleep(100);
            }catch(Exception ex){
                ex.printStackTrace();
            }  
        }
        
    }
    
}
