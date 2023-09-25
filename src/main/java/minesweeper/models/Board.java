package minesweeper.models;

import java.util.Random;

public class Board { //Class to create board and run game 

	private Tile[][] board; 
	private Tile[][] board2;
	
	/* Info 
	  "board" represents the board with all bombs and numbers visible 
	  "board2" represents a board filled with '@' (unOpened fields)
	   when playing, the "board2" is changed and filled with numbers and flags by comparison with "board"
	 */
	
	private int height; 
	private int width;
	private int numMines; 
	private int numFlags; 
	private boolean isGameOver = false;
	private boolean isGameWon = false;
	
	//Creates the board (grid) 
	public Board(int height, int width, int numMines) {

		if(height < 0 || width < 0 || numMines < 0) {
			throw new IllegalArgumentException("Height, width or number of Mines cannot be negative ");
		}
		this.height = height;
		this.width = width; 
		this.numMines = numMines;
		this.numFlags = numMines; 
		
		this.board = new Tile[height][width];
		this.board2 = new Tile[height][width];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				board[y][x] = new Tile(x,y);
				board2[y][x] = new Tile(x,y);
			}
		}
		generateMines();
		placeNumbersOnAdjacentMines();
		fillSecondBoard();
	}
	
	//Methods 
	
	public Tile getTile(int x, int y) {
		if(!isTileValid(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bound");
		}
		return this.board[y][x];
	}
		
	public Tile getTile2(int x, int y) {
		if(!isTileValid(x, y)) {
			throw new IllegalArgumentException("Coordinates out of bound");
		}
		return this.board2[y][x];
	}
	
	private boolean isTileValid (int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}

	//Randomly generates and lays out correct number of mines on board
	public void generateMines() {
		Random rand = new Random();
		int heightUpperbound = getHeight();
		int widthUpperbound = getWidth();
		int placeMines = getNumMines();
		while (placeMines > 0) {
			
			int randomY = rand.nextInt(heightUpperbound);
			int randomX = rand.nextInt(widthUpperbound);
			
			while(getTile(randomY, randomX).isBomb()) {
				randomY = rand.nextInt(heightUpperbound);
				randomX = rand.nextInt(widthUpperbound);
			}
			getTile(randomY, randomX).setBomb();
			placeMines--; 
		}
	}
	
	//Fills board2 with @ before gameStart
	public void fillSecondBoard() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				getTile2(x,y).setCovered();
			}
		}
	}
	
	public void placeNumbersOnAdjacentMines() {
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				//Checks whether the given (x, y) is a bomb/mine
				if(!getTile(x, y).isBomb()) {
					int count = 0; 
					
					//Checks whether a tile lies near a border
					boolean xIsLeftBorder = false;
					boolean xIsRightBorder = false;
					boolean yIsTopBorder = false;
					boolean yIsBottomBorder = false;
					
					if(x == 0)
						xIsLeftBorder = true;
					else if(x == getWidth()-1)
						xIsRightBorder = true;
					if(y == 0)
						yIsBottomBorder = true;
					else if(y == getHeight()-1)
						yIsTopBorder = true;
						
					//If not a bomb, start counting number of adjacent bombs
					if(!xIsLeftBorder && !yIsBottomBorder) {
						if(getTile(x-1, y-1).isBomb())
							count++;
						}
					if(!xIsLeftBorder) {
						if(getTile(x-1, y).isBomb())
							count++;
						}
					if(!xIsLeftBorder && !yIsTopBorder) {
						if(getTile(x-1, y+1).isBomb())
							count++;
						}
					if(!yIsBottomBorder) {
						if(getTile(x, y-1).isBomb())
							count++;
						}
					if(!yIsTopBorder) {
						if(getTile(x, y+1).isBomb())
							count++;
						}
					if(!xIsRightBorder && !yIsBottomBorder) {
						if(getTile(x+1, y-1).isBomb())
							count++;
						}
					if(!xIsRightBorder) {
						if(getTile(x+1, y).isBomb())
							count++;
						}
					if(!xIsRightBorder && !yIsTopBorder) {
						if(getTile(x+1, y+1).isBomb())
							count++;
						}
					
					//Chooses the method with correct number of adjacent mines to (x, y)
					if(count == 1)
						getTile(x, y).setOne();
					else if(count == 2)
						getTile(x, y).setTwo();
					else if(count == 3)
						getTile(x, y).setThree();
					else if(count == 4)
						getTile(x, y).setFour();
					else if(count == 5)
						getTile(x, y).setFive();
					else if(count == 6)
						getTile(x, y).setSix();
					else if(count == 7)
						getTile(x, y).setSeven();
					else if(count == 8)
						getTile(x, y).setEight();
					else {
						getTile(x,y).setEmpty();
					}
				}
			}
		}
	}
	
	//Checks the tiletype of original board, and sets the equivalent tile in board2 to this type.
	public void checkOriginalTile(int x, int y) {
		
		if(getTile(x,y).isOne())
			getTile2(x,y).setOne();
		else if(getTile(x,y).isTwo())
			getTile2(x,y).setTwo();
		else if(getTile(x,y).isThree())
			getTile2(x,y).setThree();
		else if(getTile(x,y).isFour())
			getTile2(x,y).setFour();
		else if(getTile(x,y).isFive())
			getTile2(x,y).setFive();
		else if(getTile(x,y).isSix())
			getTile2(x,y).setSix();
		else if(getTile(x,y).isSeven())
			getTile2(x,y).setSeven();
		else if(getTile(x,y).isEight())
			getTile2(x,y).setEight();
		else if(getTile(x,y).isBomb())
			getTile2(x,y).setBomb();
		else if(getTile(x,y).isFlag())
			getTile2(x,y).setFlag();
		else if(getTile(x,y).isEmpty())
			getTile2(x,y).setEmpty();
	}
	
	public void onClick(int x, int y) {
		if(isGameOver || isGameWon) {
			throw new IllegalStateException("Not a valid gamestate to open tile");
		}
			if(getTile(x, y).isBomb()) {
				hasLost();
			}
			
			else if(getTile2(x, y).isCovered()) {
				if(getTile(x,y).isEmpty()) 
					emptyTiles(x,y);
				checkOriginalTile(x,y);
				updateBoard();
				checkHasWon();
			}
			else {
				System.out.println("Pick another field");
			}
	}
	
	public void onRightClick(int x, int y) {
		if(isGameOver || isGameWon) {
			throw new IllegalStateException("Not a valid gamestate to open tile");
		}
		if(getTile2(x,y).isBomb() && numFlags > 0) {
			getTile2(x,y).setFlag();
			setNumMines(numMines-1);
			setNumFlags(numFlags-1);
		}
		else if(getTile2(x,y).isFlag()) {
			if(getTile(x,y).isBomb()) {
				getTile2(x,y).setCovered();
				setNumMines(numMines+1);
				setNumFlags(numFlags+1);
			}
			else {
				getTile2(x,y).setCovered();
				setNumFlags(numFlags+1);
			}
		}
		else if(getTile2(x,y).isCovered() && numFlags > 0) {
			if(getTile(x,y).isBomb()) {
				getTile2(x,y).setFlag();
				setNumMines(numMines-1);
				setNumFlags(numFlags-1);
			}
			else {
				getTile2(x,y).setFlag();
				setNumFlags(numFlags-1);
			}
		}
		updateBoard();
		checkHasWon();
	}
	
	//Opens all empty tiles adjacent to an empty tile clicked
	public void emptyTiles(int x, int y) {
		
		//Checks whether a tile lies near a border
		boolean xIsLeftBorder = false;
		boolean xIsRightBorder = false;
		boolean yIsTopBorder = false;
		boolean yIsBottomBorder = false;
					
		if(x == 0)
			xIsLeftBorder = true;
		else if(x == getWidth()-1)
			xIsRightBorder = true;
		if(y == 0)
			yIsBottomBorder = true;
		else if(y == getHeight()-1)
			yIsTopBorder = true;
						
		//If not a bomb, start counting number of adjacent bombs
			if(!xIsLeftBorder && !yIsBottomBorder) {
				if(getTile(x-1, y-1).isEmpty() && getTile2(x-1, y-1).isCovered()) {
					getTile2(x-1, y-1).setEmpty();
					emptyTiles(x-1, y-1);
				}
				else if (getTile2(x-1, y-1).isCovered())
					checkOriginalTile(x-1, y-1);
			}
			if(!xIsLeftBorder) {
				if(getTile(x-1, y).isEmpty() && getTile2(x-1, y).isCovered()) {
					getTile2(x-1, y).setEmpty();
					emptyTiles(x-1, y);
				}
				else if (getTile2(x-1, y).isCovered())
					checkOriginalTile(x-1, y);
			}
			if(!xIsLeftBorder && !yIsTopBorder) {
				if(getTile(x-1, y+1).isEmpty() && getTile2(x-1, y+1).isCovered()) {
					getTile2(x-1, y+1).setEmpty();
					emptyTiles(x-1, y+1);
				}
				else if (getTile2(x-1, y+1).isCovered())
					checkOriginalTile(x-1, y+1);
			}
			if(!yIsBottomBorder) {
				if(getTile(x, y-1).isEmpty() && getTile2(x, y-1).isCovered()) {
					getTile2(x, y-1).setEmpty();
					emptyTiles(x, y-1);
				}
				else if (getTile2(x, y-1).isCovered())
					checkOriginalTile(x, y-1);
			}
			if(!yIsTopBorder) {
				if(getTile(x, y+1).isEmpty() && getTile2(x, y+1).isCovered()) {
					getTile2(x, y+1).setEmpty();
					emptyTiles(x, y+1);
				}
				else if (getTile2(x, y+1).isCovered())
					checkOriginalTile(x, y+1);
			}
			if(!xIsRightBorder && !yIsBottomBorder) {
				if(getTile(x+1, y-1).isEmpty() && getTile2(x+1, y-1).isCovered()) {
					getTile2(x+1, y-1).setEmpty();
					emptyTiles(x+1, y-1);
				}
				else if (getTile2(x+1, y-1).isCovered())
					checkOriginalTile(x+1, y-1);
			}
			if(!xIsRightBorder) {
				if(getTile(x+1, y).isEmpty() && getTile2(x+1, y).isCovered()) {
					getTile2(x+1, y).setEmpty();
					emptyTiles(x+1, y);
				}
				else if (getTile2(x+1, y).isCovered())
					checkOriginalTile(x+1, y);
			}
			if(!xIsRightBorder && !yIsTopBorder) {
				if(getTile(x+1, y+1).isEmpty() && getTile2(x+1, y+1).isCovered()) {
					getTile2(x+1, y+1).setEmpty();
					emptyTiles(x+1, y+1);
				}
				else if (getTile2(x+1, y+1).isCovered())
					checkOriginalTile(x+1, y+1);
			}
		}
	
    public void hasLost() {
    	setGameOver();
		revealMines();
		System.out.println("You Lost");
    }
    
    public void hasWon() {
    	setGameWon();
    	System.out.println("You Won!");
    }
    
    public void checkHasWon() {
    	boolean allTilesOpened = true;
    	for (int y = 0; y < getHeight(); y++)
			for (int x = 0; x < getWidth(); x++) {
				if((getTile2(x, y).isCovered())) {
					allTilesOpened = false;
				}
			}
    	if(allTilesOpened) {
    		hasWon();
    	}
    }
    
	//Getters and setters 
    
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getNumMines() {
		return numMines;
	}

	public void setNumMines(int numMines) {
		if(numMines < 0)
			throw new IllegalArgumentException("numMines cannot be negative");
		this.numMines = numMines;
	}

	public int getNumFlags() {
		return numFlags;
	}
	
	public void setNumFlags(int numFlags) {
		if(numFlags < 0)
			throw new IllegalArgumentException("numFlags cannot be negative");
		this.numFlags = numFlags;
	}
	
	public boolean isGameWon() {
		return isGameWon;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void setGameOver() {
		isGameOver = true;
	}
	
	public void setGameWon() {
		isGameWon = true;
	}
	
	//Methods to print and update board in console 
	
	public void updateBoard() {
		String boardString = "";
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				boardString += getTile2(x,y) + " ";
				}
			boardString += "\n";
			}		
		System.out.println(boardString);
	}
	
	public void revealMines() {
		String boardString = "";
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if(getTile(x, y).isBomb()) {
					boardString += "B";
				}
				else {
					boardString += " ";
				}
			}
				boardString += "\n";
			}
		System.out.println(boardString);
	}
	
	public String toString() {
		String boardString = "";
		for (int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
					
				boardString += getTile(x, y) + " ";	
			}
			boardString += "\n";
		}
		if (isGameWon) {
			boardString += "\n\nGame won!";
		} else if (isGameOver) {
			boardString = "\n\nYou Lost!";
        }
		
		return boardString;
	}
}
