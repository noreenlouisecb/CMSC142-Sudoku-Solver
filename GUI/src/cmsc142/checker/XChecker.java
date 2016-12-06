package cmsc142.checker;

import cmsc142.Puzzle;
import cmsc142.Point;

public class XChecker extends CheckerStrategy {
	public boolean candidateCheck(int candidate, Point p) {
		int size = this.size;
		if((p.x == p.y) || ((p.x+p.y) == (size*size) - 1)) {
			for(int i = 0, j = 0; i <(size*size); i++, j++){
				if(candidate == this.puzzle[i][j])
					return false;
			}
			for(int i = (size*size)-1, j = 0; i >= 0; i--, j++){
				if(candidate == this.puzzle[i][j])
					return false;
			}
		}
		return true;
	}

	public boolean alignCheck(Point p1, Point p2) {
		return (p1.x == p1.y && p2.x == p2.y) || 
			(p1.x + p1.y == size - 1) && (p2.x + p2.y == size - 1);
	}
}
