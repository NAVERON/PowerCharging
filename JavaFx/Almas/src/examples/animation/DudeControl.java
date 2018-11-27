package examples.animation;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.*;

import javafx.util.Duration;



class DudeControl extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public DudeControl() {
        animIdle = new AnimationChannel("brick.png", 4, 32, 42, Duration.seconds(10), 1, 1);  //这里修改了图片路径
        animWalk = new AnimationChannel("brick.png", 4, 32, 42, Duration.seconds(10), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speed = 500;

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;

        getEntity().setScaleX(-1);
    }
}









