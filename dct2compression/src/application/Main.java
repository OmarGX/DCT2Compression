package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	Button UploadButton;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("DCT2 COMPRESSION");
			
			AnchorPane layout = new AnchorPane();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("AppLayout.fxml"));
			layout = (AnchorPane) loader.load();
			
			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
