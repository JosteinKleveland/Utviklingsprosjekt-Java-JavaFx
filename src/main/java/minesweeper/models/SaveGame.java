package minesweeper.models;

import java.io.FileNotFoundException;

public interface SaveGame {
	
	void save(String filename, Board game) throws FileNotFoundException;
	
	Board load(String filename) throws FileNotFoundException;

}
