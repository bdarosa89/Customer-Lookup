/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerlookup;

import java.sql.*;

/**
 *
 * @author Ben
 */
public class CustomerInfo {
    String name;
    String address;
    String email;
    String number;
    int rowsAffected;
   public void getInfo(String searchStr, Object searchParms, Object searchOrDestroy){
   if (searchParms== "By Phone" && searchOrDestroy == "Search"){//Check if Searching by phone.
        try{
         Connection conn=DriverManager.getConnection("jdbc:ucanaccess://src/customerlookup/us500.accdb");//access database in package
            Statement s = conn.createStatement();
            //Using a SELECT statement to retrieve info on database entity with matching phone number
            ResultSet rs = s.executeQuery("SELECT [first_name], [last_name], [address], [city], [state], [email] FROM [US500] WHERE [phone1]='"+ searchStr+"' OR [phone2]='"+ searchStr+"'");
            //Given results will be placed in respective variables
            while (rs.next()) {
                    this.name = rs.getString("first_name")+ " " + rs.getString("last_name");
                    this.address = rs.getString("address")+ "," + rs.getString("city")+", "+ rs.getString("state");
                    this.email = rs.getString("email");
                    this.number = searchStr;
        }   
        }
        catch(SQLException w){
        }
    }
        if (searchParms== "By Email" && searchOrDestroy=="Search"){//Check if searching by email.
        try{
         Connection conn=DriverManager.getConnection("jdbc:ucanaccess://src/customerlookup/us500.accdb");
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT [first_name], [last_name], [address], [city], [state], [phone1], [phone2] FROM [US500] WHERE [email]='"+ searchStr+"'");
            while (rs.next()) {
                    this.name = rs.getString("first_name")+ " " + rs.getString("last_name");
                    this.address = rs.getString("address")+ "," + rs.getString("city")+", "+ rs.getString("state");
                    this.number = rs.getString("phone1");
                    this.email = searchStr;
}   
        }
        catch(SQLException w){
        }
    }
        if (searchParms== "By Phone" && searchOrDestroy=="Delete"){//Check if deleting by phone.
        try{
         Connection conn=DriverManager.getConnection("jdbc:ucanaccess://src/customerlookup/us500.accdb");
            String sqlQuery = "delete from [US500] where [phone1] = '"+searchStr+"' OR [phone2] = '"+searchStr+"'";//Delete SQL statement by phone.
         Statement s = conn.createStatement();
            this.rowsAffected = s.executeUpdate(sqlQuery);
        }
        catch(SQLException w){
        }
    }   
        if (searchParms== "By Email" && searchOrDestroy=="Delete"){//Check if deleting by email.
        try{
         Connection conn=DriverManager.getConnection("jdbc:ucanaccess://src/customerlookup/us500.accdb");
            String sqlQuery = "delete from [US500] where [email] = '"+searchStr+"'";//Delete sql statement by email.
         Statement s = conn.createStatement();
            this.rowsAffected = s.executeUpdate(sqlQuery);//Find affected rows.
        }
        catch(SQLException w){
        }
    }
        
   };
   
   
   public String getName(){
       return this.name;
   }
   public String getAddress(){
       return this.address;
   }
   public String getNumber(){
       return this.number;
   }
   public String getEmail(){
       return this.email;
   }
   public int getRowsDeleted(){
       return this.rowsAffected;
   }
}
