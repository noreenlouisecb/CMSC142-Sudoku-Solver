package cmsc142.checker;

public class CheckerGenerator {
	public static CheckerStrategy[] NormalChecking() {
		CheckerStrategy[] checkers = new CheckerStrategy[3];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
		return checkers;
	}

	public static CheckerStrategy[] XChecking() {
		CheckerStrategy[] checkers = new CheckerStrategy[4];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
		checkers[3] = new XChecker();
		return checkers;
	}

	public static CheckerStrategy[] YChecking() {
		CheckerStrategy[] checkers = new CheckerStrategy[4];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
		checkers[3] = new YChecker();
		return checkers;
	}

	public static CheckerStrategy[] XYChecking() {
		CheckerStrategy[] checkers = new CheckerStrategy[5];
		checkers[0] = new RowChecker();
		checkers[1] = new ColumnChecker();
		checkers[2] = new SquareChecker();
		checkers[3] = new XChecker();
		checkers[4] = new YChecker();
		return checkers;
	}

}
