package cmsc142.solver;

import java.util.LinkedList;
import cmsc142.Puzzle;
import cmsc142.Point;
import cmsc142.checker.CheckerStrategy;

public abstract class SolverStrategy {
	protected Puzzle puzzle;
	protected int[] nopts;
	protected int[][] option;
	protected int[][] board;
	protected Point[] zeroPositions;
	protected CheckerStrategy[] checkers;


	protected abstract boolean alignCheck(int offset1, int offset2);
	public abstract boolean candidateCheck(int candidate, Point p);

	private void updateCheckers() {
		for(CheckerStrategy checker: checkers) {
			checker.setPuzzle(board);
		}
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public LinkedList<Puzzle> solve() {
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

	public LinkedList<Puzzle> solve(Puzzle puzzle) {
		this.puzzle = puzzle;
		return this.solve();
	}
}
