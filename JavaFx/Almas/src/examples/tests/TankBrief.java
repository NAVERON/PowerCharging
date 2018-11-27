package examples.tests;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;

import javafx.application.Application;
import javafx.scene.input.KeyCode;

public class TankBrief extends GameApplication {

	private Entity player;

	@Override
	protected void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
		player = Entities.builder()
				.viewFromTexture("brick.png")
				.buildAndAttach(getGameWorld());
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		// TODO Auto-generated method stub
		super.initGameVars(vars);
	}

	@Override
	protected void initInput() {
		// TODO Auto-generated method stub
		super.initInput();

		Input input  = getInput();
		input.addAction(new UserAction("MoveRight") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onActionBegin();
				player.translateX(-10);
			}
		}, KeyCode.A);
		input.addAction(new UserAction("MoveLeft") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onActionBegin();
				player.translateX(10);
			}
		}, KeyCode.D);
		input.addAction(new UserAction("MoveUp") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onActionBegin();
				player.translateY(-10);
			}
		}, KeyCode.W);
		input.addAction(new UserAction("MoveDown") {
			@Override
			protected void onAction() {
				// TODO Auto-generated method stub
				super.onActionBegin();
				player.translateY(10);
			}
		}, KeyCode.S);
		input.addAction(new UserAction("PlaySound") {
			@Override
			protected void onActionBegin() {
				// TODO Auto-generated method stub
				super.onAction();
				getAudioPlayer().playSound("drop.wav");
			}
		}, KeyCode.Q);
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setWidth(1000);
		settings.setWidth(800);
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		Texture brickTexture = getAssetLoader().loadTexture("icon.jpg");
		brickTexture.setTranslateX(200);
		brickTexture.setTranslateY(100);
		getGameScene().addUINode(brickTexture);
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}





