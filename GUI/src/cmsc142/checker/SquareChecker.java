package cmsc142.checker;

import cmsc142.Point;

public class SquareChecker extends CheckerStrategy {
	public boolean candidateCheck(int candidate, Point p) {
		int size = this.size;
		int xPos = (int)(p.x/size) * size;
		int yPos = (int)(p.y/size) * size;
		for(int i = yPos; i < (yPos + size); i++){
			for(int j = xPos; j < (xPos + size) ;j++) {
				if(candidate == this.puzzle[i][j])
					return false;
			}
		}
		return true;
	}

	public boolean alignCheck(Point p1, Point p2) {
		return ((int)(p1.y/size)==(int)(p2.y/size) &&
			(int)(p1.x/size)==(int)(p2.x/size));
	}
}
