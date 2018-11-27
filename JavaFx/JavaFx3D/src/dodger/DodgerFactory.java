package dodger;


import static com.almasb.fxgl.app.DSLKt.*;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.collision.ContactID.Type;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import javafx.util.Duration;


public class DodgerFactory implements EntityFactory{

	@Spawns("ball")
	public Entity newBall(SpawnData data){
		PhysicsComponent physics = new PhysicsComponent();
		physics.setBodyType(BodyType.DYNAMIC);
		physics.setFixtureDef(new FixtureDef().restitution(1.0f));
		physics.setOnPhysicsInitialized( () -> {
			physics.setLinearVelocity(150, 150);
		});

		return Entities.builder()
				.type(EntityType.BALL)
				.from(data)
				.viewFromNodeWithBBox(texture("ball.png", 40, 40))
				.with(physics)
				.with(new RandomVelocityComponent())
				.build();
	}

	@Spawns("bird")
	public Entity newPlayer(SpawnData data){
		return Entities.builder()
				.type(EntityType.BIRD)
				.from(data)
				.viewFromAnimatedTexture("bird.png", 2, Duration.seconds(0.5))
				.build();
	}

}
