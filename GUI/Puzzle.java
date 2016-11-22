


public class Puzzle{
	public int[][] puzzle;
	public int size;

	public Puzzle(int[][] puzzle, int size){
		this.puzzle = puzzle;
		this.size = size;
	}

	public int[][] getPuzzle(){
		return this.puzzle;
	}

	public int getSize(){
		return this.size;
	}

	public void setInput(int x, int i, int j){
		this.puzzle[i][j] = x;
	}

}