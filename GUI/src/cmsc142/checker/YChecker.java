package cmsc142.checker;

import cmsc142.Point;

public class YChecker extends CheckerStrategy {
    private boolean checkLeft(int candidate) {
		for(int i = 0, j = 0; i < (size*size)/2; i++, j++){
			if(candidate == this.puzzle[i][j])
				return false;
		}
		return true;
	}

	private boolean checkRight(int candidate) {
		for(int i = 0, j = size*size-1; i < (size*size)/2; i++, j--){
			if(candidate == this.puzzle[i][j])
				return false;
		}
		return true;
	}

	private boolean checkBottom(int candidate) {
		for(int i = (size*size)/2, j = (size*size)/2; i < (size*size); i++) {
			if(candidate == this.puzzle[i][j])
				return false;
		}
		return true;
	}

	public boolean candidateCheck(int candidate, Point p) {
		if(size % 2 == 0) return false;

		if (p.y < size*size/2) {
			if (p.x == p.y) {
				return checkRight(candidate) && checkBottom(candidate);
			} else if (p.x + p.y == size * size - 1) {
				return checkLeft(candidate) && checkBottom(candidate);
			}
		} else if (p.x == size*size/2) {
			return checkLeft(candidate) && checkRight(candidate) &&
					checkBottom(candidate);
		}

		return true;
	}

	public boolean alignCheck(Point p1, Point p2) {
		if(size % 2 == 0) return false;

		int totalSize = size*size;
		int center = totalSize/2;

		if (p1.y < center) {
			if (p1.x == p1.y) {
				return (p2.x == p2.y && p2.y < center) || (p2.y >= center && p2.x == center);
			} else if (p1.x + p1.y == totalSize - 1) {
				return (p2.x + p2.y == totalSize - 1 && p2.y < center) || (p2.y >= center && p2.x == center);
			}
		} else if (p1.x == size*size/2) {
			return (p2.x == p2.y && p2.y < center) || (p2.y >= center && p2.x == center) ||
				(p2.x + p2.y == totalSize - 1 && p2.y < center);
		}

		return false;
	}
}
