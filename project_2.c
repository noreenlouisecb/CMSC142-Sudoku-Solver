#include <stdio.h>
#include <stdlib.h>

typedef struct node {
	int x;
	int y;
} zeroList;

void setup(int ***, int ***, FILE *, int *, int *, zeroList **, int **, int ***);
void printPuzzle(int **, int);
void solveSudoku(int **, int, int **, int ***, int, zeroList **, int *, FILE *);
int rowChecker(int, zeroList, int **, int);
int colChecker(int, zeroList, int **, int);
int sqrChecker(int, zeroList, int **, int);
int xChecker(int, zeroList, int **, int);
int yChecker(int, zeroList, int **, int);
int yChkDuplicate(int, int, zeroList, zeroList, int);
int xCheckerInit(int **, int);
int yCheckerInit(int **, int);


int type;
int main() {
	int **puzzle, **solution;
	int size;
	int puzzleCount, zeroCount, solutionCount;
	int i, j;
	// Backtracking
	int *nopts;
	int **option;
	zeroList *posZero;


	FILE *fp = fopen("input2.txt", "r+");
	FILE *fp2 = fopen("output.txt", "w+");
	fscanf(fp, "%d", &puzzleCount);

	printf("type: \n[1] Regular\n[2] X\n[3] Y\n[4]XY\n");
	scanf("%d", &type);

	for(i = 0; i < puzzleCount; i++) {
		zeroCount = 0, solutionCount = 0;
		setup(&puzzle, &solution, fp, &size, &zeroCount, &posZero, &nopts, &option);
		if(xCheckerInit(puzzle, size) == 1) printf("init: X correct");
		if(yCheckerInit(puzzle, size) == 1) printf("init: Y correct");
		solveSudoku(puzzle, size, &nopts, &option, zeroCount, &posZero, &solutionCount, fp2);
		printf("Number of Solutions: %2d\n", solutionCount);
		fprintf(fp2, "Number of Solutions: %2d\n", solutionCount);
		free(posZero);
	}

	fclose(fp2);
	fclose(fp);
	return 0;
}

void setup(int ***puzzle, int ***solution, FILE *fp, int *size2, int *zeroCount, zeroList **posZero, int **nopts, int ***option){
	int i, j, k, size, pos = 0;
	fscanf(fp, "%d", size2);
	size = *size2;
	*puzzle = (int **)malloc(sizeof(int*) *size*size);
	*solution = (int **)malloc(sizeof(int*) *size*size);

	for(i = 0; i < size*size; i++){
		*(*puzzle + i) = (int *)malloc(sizeof(int)*size*size);
		*(*solution + i) = (int *)malloc(sizeof(int)*size*size);
		for(j = 0; j < size*size; j++){
			fscanf(fp, "%d", &(*puzzle)[i][j]);
			if((*puzzle)[i][j] == 0) {
				(*zeroCount)++;
			}
		}
	}

	*posZero = (zeroList *)malloc(sizeof(zeroList)*(*zeroCount));
	for(k=0, i = 0; i < size*size; i++){
		for(j = 0; j < size*size; j++){
			if((*puzzle)[i][j] == 0) {
				(*posZero)[k].x = i; (*posZero)[k].y = j; k++;
			}
		}
	}

	*nopts = (int *) malloc (sizeof(int) * (*zeroCount+2));
	*option = (int **)malloc(sizeof(int*)*(*zeroCount+2));
	for(j = 0; j < (*zeroCount+2); j++) {
		(*option)[j] = (int *)malloc(sizeof(int)*(*zeroCount+2));
	}
}

int rowChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	int i, x = posZero.x;
	for(i = 0; i<size*size; i++){
		if(i == posZero.y) continue;
		if(candidate == puzzle[x][i])	return 0;
	}
	return 1;
}

int colChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	int i, y = posZero.y;
	for(i = 0; i<size*size; i++){
		if(i == posZero.x) continue;
		if(candidate == puzzle[i][y]) return 0;
	}
	return 1;
}

int sqrChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	int i, j, x = posZero.x, y = posZero.y;
	int xPos = (int)(x/size) * size;
	int yPos = (int)(y/size) * size;
	for(i = yPos; i<(size*yPos); i++){
		for(j = xPos; j<(size*xPos) ;j++) {
			if(candidate == puzzle[x][i])
				return 0;
		}
	}
	return 1;
}

int xCheckerInit(int **puzzle, int size){
	int i, j, k, counter = 0;;
	for(k = 1; k <= size*size; k++){
		counter = 0;
		for(j = 0, i = 0; j < size*size;i++, j++){
			if(puzzle[i][j] == k) counter++;
			if(counter > 1) return 0;
		}
		counter = 0;
		for(j = size*size-1, i = 0; i < size*size;i++, j--){
			if(puzzle[i][j] == k) counter++;
			if(counter > 1) return 0;
		}
	}
	return 1;
}

int xChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	int i, j, x = posZero.x, y = posZero.y;
	if((x == y) || ((x+y) == size - 1)) {
		for(i = 0, j = 0; i <size; i++, j++){
			if(candidate == puzzle[i][j])
				return 0;
		}
		for(i = size-1, j = 0; i >=0; i--, j++){
			if(candidate == puzzle[i][j])
				return 0;
		}
	}
	return 1;
}


int yCheckerInit(int **puzzle, int size){
	int i, j, k, counter = 0;;
	for(k = 1; k <= size*size; k++){
		counter = 0;
		j = i = 0;
		while(i < size*size){
			if(puzzle[i][j] == k) counter++;
			if(counter > 1) return 0;
			
			if(j != ((size*size)-1)/2){
				j++;
			} 
			i++;	
		}
		
		counter = 0;
		i = 0;
		j = size*size-1;
		while(i < size*size){
			if(puzzle[i][j] == k) counter++;
			if(counter > 1) return 0;
			
			if(j != ((size*size)-1)/2){
				j--;
			} 
			i++;	
		}
	}
	return 1;
}

int yChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	int i, j, x = posZero.x, y = posZero.y;
	if(size%2 == 0) return 0;

	if((x == y || (x+y) == size - 1) && x <= size/2) {
		for(i = 0, j = 0; i < size/2; i++, j++){
			if(candidate == puzzle[i][j])
				return 0;
		}
		for(i = size/2, j = size/2; i < size; i++) {
			if(candidate == puzzle[i][j])
				return 0;
		}
		for(i = (size/2)-1, j = (size/2)+1; i >=0; i--, j++){
			if(candidate == puzzle[i][j])
				return 0;
		}
		for(i = size/2, j = size/2; i < size; i++) {
			if(candidate == puzzle[i][j])
				return 0;
		}
	}
	return 1;
}

int yChkDuplicate(int candidate, int option, zeroList move, zeroList checksol, int size){
	if(move.x == move.y) {
		if(checksol.x == checksol.y){
			if(candidate == option) return 0;}
	}
	if(move.x + move.y == size - 1){
		if(checksol.x + checksol.y == size - 1){
			if(candidate == option) return 0;}
	}
	if(move.x == checksol.x){
		if(move.x > size/2){
		if(candidate == option) return 0;}
	}
	return 1;
}

