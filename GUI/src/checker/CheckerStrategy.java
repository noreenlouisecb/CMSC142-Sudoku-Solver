package cmsc142.checker;

import cmsc142.Point;

public abstract class CheckerStrategy {
	protected int[][] puzzle = null;
	protected int size;

	public void setPuzzle(int[][] puzzle) {
		this.puzzle = puzzle;
		this.size = (int)Math.sqrt(puzzle.length);
	}

	public abstract boolean candidateCheck(int candidate, Point p);
	public abstract boolean alignCheck(Point p1, Point p2);
}
