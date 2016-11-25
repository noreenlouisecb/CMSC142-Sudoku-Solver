package cmsc142.solver;

import cmsc142.Point;
import cmsc142.Puzzle;
import cmsc142.checker.CheckerStrategy;

import java.util.LinkedList;

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

	private void printPuzzle(int[][] board, int sideSize) {
        int i, j, k;
        for(k = 1, i = 0; i < sideSize; i++){
        for(j = 0;j < sideSize; j++){
            if(board[i][j] == 0) {
                System.out.format("%3d", option[k][nopts[k]]);
                k++;
            }
            else{
                System.out.format("%3d", board[i][j]);
            }
        }
        System.out.print('\n');
    }
	}
	public LinkedList<int[][]> solve() {
		int size = puzzle.getSize();
		int sideSize = size*size;
		int zeroCount = puzzle.getZeroCount();

		nopts = new int[zeroCount + 2];
		option = new int[zeroCount + 2][sideSize + 2];
		board = puzzle.getPuzzle();
		zeroPositions = puzzle.getZeroList();

		updateCheckers();

		int start, move;
		int candidate, i, j, k;
		start = move = 0;
		nopts[start] = 1;
		LinkedList<int[][]> solutions = new LinkedList<>();
		int[][] solutionHolder;
        System.out.print("\033[H\033[2J");
		System.out.flush();

		while (nopts[start] > 0) {
            /*
			System.out.print("\033[H\033[2J");
			System.out.flush();
            printPuzzle(board, sideSize);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 */
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
