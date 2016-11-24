package cmsc142.checker;

import cmsc142.Puzzle;
import cmsc142.Point;

public class YChecker extends CheckerStrategy {
	public boolean candidateCheck(int candidate, Point p) {
		if(size % 2 == 0) return false;

		if((p.x == p.y || (p.x+p.y) == (size*size) - 1) && p.x <= (size*size)/2) {
			for(int i = 0, j = 0; i < (size*size)/2; i++, j++){
				if(candidate == this.puzzle[i][j])
					return false;
			}
			for(int i = (size*size)/2, j = (size*size)/2; i < (size*size); i++) {
				if(candidate == this.puzzle[i][j])
					return false;
			}
			for(int i = ((size*size)/2)-1, j = ((size*size)/2)+1; i >= 0; i--, j++){
				if(candidate == this.puzzle[i][j])
					return false;
			}
			for(int i = (size*size)/2, j = (size*size)/2; i < (size*size); i++) {
				if(candidate == this.puzzle[i][j])
					return false;
			}
		}
		return true;
	}

	public boolean alignCheck(Point p1, Point p2) {
		return true;
	}
}
