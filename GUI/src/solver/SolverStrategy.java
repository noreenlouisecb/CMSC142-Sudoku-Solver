package cmsc142.solver;

import java.util.LinkedList;
import cmsc142.Puzzle;

public interface SolverStrategy {
	LinkedList<Puzzle> solve(Puzzle board);
}
