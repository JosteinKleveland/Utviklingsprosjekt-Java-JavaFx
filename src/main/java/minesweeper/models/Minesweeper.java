package minesweeper.models;


public class Minesweeper { //Main-class to choose difficulty and start a new game
	
	private String level;
	private Board game;
	
	public Minesweeper(String level) {
		this.level = level;
	}
	
	public Board run() {
		
		int height;
		int width;
		int numMines;
	
		switch(level) {
			case "medium": 
				height = 12;
				width = 12;
				numMines = 20;
				break;
			case "hard": 
				height = 16;
				width = 16;
				numMines = 50;
				break;
			default: //"Easy"
				height = 8;
				width = 8;
				numMines = 6;
		}
		return this.game = new Board(height, width, numMines);
	}
}
