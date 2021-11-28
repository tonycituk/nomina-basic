/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nominapoo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tonyc
 */
public class NominaCRUD {

    private Connector connector;

    public NominaCRUD() {
        DatabaseProps props = new DatabaseProps();
        props.loadDbInfo();
        props.loadCredentials();
        this.connector = new Connector(props.getUrlString(), props.getCredentials());
    }

    //Retorna todas las filas obtenidas de la tabla que le mandemos en tableName
    public ResultSet getAll(String tableName) {
        connector.connect();
        ResultSet rs = null;
        try {
            Statement stmnt = connector.getConnection().createStatement();
            rs = stmnt.executeQuery("SELECT * FROM " + tableName + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.close();
        return rs;
    }

    //Inserta en la tabla tableName a las columnas columns[] los valores values[]
    public int insertOne(String tableName, String columns[], String values[]) {
        //"insert into student(Id,name,number) values('1','rachel','45')");
        String queryString = "INSERT INTO " + tableName + "(";
        for (int i = 0; i < columns.length; i++) {
            queryString += columns[i];
            if (i != columns.length - 1) {
                queryString += ", ";
            } else {
                queryString += ") values(";
            }
        }
        for (int i = 0; i < values.length; i++) {
            queryString += values[i];
            if (i != values.length - 1) {
                queryString += ", ";
            } else {
                queryString += ");";
            }
        }
        //System.out.println(queryString);
        int result = 0;
        connector.connect();
        try {
            Statement stmnt = connector.getConnection().createStatement();
            result = stmnt.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.close();
        return result;
    }

    //Elimina en la tabla tableName where the id is equal to the id
    public int deleteOne(String tableName, int id) {
        //"insert into student(Id,name,number) values('1','rachel','45')");
        String queryString = "DELETE FROM " + tableName + " WHERE id=" + String.valueOf(id) + ";";
        //System.out.println(queryString);
        int result = 0;
        connector.connect();
        try {
            Statement stmnt = connector.getConnection().createStatement();
            result = stmnt.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.close();
        return result;
    }

    //Elimina en la tabla tableName where the id is equal to the id
    public int updateOne(String tableName, String columns[], String values[], int id) {
        //"insert into student(Id,name,number) values('1','rachel','45')");
        String queryString = "UPDATE " + tableName + " SET";
        //String sql = "update employee set name='Michael Sam' where emp_id=1";
        
        for (int i = 0; i < columns.length; i++) {
            queryString += " " + columns[i] + "=" + values[i];
            if (i != columns.length - 1) {
                queryString += ",";
            }
        }
        queryString += " WHERE id=" + String.valueOf(id) + ";";
        //System.out.println(queryString);
        /*
        */
        int result = 0;
        connector.connect();
        try {
            Statement stmnt = connector.getConnection().createStatement();
            result = stmnt.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.close();
        return result;
    }

    public static void main(String[] args) {
        
        NominaCRUD crud = new NominaCRUD();
        
        String[] columns = {"nombre", "numhoras"};
        String[] values = {"'lol'", "6"};
        
        crud.updateOne("admin", columns, values, 2);
    }
}
