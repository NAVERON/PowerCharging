package examples.tests;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

public class EnCircle extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("circle turned");
		Scene scene = new Scene(CreateContent());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Parent CreateContent() {
		Pane root = new Pane();
		Circle circle = CircleBuilder.create()
				.radius(10)
				.centerX(100)
				.centerY(50)
				.build();
		root.getChildren().add(circle);
		return root;
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}
