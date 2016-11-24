package cmsc142.solver;

import cmsc142.Point;
import cmsc142.checker.CheckerStrategy;
import cmsc142.checker.ColumnChecker;
import cmsc142.checker.RowChecker;
import cmsc142.checker.SquareChecker;

import java.util.LinkedList;

public class NormalSolver extends SolverStrategy {
	public NormalSolver() {
		checkers = new CheckerStrategy[3];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
	}

	protected boolean alignCheck(int offset1, int offset2) {
		int size = puzzle.getSize();
		return (zeroPositions[offset1].y==zeroPositions[offset2].y ||
			zeroPositions[offset1].x==zeroPositions[offset2].x || 
			((int)(zeroPositions[offset1].y/size)==(int)(zeroPositions[offset2].y/size) &&
			(int)(zeroPositions[offset1].x/size)==(int)(zeroPositions[offset2].x/size)));
		/*
		for(CheckerStrategy checker: checkers) {
			if (!checker.alignCheck(zeroPositions[offset1], zeroPositions[offset2])) {
				return false;
			}
		}
		return true;
		*/
	}

	public boolean candidateCheck(int candidate, Point p) {
		for(CheckerStrategy checker: checkers) {
			if (!checker.candidateCheck(candidate, p)) {
				return false;
			}
		}
		return true;
	}


}
