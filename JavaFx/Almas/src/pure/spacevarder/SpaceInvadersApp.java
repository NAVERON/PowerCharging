package pure.spacevarder;


import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SpaceInvadersApp extends Application{

	private Pane root = new Pane();
	private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);

	double t = 0;

	private Parent createcontent(){
		root.setPrefSize(600,  800);
		root.getChildren().add(player);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
			}
		};

		timer.start();
		nextlevel();

		return root;
	}

	private void nextlevel(){
		for(int i = 0; i < 5; i++){
			Sprite s = new Sprite(90 + i*100, 150, 40, 40, "enemy", Color.RED);

			root.getChildren().add(s);
		}
	}

	private List<Sprite> sprites() {
		return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
	}

	public void update() {
		t += 0.01;

		sprites().forEach(s -> {
			switch (s.type) {
				case "enemybullet":
					s.moveDown();
					if(player.dead){  //解决  在玩家死后仍然出现碰撞检测
						return;
					}
					if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
						player.dead = true;
						s.dead = true;
					}
					break;
				case "playerbullet":
					s.moveUp();
					sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
						if(s.getBoundsInParent().intersects(enemy.getBoundsInParent())){
							enemy.dead = true;
							s.dead = true;
						}
					});
					break;
				case "enemy":
					if(t > 2){  //控制子弹的频率
						if(Math.random() < 0.5){
							shoot(s);
						}
					}
					break;
				default:
					break;
			}
		});

		root.getChildren().removeIf( n -> {
			Sprite s = (Sprite)n;
			return s.dead;
		});
		//上面的界面更新只是更新界面对象指针，但是实际的对象仍然存在

		if(t > 2){
			t = 0;
		}
	}

	public void shoot(Sprite who){
		Sprite s = new Sprite( (int)(who.getTranslateX() + player.getWidth()/2), (int)who.getTranslateY(), 5, 20, who.type + "bullet", Color.BLACK);
		root.getChildren().add(s);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createcontent());

		scene.setOnKeyPressed(e -> {
			if(player.dead){  //同样解决   在玩家死后，仍然可以移动和发射子弹
				return;
			}
			switch (e.getCode()) {
				case A:
					player.moveLeft();
					break;
				case D:
					player.moveRight();
					break;
				case W:
					player.moveUp();
					break;
				case S:
					player.moveDown();
					break;
				case SPACE:
					shoot(player);
					break;
				default:
					break;
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args){
		Application.launch(args);
	}

	private static class Sprite extends Rectangle {
		boolean dead = false;
		String type = null;

		public Sprite(int x, int y, int w, int h, String type, Color color) {
			super(w, h, color);

			this.type = type;
			setTranslateX(x);
			setTranslateY(y);
		}

		private void moveLeft() {
			setTranslateX(getTranslateX() - 5);
		}

		private void moveRight() {
			setTranslateX(getTranslateX() + 5);
		}

		private void moveUp() {
			setTranslateY(getTranslateY() - 5);
		}

		private void moveDown() {
			setTranslateY(getTranslateY() + 5);
		}
	}
}

























