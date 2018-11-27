
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LocalShip extends Navigator {

    @Override
    public void upDate() {}

    public LocalShip(int id) {
        super(new Circle(10, Color.GREY));
        super.setId(id);
        super.setVelocity(Point2D.ZERO);
        super.getView().setRotate(0);
    }

    public LocalShip(int id, Point2D velocity) {
        super(new Circle(10, Color.GREY));
        super.setId(id);
        super.setVelocity(velocity);
    }
    public double ratio = 0;
}









