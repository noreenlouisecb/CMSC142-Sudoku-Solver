package cmsc142.solver;

import cmsc142.Point;
import cmsc142.Puzzle;
import cmsc142.checker.CheckerStrategy;
import cmsc142.checker.ColumnChecker;
import cmsc142.checker.RowChecker;
import cmsc142.checker.SquareChecker;

import java.util.LinkedList;

public class NormalSolver implements SolverStrategy {
	protected Puzzle puzzle;
	protected int[] nopts;
	protected int[][] option;
	protected int[][] board;
	protected Point[] zeroPositions;
	protected CheckerStrategy[] checkers;

	public NormalSolver() {
		checkers = new CheckerStrategy[3];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
	}

	private boolean alignCheck(int offset1, int offset2) {
		int size = puzzle.getSize();
		return (zeroPositions[offset1].y==zeroPositions[offset2].y ||
			zeroPositions[offset1].x==zeroPositions[offset2].x || 
			((int)(zeroPositions[offset1].y/size)==(int)(zeroPositions[offset2].y/size) &&
			(int)(zeroPositions[offset1].x/size)==(int)(zeroPositions[offset2].x/size)));
	}

	private boolean candidateCheck(int candidate, Point p) {
		for(CheckerStrategy checker: checkers) {
			if (!checker.candidateCheck(candidate, p)) {
				return false;
			}
		}
		return true;
	}

	private void updateCheckers() {
		for(CheckerStrategy checker: checkers) {
			checker.setPuzzle(board);
		}
	}

	public LinkedList<Puzzle> solve(Puzzle puzzle) {
		this.puzzle = puzzle;
		int zeroCount = puzzle.getZeroCount();
		nopts = new int[zeroCount + 2];
		option = new int[zeroCount + 2][zeroCount + 2];
		board = puzzle.getPuzzle();
		zeroPositions = puzzle.getZeroList();

		updateCheckers();

		int start, move;
		int row, col, sqr, candidate, i, j, k;
		int solutionCount = 0;
		start = move = 0;
		int size = puzzle.getSize();
		nopts[start] = 1;
		System.out.format("%d\n", size);
		while (nopts[start] > 0) {
			if(nopts[move] > 0) {
				move++;
				nopts[move] = 0;
				if(move == zeroCount+1) {
					solutionCount++;
					for(k = 1, i = 0; i < size*size; i++){
						for(j = 0;j < size*size; j++){
							if(board[i][j] == 0) {
								System.out.format("%d ", option[k][nopts[k]]);
								k++;
							}
							else{
								System.out.format("%d ", board[i][j]);
							}
						}
						System.out.format("\n");
					}
					System.out.format("\n");
				} else if(move == 1) {
					for(candidate = size*size; candidate >=1; candidate --) {
						if(candidateCheck(candidate, zeroPositions[move-1])) {
							nopts[move]++;
							option[move][nopts[move]] = candidate;
						}
					}
				} else{
					for(candidate=size*size; candidate >= 1; candidate--) {
						if(candidateCheck(candidate, zeroPositions[move-1])) {
							for(i = move-1; i >= 1;i--)
							if(candidate==option[i][nopts[i]] && alignCheck(i-1, move-1)) break;
							if(!(i>=1)) option[move][++(nopts[move])] = candidate;
						}
					}
				}
			} else {
				move--;
				nopts[move]--;
			}
		}
		System.out.println("Solutions: " + solutionCount);
		return null;
	}
}
