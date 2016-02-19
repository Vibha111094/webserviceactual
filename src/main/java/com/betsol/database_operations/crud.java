package com.betsol.database_operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class crud {
	//private String user_id, password;
	
	public static Connection database_connection(String user, String password){ 
	    Connection conn = null;
		Statement stmt = null;
		String DB_URL = "jdbc:mysql://localhost:3306/user";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			conn = DriverManager.getConnection(DB_URL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
}

	public static void  create_user(String obj){
		Connection conn = crud.database_connection("root",null);
	    Statement stmt = null;
	    JSONObject jsonObject = null;
    	String  user_name= null;
    	String 	password= null ;
    	String 	phone_number= null ;
    	String  address= null ;
    	String  first_name= null ;
    	String   last_name= null ;
		try {
			jsonObject = new JSONObject(obj);
			user_name = jsonObject.getString("user_name");
			password=jsonObject.getString("password");
			phone_number =jsonObject.getString("phone_number");
	    	address =jsonObject.getString("address");
	        first_name =jsonObject.getString("first_name");
	    	last_name =jsonObject.getString("last_name");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
		}
	      try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //System.out.println(name);
	    String sql = "INSERT INTO user_info VALUES ('"+user_name+"','"+password+"','"+phone_number+"','"+address+"','"+first_name+"','"+last_name+"')";
	      try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      System.out.println("Inserted records into the table...");
//return name;
	   }
	 

	
	 public static  Response retrieve_user(String name) throws SQLException {
	    	name = name.replaceAll("(\\n)", "");
	    	String one="'", two="'";
	    	JSONObject jobj = new JSONObject();
	    	Statement stmt =null;
	    	String  user_name1= null;
		   	String 	password= null ;
		    String 	phone_number= null ;
		    String  address= null ;
		    String  first_name= null ;
		    String  last_name= null ;
	    	String USER = "root";
			String PASS = "";
			Connection conn = crud.database_connection("root",null);
			stmt = (Statement) conn.createStatement();
			String sql = "SELECT * FROM user_info WHERE user_name ='"+name+"'";
			
			ResultSet rs = null;
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
			    user_name1= rs.getString("user_name");
			    password= rs.getString("phone_number") ;
			    phone_number= rs.getString("password") ;
			    address= rs.getString("address") ;
			    first_name= rs.getString("first_name") ;
			    last_name= rs.getString("last_name") ;
			 }
			 rs.close();
			 jobj.put("user_name", user_name1);
			 jobj.put("phone_number",phone_number);
			 jobj.put("password", password);
			 jobj.put("address", address);
			 jobj.put("first_name", first_name);
			 jobj.put("last_name",last_name );
			 
		     return Response.ok(jobj.toString()).build();
	    }
	 
	 public static Response update_user(String name) {
	    	name = name.replaceAll("(\\n)", "");
	    	System.out.println(name);
	    	Connection conn = crud.database_connection("root",null);
	        Statement stmt = null ;
	        try {
				stmt = (Statement) conn.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sql1 = "UPDATE user_info SET `password` = 'surabhi'  WHERE  user_name='"+name+"'";
	        System.out.println(sql1);
			 try {
				 stmt.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			Response res = null;
			try {
				res = crud.retrieve_user(name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
	    }
}





