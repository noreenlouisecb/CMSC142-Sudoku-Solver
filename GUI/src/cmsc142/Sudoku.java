package cmsc142;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import cmsc142.solver.*;
import cmsc142.checker.CheckerGenerator;

public class Sudoku implements ActionListener{
	static int[][] puzzle;
	static String temp;
	static String regex = "[1";
	static String[] tokens;
	static int problemCount, size = 0;
	static Puzzle[] puzzleList;
	static JFrame frame = new JFrame("Sudoku");
	static JButton[][] buttons;	
	static int index = 0;	
	static JPanel gamePanel = new JPanel();
	static JPanel subSquare;
	static SolverStrategy solver;
	public static void main(String[] args){
		
		initializeGUI();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("../input1.txt"));

			temp = br.readLine();
			problemCount = Integer.parseInt(temp);
			puzzleList = new Puzzle[problemCount];

			for(int k = 0; k < problemCount; k++){
				temp = br.readLine();
				size = Integer.parseInt(temp);	
				puzzle = new int[size*size][size*size];
				for(int i = 0; i < size*size; i++){
					temp = br.readLine();
					tokens = temp.split("[ ]+");
					for(int j = 0; j < tokens.length; j++){
						puzzle[i][j] = Integer.parseInt(tokens[j]);
					}
				}
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
	}
	

	public Sudoku(int x, int y, int input){
		int top, left, bottom, right;
		top = left = bottom = right = 1;
		buttons[x][y] = new JButton();
		buttons[x][y].addActionListener(this);		
		if(input != 0){
			buttons[x][y].setEnabled(false);	
			buttons[x][y].setText(Integer.toString(input));			
			buttons[x][y].setBackground(Color.black);		
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
					String temp = b.showInputDialog(frame, "Input number from 1 - " + (size*size));
					if(temp != null && temp.matches(regex)){
						puzzleList[index].setInput(Integer.parseInt(temp), i, j);
						puzzle[i][j] = Integer.parseInt(temp);
						buttons[i][j].setText(temp);	
					}
				}
			}
		}
	}

	public static void setSudoku(){
		gamePanel.removeAll();
		size = puzzleList[index].getSize();

		for(int i = 2; i <= size*size; i++){
			regex = regex + Integer.toString(i);
		}
		regex = regex + "]";
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
		frame.add(gamePanel, BorderLayout.CENTER);	
	}

	public static void initializeGUI(){
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout(5,5));
		gamePanel.setSize(400,450);			

		JPanel navBar = new JPanel();
		JPanel solveContainer = new JPanel();
		JPanel prevContainer = new JPanel();
		JPanel nextContainer = new JPanel();
		JButton solve = new JButton();
		JButton next = new JButton();
		JButton prev = new JButton();
		navBar.setLayout(new GridLayout(1,3));

		solve.setText("Solve");
		prev.setText("Prev");
		next.setText("Next");
		solve.setVisible(true);
		prev.setVisible(true);
		next.setVisible(true);
		solve.setBounds(10, 10, 100, 50);		
		next.setBounds(10, 10, 100, 50);		
		prev.setBounds(10, 10, 100, 50);		
	
		solveContainer.setVisible(true);
		prevContainer.setVisible(true);
		nextContainer.setVisible(true);
		prevContainer.add(prev);
		solveContainer.add(solve);
		nextContainer.add(next);
		
		navBar.add(prevContainer);
		navBar.add(solveContainer);
		navBar.add(nextContainer);

		frame.add(navBar, BorderLayout.PAGE_START);
	

		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(index+1 < problemCount){
					index++;
					setSudoku();		
				} 
			}
		});
			
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(index-1 >= 0){
					index--;
					setSudoku();		
				} 
			}
		});

		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 solver.solve(puzzleList[index]);
			}
		});
	}
}
