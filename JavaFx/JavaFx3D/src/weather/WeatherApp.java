package weather;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeatherApp extends Application{

	@Override
	public void start(Stage stage) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UI.fxml"));
		loader.setControllerFactory(t -> new MainController(new WeatherProvider()));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args){
		Application.launch(args);
	}


}
