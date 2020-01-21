/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package american_corner;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Prarthana
 */
public class American_Corner {

   
    public static void main(String[] args) {
        
        String url="jdbc:mysql://localhost:3306/america_corner";
        String uname="root";
        String pwd="";
        
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(url,uname,pwd); 
    
  

}catch(Exception e){
    System.out.println(e);}  
    }
    
}
