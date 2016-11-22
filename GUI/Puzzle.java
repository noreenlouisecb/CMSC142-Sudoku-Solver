


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

	public boolean rowChecker(int candidate, int x, int y){
		for(int i = 0; i < this.size*this.size; i++){
			if(i == y) continue;
			if(candidate == this.puzzle[x][i])	return false;
		}
		return true;
	}

	public boolean colChecker(int candidate, int x, int y){
		for(int i = 0; i < this.size*this.size; i++){
			if(i == x) continue;
			if(candidate == this.puzzle[i][y]) return false;
		}
		return true;
	}	

	public boolean sqrChecker(int candidate, int x, int y){
		int size = this.size;
		int xPos = (int)(x/size) * size;
		int yPos = (int)(y/size) * size;
		for(int i = yPos; i < (size*yPos); i++){
			for(int j = xPos; j < (size*xPos) ;j++) {
				if(candidate == this.puzzle[x][i])
					return false;
			}
		}
		return true;
	}

	public boolean xChecker(int candidate, int x, int y) {
		int size = this.size;
		if((x == y) || ((x+y) == (size*size) - 1)) {
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

	public boolean yChecker(int candidate, int x, int y) {
		int size = this.size;
		if(size % 2 == 0) return false;

		if((x == y || (x+y) == (size*size) - 1) && x <= (size*size)/2) {
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

	public boolean yChkDuplicate(int candidate, int option, int move_x, int move_y, int checksol_x, int checksol_y){
		if(move_x == move_y) {
			if(checksol_x == checksol_y){
				if(candidate == option) return false;}
		}
		if(move_x + move_y == (this.size*this.size) - 1){
			if(checksol_x + checksol_y == (this.size*this.size) - 1){
				if(candidate == option) return false;}
		}
		if(move_y == checksol_y){
			if(move_x > (this.size*this.size)/2){
			if(candidate == option) return false;}
		}
		return true;
	}
}