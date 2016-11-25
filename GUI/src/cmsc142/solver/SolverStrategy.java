package cmsc142.solver;

import java.util.LinkedList;
import cmsc142.Puzzle;
import cmsc142.Point;
import cmsc142.checker.CheckerStrategy;

public class SolverStrategy {
	private Puzzle puzzle;
	private int[] nopts;
	private int[][] option;
	private int[][] board;
	private Point[] zeroPositions;
	private CheckerStrategy[] checkers;

	private boolean alignCheck(int offset1, int offset2) {
		for(CheckerStrategy checker: checkers) {
			if (checker.alignCheck(zeroPositions[offset1], zeroPositions[offset2])) {
				return true;
			}
		}
		return false;
	}

	public void changeCheckers(CheckerStrategy[] checkers) {
		this.checkers = checkers;
	}

	public boolean candidateCheck(int candidate, Point p) {
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

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public LinkedList<int[][]> solve() {
		int zeroCount = puzzle.getZeroCount();
		nopts = new int[zeroCount + 2];
		option = new int[zeroCount + 2][zeroCount + 2];
		board = puzzle.getPuzzle();
		zeroPositions = puzzle.getZeroList();

		updateCheckers();

		int start, move;
		int candidate, i, j, k;
		start = move = 0;
		int size = puzzle.getSize();
		nopts[start] = 1;
		LinkedList<int[][]> solutions = new LinkedList<>();
		int[][] solutionHolder;
		int sideSize = size*size;

		while (nopts[start] > 0) {
			if(nopts[move] > 0) {
				move++;
				nopts[move] = 0;
				if(move == zeroCount+1) {
					solutionHolder = new int[sideSize][sideSize];
					solutions.add(solutionHolder);
					for(k = 1, i = 0; i < sideSize; i++){
						for(j = 0;j < sideSize; j++){
							if(board[i][j] == 0) {
								solutionHolder[i][j] = option[k][nopts[k]];
								k++;
							}
							else{
								solutionHolder[i][j] = board[i][j];
							}
						}
					}
				} else if(move == 1) {
					for(candidate = sideSize; candidate >=1; candidate --) {
						if(candidateCheck(candidate, zeroPositions[move-1])) {
							nopts[move]++;
							option[move][nopts[move]] = candidate;
						}
					}
				} else{
					for(candidate=sideSize; candidate >= 1; candidate--) {
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
		System.out.println("Solutions: " + solutions.size());
		return solutions;
	}

	public LinkedList<int[][]> solve(Puzzle puzzle) {
		this.puzzle = puzzle;
		return this.solve();
	}
}
