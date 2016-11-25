package cmsc142.checker;

import cmsc142.Point;

public class RowChecker extends CheckerStrategy {
	public boolean candidateCheck(int candidate, Point p) {
		for(int i = 0; i < this.size*this.size; i++){
			if(candidate == this.puzzle[i][p.x])	return false;
		}
		return true;
	}

	public boolean alignCheck(Point p1, Point p2) {
		return p1.y==p2.y;
	}
}
