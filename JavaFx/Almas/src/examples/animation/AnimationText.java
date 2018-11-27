package examples.animation;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;

import javafx.application.Application;
import javafx.scene.input.KeyCode;


public class AnimationText extends GameApplication {

	private Entity player;

	@Override
	protected void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
		player = Entities.builder()
				.at(200, 300)
				.with(new DudeControl())
				.buildAndAttach(getGameWorld());
	}

	@Override
	protected void initPhysics() {
		// TODO Auto-generated method stub
		super.initPhysics();
	}

	@Override
	protected void initInput() {

		super.initInput();

		Input input = getInput();
		input.addAction(new UserAction("MoveRight") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onAction();
				player.getComponent(DudeControl.class).moveRight();
			}
		}, KeyCode.D);
		input.addAction(new UserAction("MoveLeft") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onAction();
				player.getComponent(DudeControl.class).moveLeft();
			}
		}, KeyCode.A);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setWidth(1500);
		settings.setHeight(800);
		settings.setVersion("0.2");
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}








