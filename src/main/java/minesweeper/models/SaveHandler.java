package minesweeper.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.scene.text.Text;

public class SaveHandler implements SaveGame {

	public final static String SAVE_FOLDER = "src/main/java/minesweeper/saves/";
	
	public void save(String filename, Board game) throws FileNotFoundException{
		
		PrintWriter writer = new PrintWriter(getFilePath(filename));
			
			writer.println(game.getHeight());
			writer.println(game.getWidth());
			writer.println(game.getNumMines());
			writer.println(game.isGameOver());
			writer.println(game.isGameWon());
			
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.print(game.getTile2(x, y));
				}
			} 
			writer.println();
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.print(game.getTile(x, y));
				}
			}
			writer.close();
		}
	
	public Board load(String filename) throws FileNotFoundException {
		File file = new File(getFilePath(filename));
		Scanner scanner = new Scanner(file);
			int height = scanner.nextInt();
			int width = scanner.nextInt();
			int mines = scanner.nextInt();
			Board game = new Board(height, width, mines);
			
			if(scanner.nextBoolean())
				game.setGameOver();
			if(scanner.nextBoolean())
				game.setGameWon();
			
			scanner.nextLine();
			
			String boardstring = scanner.next();
			for (int y = 0; y < game.getHeight(); y++ )
				for(int x = 0; x < game.getWidth(); x++) {
					char type = boardstring.charAt(y * width + x);
					
					if(type == '1')
						game.getTile2(x, y).setOne();
					else if(type == '2')
						game.getTile2(x, y).setTwo();
					else if(type == '3')
						game.getTile2(x, y).setThree();
					else if(type == '4')
						game.getTile2(x, y).setFour();
					else if(type == '5')
						game.getTile2(x, y).setFive();
					else if(type == '6')
						game.getTile2(x, y).setSix();
					else if(type == '7')
						game.getTile2(x, y).setSeven();
					else if(type == '8')
						game.getTile2(x, y).setEight();
					else if(type == 'B')
						game.getTile2(x, y).setBomb();
					else if(type == 'F')
						game.getTile2(x, y).setFlag();
					else if(type == 'E')
						game.getTile2(x, y).setEmpty();
					else if(type == '@')
						game.getTile2(x, y).setCovered();	
				}
			scanner.nextLine();
			
			String boardstring2 = scanner.next();
			for (int y = 0; y < game.getHeight(); y++ )
				for(int x = 0; x < game.getWidth(); x++) {
					char type = boardstring2.charAt(y * width + x);
					
					if(type == '1')
						game.getTile(x, y).setOne();
					else if(type == '2')
						game.getTile(x, y).setTwo();
					else if(type == '3')
						game.getTile(x, y).setThree();
					else if(type == '4')
						game.getTile(x, y).setFour();
					else if(type == '5')
						game.getTile(x, y).setFive();
					else if(type == '6')
						game.getTile(x, y).setSix();
					else if(type == '7')
						game.getTile(x, y).setSeven();
					else if(type == '8')
						game.getTile(x, y).setEight();
					else if(type == 'B')
						game.getTile(x, y).setBomb();
					else if(type == 'F')
						game.getTile(x, y).setFlag();
					else if(type == 'E')
						game.getTile(x, y).setEmpty();
					else if(type == '@')
						game.getTile(x, y).setCovered();
					
				}
		
		scanner.close();
		return game;
	}
	
	protected static String getFilePath(String filename){
		return SAVE_FOLDER + filename + ".txt";
	}
}
