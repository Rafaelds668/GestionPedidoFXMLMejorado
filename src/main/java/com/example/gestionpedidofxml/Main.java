package com.example.gestionpedidofxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage myStage;

    /**
     * Inicia la aplicación JavaFX, cargando la interfaz gráfica de usuario (GUI) desde el archivo FXML de la pantalla de inicio
     * y mostrándola en una nueva ventana (Stage).
     *
     * @param stage La ventana (Stage) en la que se mostrará la interfaz gráfica de usuario.
     * @throws IOException Se lanza una excepción de entrada/salida si hay algún problema al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Carga y muestra una nueva interfaz gráfica de usuario (GUI) desde un archivo FXML en la ventana actual.
     *
     * @param fxml   El nombre del archivo FXML que contiene la definición de la interfaz gráfica.
     * @param titulo El título que se asignará a la ventana al cargar la nueva interfaz gráfica.
     * @throws RuntimeException Se lanza una excepción si hay un error al cargar el archivo FXML.
     */
    public static void loadFXML (String fxml, String titulo){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/"+fxml));
            Scene scene = new Scene(fxmlLoader.load());
            myStage.setTitle(titulo);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo "+fxml);
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}