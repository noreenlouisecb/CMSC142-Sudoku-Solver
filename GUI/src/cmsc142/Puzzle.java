package cmsc142;

import java.util.ArrayList;

public class Puzzle{
	public int[][] puzzle;
	public int size;
	private ArrayList<Point> zeroList;

	public Puzzle(int[][] puzzle, int size){
		this.puzzle = puzzle;
		this.size = size;
		countZero();
	}

	private void countZero() {
		zeroList = new ArrayList<>();
		int totalSize = size*size;
		for(int ii = 0; ii < totalSize; ii++) {
			for(int jj = 0; jj < totalSize; jj++) {
				if (puzzle[ii][jj] == 0) {
					zeroList.add(new Point(jj, ii));
				}
			}
		}
	}

	public int getZeroCount() {
		return zeroList.size();
	}

	public int[][] getPuzzle(){
		return this.puzzle;
	}

	public int getSize(){
		return this.size;
	}

	public Point[] getZeroList() {
		return zeroList.toArray(new Point[1]);
	}

	public void setInput(int x, int ii, int jj){
		if (x == 0 && puzzle[ii][jj] != 0) {
			zeroList.add(new Point(jj, ii));
		} else if (x != 0 && puzzle[ii][jj] == 0) {
			for(int kk = 0; kk < zeroList.size(); kk++) {
				if (zeroList.get(kk).x == jj && zeroList.get(kk).y == ii) {
					zeroList.remove(kk);
					break;
				}
			}
		}
		this.puzzle[ii][jj] = x;
	}

	/*
	public boolean yChkDuplicate(int candidate, int option, Points move, Points checksol){
		if(move.x == move.y) {
			if(checksol.x == checksol.y){
				if(candidate == option) return false;}
		}
		if(move.x + move.y == (this.size*this.size) - 1){
			if(checksol.x + checksol.y == (this.size*this.size) - 1){
				if(candidate == option) return false;}
		}
		if(move.y == checksol.y){
			if(move.x > (this.size*this.size)/2){
			if(candidate == option) return false;}
		}
		return true;
	}
	*/
}
