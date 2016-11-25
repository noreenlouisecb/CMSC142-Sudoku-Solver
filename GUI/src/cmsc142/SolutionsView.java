package cmsc142;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SolutionsView extends JFrame {
	private LinkedList<int[][]> solutions = new LinkedList<>();
	private int currentlyShown = 0;
	private JPanel gamePanel = new JPanel();

	private class SequenceButtonListener implements ActionListener {
		private int increment;
		public SequenceButtonListener(int increment) {
			this.increment = increment;
		}

		public void actionPerformed(ActionEvent e){
			updateBoard(increment);
		}
	}

	public SolutionsView(String windowName) {
		super(windowName);

		this.setSize(700, 500);
		this.setLayout(new BorderLayout(5,5));
		gamePanel.setSize(400,550);			

		JPanel navBar = new JPanel();
		JPanel prevContainer = new JPanel();
		JPanel nextContainer = new JPanel();
		JButton next = new JButton();
		JButton prev = new JButton();
		navBar.setLayout(new GridLayout(1,4));
		
		prev.setText("Prev");
		next.setText("Next");
		prev.setVisible(true);
		next.setVisible(true);
		next.setBounds(10, 10, 100, 50);		
		prev.setBounds(10, 10, 100, 50);		
	
		prevContainer.setVisible(true);
		nextContainer.setVisible(true);
		prevContainer.add(prev);
		nextContainer.add(next);
		
		navBar.add(prevContainer);
		navBar.add(nextContainer);

		this.add(navBar, BorderLayout.PAGE_START);
	

		next.addActionListener(new SequenceButtonListener(1));
			
		prev.addActionListener(new SequenceButtonListener(-1));
	}

	public void changeSolutions(LinkedList<int[][]> solutions) {
		this.solutions = solutions;
		currentlyShown = 0;
		updateBoard(0);
	}

	private void updateBoard(int increment) {
		int newIndex = currentlyShown + increment;
		if (newIndex < 0 || newIndex >= solutions.size()) {
			return;
		}

		currentlyShown = newIndex;

		int[][] solution = solutions.get(currentlyShown);
		gamePanel.removeAll();
		int sideSize = solution.length;

		gamePanel.setVisible(false);
		gamePanel.setLayout(new GridLayout(sideSize, sideSize));
		JLabel[][] labels = new JLabel[sideSize][sideSize];					
		for(int i = 0; i < sideSize; i++){
			for(int j = 0; j < sideSize; j++){
                labels[i][j] = new JLabel(Integer.toString(solution[i][j]));
				gamePanel.add(labels[i][j]);
			}
		}

		gamePanel.setVisible(true);
		this.add(gamePanel, BorderLayout.CENTER);	
	}
}
