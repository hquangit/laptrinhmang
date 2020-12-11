/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.spi.LoginModule;
import model.Message;
import model.User;

/**
 *
 * @author KyThuat88
 */
public class ServerControl implements Runnable {

    private Socket client;
    private UserDao userDao = new UserDao();
    private User user = new User();
    private ServerControl opSc;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ServerControl(Socket client) {
        this.client = client;
        try {
            oos = new ObjectOutputStream(client.getOutputStream());            
            ois = new ObjectInputStream(client.getInputStream());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Object o = ois.readObject();
                System.out.println("Ket noi cmnr");
                if (o instanceof Message) {
                    Message request = (Message) o;
                    Object obj = request.getObject();

                    Message response; 
                    switch (request.getLabel()) {
                        case LOGIN:
                            User mUser = (User) obj;
                            Boolean result = userDao.login(mUser);
                            user = mUser;
                            if (result) {
                                response = new Message(user, Message.Label.LOGIN_SUCCESS);
                                
                            } else {
                                response = new Message(user, Message.Label.LOGIN_FAIL);
                            }

                            oos.writeObject(response); 
                            break;
                        
                        case LOGOUT:
                            userDao.updateStatus(user, "OFFLINE");
                            break;
                        
                        case REGISTER:                            
                            User nUser = (User) obj;
                            boolean resultz = userDao.insertUser(nUser);
                            user = nUser;
                            if (resultz) {
                                response = new Message(user, Message.Label.REGISTER_SUCCESS);
                                
                            } else {
                                response = new Message(user, Message.Label.REGISTER_FAIL);
                            }

                            oos.writeObject(response); 
                            break;
                            
                        case LIST_USERS:
                            
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
