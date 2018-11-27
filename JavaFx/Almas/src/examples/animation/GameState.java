package examples.animation;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.input.ActionType;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputMapping;
import com.almasb.fxgl.input.OnUserAction;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class GameState extends GameApplication{

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(1000);
		settings.setHeight(500);
	}


	@Override
	protected void initInput() {
		super.initInput();
		Input input = getInput();
		input.addAction(new UserAction("Hello") {
			@Override
			protected void onActionBegin() {
				super.onActionBegin();
				System.out.println("keyboard action!!");
			}
		}, KeyCode.F11);

		Input map = getInput();
		map.addInputMapping(new InputMapping("OK", MouseButton.PRIMARY));
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		super.initGameVars(vars);

		vars.put("score", 0);
	}


	@OnUserAction (name = "OK", type = ActionType.ON_ACTION_BEGIN)
	public void okMousebegin(){
		System.out.println("mouse pressed!!!");
		System.out.println("change test, ok, go back!!!!!!");
	}


	public static void main(String[] args){
		Application.launch(args);
	}

}
