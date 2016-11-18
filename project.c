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

int main(){
	int **puzzle, **solution;
	int size;
	int puzzleCount, zeroCount, solutionCount;
	int i, j;
	// Backtracking
	int *nopts;
	int **option;
	zeroList *posZero;

	FILE *fp = fopen("input1.txt", "r+");
	FILE *fp2 = fopen("output.txt", "w+");
	fscanf(fp, "%d", &puzzleCount);

	for(i = 0; i < puzzleCount; i++) {
		zeroCount = 0, solutionCount = 0;
		setup(&puzzle, &solution, fp, &size, &zeroCount, &posZero, &nopts, &option);

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

int xChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	if (posZero.x == posZero.y) {
		int ii;
		for(ii = 0; ii < size*size; ii++) {
			if (candidate ==  puzzle[ii][ii])
				return 0;
		}
	}

	if ((posZero.x + posZero.y) == size*size - 1) {
		int ii, jj;
		for(ii = 0, jj = size*size - 1; ii < size*size && jj >= 0; ii++, jj--) {
			if (candidate ==  puzzle[ii][jj])
				return 0;
		}
	}
	return 1;
}

int candidateChecker(int candidate, zeroList posZero, int **puzzle, int size) {
	return sqrChecker(candidate, posZero, puzzle, size) &&
		rowChecker(candidate, posZero, puzzle, size) &&
		colChecker(candidate, posZero, puzzle, size) &&
		xChecker(candidate, posZero, puzzle, size);
}

int isAligned(zeroList pos1, zeroList pos2, int size) {
	return pos1.y==pos2.y || pos1.x==pos2.x || ((int)(pos1.y/size)==(int)(pos2.y/size) && 
		(int)(pos1.x/size)==(int)(pos2.x/size)) || 
		(pos1.x == pos1.y && pos2.x == pos2.y);
}

void solveSudoku(int **puzzle, int size, int **nopts, int ***option, int zeroCount, zeroList **posZero, int *solutionCount, FILE *fp){
	int start, move;
	int row, col, sqr, candidate, i, j, k;
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
							printf("%2d", (*option)[k][(*nopts)[k]]);
							fprintf(fp, "%d ", (*option)[k][(*nopts)[k]]);
							k++;
						}
						else{
							printf("%2d", puzzle[i][j]);
							fprintf(fp, "%d ", puzzle[i][j]);
						}
					}
					printf("\n");
					fprintf(fp, "\n");
				}
				printf("\n");
				fprintf(fp, "\n");
			} else if(move == 1) {
				for(candidate = size*size; candidate >=1; candidate --) {
					if(candidateChecker(candidate, (*posZero)[move-1], puzzle, size)) {
						(*nopts)[move]++;
						(*option)[move][(*nopts)[move]] = candidate;
					}
				}
			} else{
				for(candidate=size*size; candidate >= 1; candidate--) {
					if(candidateChecker(candidate, (*posZero)[move-1], puzzle, size)) {
						for(i = move-1; i >= 1;i--) {
						if(candidate==(*option)[i][(*nopts)[i]] && isAligned((*posZero)[i-1], (*posZero)[move-1], size)) break;

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
