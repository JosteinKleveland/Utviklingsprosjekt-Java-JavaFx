package minesweeper.fxui;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MinesweeperApp extends Application {
	
	@Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Minesweeper.fxml"));
        stage.setTitle("Minesweeper");
        stage.setScene(new Scene(parent));   
        stage.show();
    }
	
    public static void main(String[] args) {
        launch(MinesweeperApp.class,args); 
    }
}
