package dodger;

import javafx.geometry.Point2D;

public enum Direction {

	UP(new Point2D(0, -1)),
	DOWN(new Point2D(0, 1)),
	LEFT(new Point2D(-1, 0)),
	RIGHT(new Point2D(1, 0));

	final Point2D vector;

	public Direction next(){
		return Direction.values()[ordinal() + 1];
	}

	private Direction(Point2D vector) {
		this.vector = vector;
	}
}
