package com.example.gestionpedidofxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage myStage;

    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/ventana-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene (String fxml, String title){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/" + fxml));
            Scene scene = new Scene(fxmlLoader.load(), 850, 600);
            myStage.setTitle(title);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void crearSesion (String stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(stage));
            Scene scene = new Scene(fxmlLoader.load(), 500, 600);
            myStage.setTitle("Crear Usuario");
            myStage.setScene(scene);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}