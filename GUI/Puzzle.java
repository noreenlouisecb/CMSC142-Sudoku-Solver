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

	public boolean rowChecker(int candidate, Points p){
		for(int i = 0; i < this.size*this.size; i++){
			if(i == p.y) continue;
			if(candidate == this.puzzle[p.x][i])	return false;
		}
		return true;
	}

	public boolean colChecker(int candidate, Points p){
		for(int i = 0; i < this.size*this.size; i++){
			if(i == p.x) continue;
			if(candidate == this.puzzle[i][p.y]) return false;
		}
		return true;
	}	

	public boolean sqrChecker(int candidate, Points p){
		int size = this.size;
		int xPos = (int)(p.x/size) * size;
		int yPos = (int)(p.y/size) * size;
		for(int i = yPos; i < (size*yPos); i++){
			for(int j = xPos; j < (size*xPos) ;j++) {
				if(candidate == this.puzzle[p.x][i])
					return false;
			}
		}
		return true;
	}

	public boolean xChecker(int candidate, Points p) {
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

	public boolean yChecker(int candidate, Points p) {
		int size = this.size;
		if(size % 2 == 0) return false;

		if((p.x == p.y || (p.x+p.y) == (size*size) - 1) && p.x <= (size*size)/2) {
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
}