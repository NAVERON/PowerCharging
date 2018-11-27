package examples.tests;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CollisionTest extends GameApplication{

	public enum EntityType {
		PLAYER, COIN
	}
	private Entity player, enemy;

	@Override
	protected void initGame() {
		// TODO Auto-generated method stub
		super.initGame();

		player = Entities.builder()
				.at(100, 200)
				.type(EntityType.PLAYER)
				.viewFromNodeWithBBox(new Circle(20, Color.RED))
				.with(new CollidableComponent(true))
				.buildAndAttach(getGameWorld());
		enemy = Entities.builder()
				.type(EntityType.COIN)
				.at(200, 200)
				.viewFromTextureWithBBox("brick.png")
				.with(new CollidableComponent(true))
				.buildAndAttach(getGameWorld());
	}

	@Override
	protected void initPhysics() {
		// TODO Auto-generated method stub
		super.initPhysics();
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
			@Override
			protected void onCollision(Entity player, Entity coin) {
				// TODO Auto-generated method stub
				super.onCollision(player, coin);
				coin.removeFromWorld();
			}
		});
	}

	@Override
	protected void initInput() {
		// TODO Auto-generated method stub
		super.initInput();
		Input input = getInput();
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
	}

	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initUI() {
		super.initUI();
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}









