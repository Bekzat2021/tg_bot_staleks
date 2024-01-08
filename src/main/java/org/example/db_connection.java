package org.example;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Properties;

public class db_connection {
    public static Connection connect(){
        String url="jdbc:postgresql://localhost/staleks_tg_bot";
        Properties props=new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "pg_sql");
        //props.setProperty("ssl", "true");

        Connection conn=null;
        try {
            conn=DriverManager.getConnection(url, props);
            //System.out.println("Connection is succeful");

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return conn;
    }

    public static void selectAll(){
        if (connect()!=null){
            String select_all="SELECT * FROM users";
            try{
                Statement stmt=connect().createStatement();
                ResultSet rs_set=stmt.executeQuery(select_all);

//                while (rs_set.next()){
//                    System.out.println(rs_set.getString("id")+" "+
//                            rs_set.getString("first_name")+" "+
//                            rs_set.getString("last_name")+" "+
//                            rs_set.getString("department")+" "+
//                            rs_set.getString("telegram_id"));
//                }
            }catch (SQLException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    public static boolean insertUser(String first_name, String last_name, String department,
                                     Long telegram_id){
        if (connect()!=null){
            String insertUser="INSERT INTO users(first_name, last_name, department, telegram_id)" +
                    "VALUES('"+first_name+"', '"+last_name+"', '"+department+"', "+telegram_id+");";
            System.out.println(insertUser);
            try{
                Statement stmt=connect().createStatement();
                ResultSet rs_set=stmt.executeQuery(insertUser);
                if (!rs_set.next()){
                    return false;
                }
            }catch (SQLException exception){
                System.out.println(exception.getMessage());
            }
        }
        return true;
    }

    public static String userDept(Long user_id){
        PreparedStatement findByIdStmt=null;
        ResultSet rs_set=null;
        Connection conn=connect();
        if (conn!=null){
            try {
                String findById="SELECT department FROM users WHERE telegram_id=?;";
                findByIdStmt=conn.prepareStatement(findById);
                findByIdStmt.setLong(1, user_id);

                rs_set=findByIdStmt.executeQuery();
                if (rs_set.next()){
                    return rs_set.getString("department");
                }
            }catch (SQLException exception){
                System.out.println(exception.getMessage());
            }finally {
                try {
                    conn.close();
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
                if (findByIdStmt!=null){
                    try{
                        findByIdStmt.close();
                    }catch (SQLException exception){
                        System.out.println(exception.getMessage());
                    }
                }
                if (rs_set!=null){
                    try{
                        rs_set.close();
                    }catch (SQLException exception){
                        System.out.println(exception.getMessage());
                    }
                }
            }
        }
        return null;
    }

    public static boolean isUserRegistred(Long userId){
        if (connect()!=null){
            String findById="SELECT * FROM users WHERE telegram_id="+userId+";";
            try{
                Statement stmt=connect().createStatement();
                ResultSet rs_set=stmt.executeQuery(findById);
                if (!rs_set.next()){
                    return false;
                }
            }catch (SQLException exception){
                System.out.println(exception.getMessage());
            }

        }
        return true;
    }
}