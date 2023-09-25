package minesweeper.models;

public class Tile { //Class for one square

	private int x; 
	private int y; 
	private char type = ' ';
	
	public Tile(int x, int y) {
		if(x < 0 || y < 0)
			throw new IllegalArgumentException("Tile cannot be negative");
		this.x = x; 
		this.y = y; 
	}
	
	//Setters to give a Tile a value 
	public void setBomb() {
		type = 'B';
	}
	public void setFlag() {
		type = 'F';
	}
	public void setEmpty() {
		type = 'E';
	}
	public void setCovered() {
		type = '@';
	}
	public void setOne() {
		type = '1';
	}
	public void setTwo() {
		type = '2';
	}
	public void setThree() {
		type = '3';
	}
	public void setFour() {
		type = '4';
	}
	public void setFive() {
		type = '5';
	}
	public void setSix() {
		type = '6';
	}
	public void setSeven() {
		type = '7';
	}
	public void setEight() {
		type = '8';
	}
	
	//Booleans to check the value a a Tile
	public boolean isBomb() {
		return type == 'B';
	}
	public boolean isFlag() {
		return type == 'F';
	}
	public boolean isEmpty() {
		return type == 'E';
	}
	public boolean isCovered() {
		return type == '@';
	}
	public boolean isOne() {
		return type == '1';
	}
	public boolean isTwo() {
		return type == '2';
	}
	public boolean isThree() {
		return type == '3';
	}
	public boolean isFour() {
		return type == '4';
	}
	public boolean isFive() {
		return type == '5';
	}
	public boolean isSix() {
		return type == '6';
	}
	public boolean isSeven() {
		return type == '7';
	}
	public boolean isEight() {
		return type == '8';
	}	
	
	//Getters for x and y
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
	@Override
	public String toString() {
		return Character.toString(type);
	}
}
