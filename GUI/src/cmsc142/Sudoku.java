package cmsc142;

import cmsc142.checker.CheckerGenerator;
import cmsc142.solver.SolverStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Sudoku implements ActionListener{
	static int[][] puzzle;
	static int[][] puzzle2;
	static String temp;
	static String regex = "(1";
	static String[] tokens;
	static int problemCount, size = 0;
	static Puzzle[] puzzleList;
	static Puzzle[] original;
	static JFrame frame = new JFrame("Sudoku");
	static JButton[][] buttons;	
	static int index = 0;	
	static JPanel gamePanel = new JPanel();
	static JPanel subSquare;
	static SolverStrategy solver;
	static SolutionsView solutionsView = new SolutionsView("Solutions");
	public static void main(String[] args){
		
		initializeGUI();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("../input1.txt"));

			temp = br.readLine();
			problemCount = Integer.parseInt(temp);
			puzzleList = new Puzzle[problemCount];
			original = new Puzzle[problemCount];
			for(int k = 0; k < problemCount; k++){
				temp = br.readLine();
				size = Integer.parseInt(temp);	
				puzzle = new int[size*size][size*size];
				puzzle2 = new int[size*size][size*size];
				for(int i = 0; i < size*size; i++){
					temp = br.readLine();
					tokens = temp.split("[ ]+");
					for(int j = 0; j < tokens.length; j++){
						puzzle[i][j] = Integer.parseInt(tokens[j]);
						puzzle2[i][j] = Integer.parseInt(tokens[j]);
					}
				}
				original[k] = new Puzzle(puzzle2, size);
				puzzleList[k] = new Puzzle(puzzle, size);
			}
			
			br.close();

		} catch(Exception e){
			e.printStackTrace();
			return;
		}		
		

		solver = new SolverStrategy();
		solver.changeCheckers(CheckerGenerator.NormalChecking());
		setSudoku();	

	    frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		solutionsView.setVisible(true);
	}
	

	public Sudoku(int x, int y, int input){
		int top, left, bottom, right;
		top = left = bottom = right = 1;
		buttons[x][y] = new JButton();
		buttons[x][y].addActionListener(this);		
		if(input != 0){
			if((original[index].getPuzzle()[x][y]) == puzzleList[index].getPuzzle()[x][y]){
				buttons[x][y].setEnabled(false);		
				buttons[x][y].setBackground(Color.black);
			} else buttons[x][y].setBackground(Color.white);
			buttons[x][y].setText(Integer.toString(input));			
					
		} else buttons[x][y].setBackground(Color.white);		 	

		if(x == 0) top = 5;
		if(y == 0) left = 5;

		for(int i = 1; i <= size; i++){
			if(x == (i*size)-1) bottom = 5;
			if(y == (i*size)-1) right = 5; 
		}

		buttons[x][y].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.black));
	}

	public void actionPerformed(ActionEvent e){
		for(int i = 0; i < size*size; i++){
			for(int j = 0; j < size*size; j++){
				if(buttons[i][j].equals(e.getSource())){
					JOptionPane b = new JOptionPane();
					String temp = b.showInputDialog(frame, "Input number from 1 - " + (size*size) + " (0 to clear)");
					System.out.println(regex);
					if(temp != null && temp.matches(regex)){
						puzzleList[index].setInput(Integer.parseInt(temp), i, j);
						puzzle[i][j] = Integer.parseInt(temp);
						buttons[i][j].setText(temp);	
					}
					if(temp != null && temp.equals("0")){
						puzzleList[index].setInput(Integer.parseInt(temp), i, j);
						buttons[i][j].setText("");
						puzzle[i][j] = Integer.parseInt(temp);
					}
				}
			}
		}
	}

	public static void setSudoku(){
		gamePanel.removeAll();
		size = puzzleList[index].getSize();

		for(int i = 2; i <= size*size; i++){
			regex = regex + "|" + Integer.toString(i);
		}
		regex = regex + ")";
		gamePanel.setVisible(false);
		gamePanel.setLayout(new GridLayout(size*size, size*size));
		buttons = new JButton[size*size][size*size];					
		puzzle = puzzleList[index].getPuzzle();
		for(int i = 0; i < size*size; i++){
			for(int j = 0; j < size*size; j++){
				Sudoku temp = new Sudoku(i, j, puzzle[i][j]);
				gamePanel.add(buttons[i][j]);
			}
		}

		

		//printing
		for(int i = 0; i < size*size; i++){
			for(int j = 0; j < size*size; j++){
				System.out.print(puzzle[i][j] + " ");
			}System.out.println();
		}		
		gamePanel.setVisible(true);
	}

	public static void initializeGUI(){
		frame.setSize(850, 500);
		frame.setLayout(new BorderLayout(5,5));
		gamePanel.setSize(400,550);			

		JPanel navBar = new JPanel();
		JPanel solveContainer = new JPanel();
		JPanel solve2Container = new JPanel();
		JPanel prevContainer = new JPanel();
		JPanel nextContainer = new JPanel();
		JButton solve = new JButton();
		JButton solveReg = new JButton();
		JButton solveX = new JButton();
		JButton solveY = new JButton();
		JButton solveXY = new JButton();
		JButton next = new JButton();
		JButton prev = new JButton();
		navBar.setLayout(new GridLayout(1,4));
		
		solve2Container.setLayout(new GridLayout(1, 4));
		solve.setText("Solve");
		solveReg.setText("R");
		solveX.setText("X");
		solveY.setText("Y");
		solveXY.setText("XY");
		prev.setText("Prev");
		next.setText("Next");
		solveReg.setVisible(true);
		solve.setVisible(true);
		solveX.setVisible(true);
		solveY.setVisible(true);
		solveXY.setVisible(true);
		prev.setVisible(true);
		next.setVisible(true);
		solve.setBounds(10, 10, 100, 50);
		solveReg.setBounds(10, 10, 150, 50);
		solveX.setBounds(10, 10, 100, 50);
		solveY.setBounds(10, 10, 100, 50);
		solveXY.setBounds(10, 10, 150, 50);
		next.setBounds(10, 10, 100, 50);		
		prev.setBounds(10, 10, 100, 50);		
	
		solveContainer.setVisible(true);
		solve2Container.setVisible(true);
		prevContainer.setVisible(true);
		nextContainer.setVisible(true);
		prevContainer.add(prev);
		solveContainer.add(solve);
		solve2Container.add(solveReg);
		solve2Container.add(solveX);
		solve2Container.add(solveY);
		solve2Container.add(solveXY);
		nextContainer.add(next);
		
		navBar.add(prevContainer);
		navBar.add(solveContainer);
		navBar.add(solve2Container);
		navBar.add(nextContainer);

		frame.add(navBar, BorderLayout.PAGE_START);
		frame.add(gamePanel, BorderLayout.CENTER);	
	

		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(index+1 < problemCount){
					index++;
					regex = "(1";
					setSudoku();		
				} 
			}
		});
			
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(index-1 >= 0){
					index--;
					regex = "(1";
					setSudoku();		
				}
			}
		});

		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LinkedList<int[][]> solutions = solver.solve(puzzleList[index]);
				if (solutions.size() > 0) {
					solutionsView.changeSolutions(solutions);
					solutionsView.setVisible(true);
				} else {
					solutionsView.setVisible(false);
					JOptionPane.showMessageDialog(frame, "No solutions found");
				}
			}
		});

		solveX.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 solver.changeCheckers(CheckerGenerator.XChecking());
			}
		});

		solveY.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solver.changeCheckers(CheckerGenerator.YChecking());
			}
		});

		solveXY.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solver.changeCheckers(CheckerGenerator.XYChecking());
			}
		});

		solveReg.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				solver.changeCheckers(CheckerGenerator.NormalChecking());
			}
		});
	}
}
