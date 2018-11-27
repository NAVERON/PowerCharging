package dodger;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.settings.GameSettings;
import static com.almasb.fxgl.app.DSLKt.*;

import javafx.application.Application;
import javafx.scene.input.KeyCode;



public class DodgerApp extends GameApplication{

	@Override
	protected void initSettings(GameSettings settings) {

	}


	@Override
	protected void initInput() {
		super.initInput();
		onKey(KeyCode.A, "Left", ()->{
			getGameWorld().getEntitiesByType(EntityType.BIRD).forEach((bird)->{
				BirdComponent comp = bird.getComponent(BirdComponent.class);
				comp.move(Direction.LEFT);
			});
		});
	}


	@Override
	protected void initGame() {
		super.initGame();
		getGameWorld().addEntityFactory(new DodgerFactory());

		getGameWorld().addEntity(Entities.makeScreenBounds(40));

		spawn("ball", 30, 30);

		for(int  i = 0; i < 4; i++){
			double x = FXGLMath.random(i*100, i*100+100);
			double y = FXGLMath.random(400, 600);
			spawn("bird", x, y);
		}
	}

	@Override
	protected void initPhysics() {
		// TODO Auto-generated method stub
		super.initPhysics();
		getPhysicsWorld().setGravity(0, 0);
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}









