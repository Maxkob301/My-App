package com.example.myapp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;

import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;


public class DataBaseHandler extends Configs {

    File pathImagesDirectory = new File("C:\\Users\\Маksim\\My-app\\My-App\\src\\main\\java\\Images");

    FileChooser chooser = new FileChooser();


    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException,SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return  dbConnection;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO" + " " + Constants.USER_TABLE + " " + "(" +
                Constants.USER_FIRSTNAME + "," + Constants.USER_LASTNAME + ","
                + Constants.USER_LOGIN + "," + Constants.USER_PASSWORD + ","
                + Constants.USER_LOCATION + "," + Constants.USER_GENDER + ")" +
                "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement  statement = getDbConnection().prepareStatement(insert);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3,user.getLoginName());
            statement.setString(4, user.getPasswordName());
            statement.setString(5,user.getCountryName());
            statement.setString(6, user.getGender());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getUser(User user){
        ResultSet resultSet;
        String select = "SELECT * FROM " + Constants.USER_TABLE + " WHERE " + Constants.USER_LOGIN + "=? AND " + Constants.USER_PASSWORD + "=?";
        try {
            PreparedStatement  statement = getDbConnection().prepareStatement(select);
            statement.setString(1, user.getLoginName());
            statement.setString(2, user.getPasswordName());
            resultSet = statement.executeQuery();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  resultSet;
    }
    public String getImageFromTable(String nameImage) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String name = null;
        Statement statement = getDbConnection().createStatement();
        String select = "SELECT * FROM images WHERE ImageName " + "= " + "\'" + nameImage + "\'";
        resultSet = statement.executeQuery(select);
         while(resultSet.next()){
             name = resultSet.getString("ImageName");
         }
        return name;
    }
    //Добавляем файл и его название в базу данных images
    public void CreateImageName(String nameImage){
        //SQL запрос добавления имени и файла в таблицу
        String insert = "INSERT INTO images (ImageName)" + "VALUES(" + "\"" + nameImage + "\"" + ")";
        File filePath;
        try {
            filePath = chooser.showOpenDialog(null);
            if(filePath == null){
                System.out.println("");
            }else {
                //Создание соединения
                Statement statement = getDbConnection().createStatement();
                statement.executeUpdate(insert);
                FileUtils.copyFileToDirectory(filePath, pathImagesDirectory);
                File file1 = new File(pathImagesDirectory + "\\" + filePath.getName());
                File file2 = new File(pathImagesDirectory + "\\" + nameImage + ".jpg");
                FileUtils.moveFile(file1, file2);
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //DELETE FROM `uers`.`images` WHERE (`idimages` = '6');
    public void removeNameImage()  {
        Statement statement;
        File fileRemove;
        try {
            statement = getDbConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String removeName = null;
        fileRemove = chooser.showOpenDialog(null);
        if(fileRemove == null){
            System.out.println("");
        }else {
            String remove = "DELETE FROM images" + " WHERE " + "(" + "\"ImageName\"" + " = " + "\"" + fileRemove.getName() + "\"" + ")";
            File deleteFile = new File(pathImagesDirectory + "\\" + fileRemove.getName());
            if (deleteFile.delete()) {
                System.out.println("Удалено успешно");
            } else {
                System.out.println("Не удалено");
            }
            try {
                statement.executeUpdate(remove);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
