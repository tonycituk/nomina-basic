/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nominapoo.mvc.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nominapoo.db.NominaCRUD;
import nominapoo.mvc.model.Admin;

/**
 *
 * @author tonyc
 */
public class AdminController {

    ArrayList<Admin> admins = new ArrayList<Admin>();
    NominaCRUD crud = new NominaCRUD();

    //Retorna todos los admins de la base de datos
    public ArrayList<Admin> getAdmins() {
        //Vacía el array
        this.admins.removeAll(this.admins);
        try {
            ResultSet adminRows = this.crud.getAll("admin");

            while (adminRows.next()) {
                //Crea un nuevo Admin con la info obtenida del resultset que nos devolvió this.crud.getAll("admin");
                Admin newAdmin = new Admin(adminRows.getInt(1), adminRows.getString(2));
                //Guarda el Admin en nuestro arreglo de Admins admins
                this.admins.add(newAdmin);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.admins;

    }

    //Retorna todos los admins que ya tenemos localmente
    public ArrayList<Admin> getLocalAdmins() {
        return this.admins;
    }

    //Utileria para agregar comillas a los valores que son varchar en la db
    private String addComillas(String cadena) {
        return "'" + cadena + "'";
    }

    //Agregar nuevo admin
    public int addAdmin(Admin admin) {
        String[] columns = {"id", "nombre"};
        String[] values = {"NULL", addComillas(admin.getNombre())};
        return this.crud.insertOne("admin", columns, values);
    }

    //Elimina un admin
    public int deleteAdmin(Admin admin) {
        return this.crud.deleteOne("admin", admin.getId());
    }

    //Actualiza un admin
    public int updateAdmin(Admin admin) {
        String[] columns = {"nombre"};
        String[] values = {addComillas(admin.getNombre())};
        return this.crud.updateOne("admin", columns, values, admin.getId());
    }

    public static void main(String[] args) {

        AdminController adminController = new AdminController();

        Admin admin = new Admin(0, "Valerie");
        adminController.addAdmin(admin);
    }

}