void solveSudoku(int **puzzle, int size, int **nopts, int ***option, int zeroCount, zeroList **posZero, int *solutionCount, FILE *fp){
	int start, move;
	int row, col, sqr, xchk, ychk, xychk, candidate, i, j, k;
	start = move = 0;
	(*nopts)[start] = 1;
	fprintf(fp, "%d\n", size);
	while ((*nopts)[start] > 0) {
		if((*nopts)[move] > 0) {
			move++;
			(*nopts)[move] = 0;
			if(move == zeroCount+1) {
				(*solutionCount)++;
				for(k = 1, i = 0; i < size*size; i++){
					for(j = 0;j < size*size; j++){
						if(puzzle[i][j] == 0) {
							//printf("%2d", (*option)[k][(*nopts)[k]]);
							fprintf(fp, "%d ", (*option)[k][(*nopts)[k]]);
							k++;
						}
						else{
							//printf("%2d", puzzle[i][j]);
							fprintf(fp, "%d ", puzzle[i][j]);
						}
					}
					//printf("\n");
					fprintf(fp, "\n");
				}
				//printf("\n");
				fprintf(fp, "\n");
			} else if(move == 1) {
				for(candidate = size*size; candidate >=1; candidate --) {
					row = rowChecker(candidate, (*posZero)[move-1], puzzle, size);
					col = colChecker(candidate, (*posZero)[move-1], puzzle, size);
					sqr = sqrChecker(candidate, (*posZero)[move-1], puzzle, size);
					if(row && col && sqr) {
						(*nopts)[move]++;
						(*option)[move][(*nopts)[move]] = candidate;
					}
				}
			} else{
				for(candidate=size*size; candidate >= 1; candidate--) {
					row = rowChecker(candidate, (*posZero)[move-1], puzzle, size);
					col = colChecker(candidate, (*posZero)[move-1], puzzle, size);
					sqr = sqrChecker(candidate, (*posZero)[move-1], puzzle, size);
					xchk = xChecker(candidate,  (*posZero)[move-1], puzzle, size);
					ychk = yChecker(candidate,  (*posZero)[move-1], puzzle, size);

					if(row && col && sqr && type == 1) {
						for(i = move-1; i >= 1; i--)
						if(candidate==(*option)[i][(*nopts)[i]] && ((*posZero)[i-1].y==(*posZero)[move-1].y ||
							(*posZero)[i-1].x==(*posZero)[move-1].x || ((int)((*posZero)[i-1].y/size)==(int)((*posZero)[move-1].y/size) &&
								(int)((*posZero)[i-1].x/size)==(int)((*posZero)[move-1].x/size)))) break;
						if(!(i>=1)) (*option)[move][++((*nopts)[move])] = candidate;
					}

					if(row && col && sqr && xchk && type == 2) {
						for(i = move-1; i >= 1; i--){
							if(candidate==(*option)[i][(*nopts)[i]] && ((*posZero)[i-1].y==(*posZero)[move-1].y ||
								(*posZero)[i-1].x==(*posZero)[move-1].x || ((int)((*posZero)[i-1].y/size)==(int)((*posZero)[move-1].y/size) &&
									(int)((*posZero)[i-1].x/size)==(int)((*posZero)[move-1].x/size)))) break;
							if(((((*posZero)[move-1].x == (*posZero)[move-1].y && (*posZero)[i-1].x == (*posZero)[i-1].y) ||
								((*posZero)[move-1].x + (*posZero)[move-1].y == size - 1 && (*posZero)[i-1].x + (*posZero)[i-1].y == size - 1))) &&
								(candidate==(*option)[i][(*nopts)[i]])) break;
						}
						if(!(i>=1)) (*option)[move][++((*nopts)[move])] = candidate;
					}

					if(row && col && sqr && ychk && type == 3) {
						for(i = move-1; i >= 1; i--){
							if(candidate==(*option)[i][(*nopts)[i]] && ((*posZero)[i-1].y==(*posZero)[move-1].y ||
								(*posZero)[i-1].x==(*posZero)[move-1].x || ((int)((*posZero)[i-1].y/size)==(int)((*posZero)[move-1].y/size) &&
									(int)((*posZero)[i-1].x/size)==(int)((*posZero)[move-1].x/size)))) break;
									// if(yChkDuplicate(candidate, (*option)[i][(*nopts)[i]], (*posZero)[move-1], (*posZero)[i-1], size)) break;
						}
						if(!(i>=1)) (*option)[move][++((*nopts)[move])] = candidate;
					}
				}
			}
		} else {
			move--;
			(*nopts)[move]--;
		}
	}
}
