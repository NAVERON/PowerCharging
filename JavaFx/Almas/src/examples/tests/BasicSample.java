package examples.tests;

import java.util.Map;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.ActionType;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputMapping;
import com.almasb.fxgl.input.OnUserAction;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BasicSample extends GameApplication{

	private Entity player;
	private enum TYPE{
		PLAYER
	}

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setTitle("BasicSample");
		settings.setVersion("0.1");

		settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
		settings.setAppIcon("icon.jpg");
	}

	@Override
	protected void initGame() {
		super.initGame();

		player = Entities.builder()
				.type(TYPE.PLAYER)
				.at(100, 100)
				.viewFromNode(new Rectangle(25, 25, Color.BLUE))
				.buildAndAttach(getGameWorld());

        getGameState().<Vec2>addListener("vector", ((prev, now) -> System.out.println(prev + " " + now)));
        System.out.println(getGameState().<Vec2>getObject("vector").x);  //两种获取属性的方法
        System.out.println(getGameState().<Vec2>objectProperty("vector").get().y);
        getGameState().setValue("vector", new Vec2(300, 300));
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		super.initGameVars(vars);
        vars.put("vector", new Vec2(1, 1));
        vars.put("score", 0);
	}

	@Override
	protected void onUpdate(double tpf) {
		// TODO Auto-generated method stub
		super.onUpdate(tpf);
		if(getInput().isHeld(KeyCode.U)){
			System.out.println("Tick : " + getTick());
		}
	}

	@Override
	protected void initInput() {
		super.initInput();

		Input moveInput = getInput();
		moveInput.addAction(new UserAction("Move Right") {
			@Override
			protected void onActionEnd() {
				// TODO Auto-generated method stub
				super.onActionEnd();
				player.translateX(100);
			}
		}, MouseButton.PRIMARY);
		moveInput.addAction(new UserAction("Move Left") {
			@Override
			protected void onActionBegin() {
				// TODO Auto-generated method stub
				super.onActionBegin();
				player.translateX(-100);
			}
		}, MouseButton.SECONDARY);

		Input other = getInput();
		other.addInputMapping(new InputMapping("Print Line", KeyCode.E));

	}

	@OnUserAction(name = "Print Line", type = ActionType.ON_ACTION_BEGIN)
	public void printLineBegin(){
		System.out.println("Action Begin");
		getGameState().increment("score", 5);
	}

    @OnUserAction(name = "Print Line", type = ActionType.ON_ACTION_END)
    public void printLineEnd() {
        System.out.println("Action End");
    }

	@Override
	protected void initUI() {
		super.initUI();

        Text uiScore = getUIFactory().newText("", Color.BLACK, 16.0);
        uiScore.setTranslateX(100);
        uiScore.setTranslateY(100);
        uiScore.textProperty().bind(getGameState().intProperty("score").asString());

        getGameScene().addUINode(uiScore);
	}

	public static void main(String[] args){
		Application.launch(args);
	}

}






