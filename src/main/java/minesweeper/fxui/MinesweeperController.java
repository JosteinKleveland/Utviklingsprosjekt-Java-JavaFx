package minesweeper.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import minesweeper.models.Board;
import minesweeper.models.Minesweeper;
import minesweeper.models.SaveHandler;
import minesweeper.models.Tile;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

public class MinesweeperController {

	private Board game;
	private Minesweeper ms;
	private SaveHandler saveHandler = new SaveHandler();
	
	@FXML
	Pane startMenu, gamePane, paneBoard, paneResult, gameState, gameInfo, gameFeedback, minesweeper;

	@FXML
	TextField filenameToSave;

	@FXML
	Text fileNotFoundMessage, errorWhenLoadingFile;
	
	@FXML
	ListView<Button> fileList;
	
	public void initialize() {
		getFiles();
	}
	
	private void getFiles() {
		try {
			fileList.getItems().clear();
			Stream<Path> stream = Files.list(Paths.get("src/main/java/minesweeper/saves/"));
			Set<String> listOfFiles = stream.map(Path::getFileName).map(Path::toString).collect(Collectors.toSet());
			listOfFiles.forEach(file -> {
				Button button = new Button();
				button.setText(file);
				button.setOnMouseClicked(event -> {	
					handleLoad(file);
				});
				fileList.getItems().add(button);
			});
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void startGame(Event event) {		
		ms = new Minesweeper(((Control)event.getSource()).getId());
			System.out.println(((Control)event.getSource()).getId());
		game = ms.run(); //Creates board with mines and numbers
			System.out.println(game.toString());
		
		startMenu.setVisible(false);
		gamePane.setVisible(true);
		fileNotFoundMessage.setVisible(false);
		errorWhenLoadingFile.setVisible(false);
		filenameToSave.clear();
		createPaneBoard();
		
	}
	
	public void createPaneBoard() {
		gameFeedback.getChildren().clear();
		paneBoard.getChildren().clear();
		for (int y = 0; y < game.getHeight(); y++)
			for (int x = 0; x < game.getWidth(); x++) {
				Tile tile = game.getTile(x, y);
				StackPane pane = new StackPane();
				Text text = new Text();
				pane.setTranslateY(y*25);
				pane.setTranslateX(x*25);
				pane.setPrefHeight(25);
				pane.setPrefWidth(25);
				text.setText(getTileValue(tile));
				if(!game.getTile2(x, y).isCovered())	
					text.setVisible(true);
				else
					text.setVisible(false);
				
				pane.getChildren().add(text);
				pane.setStyle("-fx-background-color: " + getTileColor(x, y, text) + "-fx-border-color: black; -fx-border-width: 1px;");
				
				final int coordinateX = x;
				final int coordinateY = y;
				final StackPane stackPane = pane;
				final Text textNode = text;
					
				pane.setOnMouseClicked(event -> {
					if(event.getButton() != MouseButton.SECONDARY) {
						handleOnClick(coordinateX, coordinateY, stackPane, textNode);
					}
					else {
						handleOnRightClick(coordinateX, coordinateY, stackPane, textNode);
					}
				});
				
				paneBoard.getChildren().add(pane);
			}
		}
	
	public void handleOnClick(int x, int y, StackPane pane, Text textNode) {
		
		if(!game.getTile2(x, y).isFlag() && game.getTile2(x, y).isCovered()) {
				game.onClick(x, y);
				createPaneBoard();
				flagCount();
				checkHasWon();
			}
		else {
			handleGameFeedback();
			checkHasWon();
		}
	}
	
	public void handleOnRightClick(int x, int y, StackPane pane, Text textNode) {
		
		if(game.getTile2(x, y).isCovered() || game.getTile2(x, y).isFlag()) {
			game.onRightClick(x, y);
			createPaneBoard();
			flagCount();
			checkHasWon();
		}
		else {
			handleGameFeedback();
			checkHasWon();
		}	
	}
	
	public void checkHasWon() {
		
		if(game.isGameWon()) {
			handleHasWon();
		} 
		else if(game.isGameOver()) {
			handleHasLost();
			handleRevealMines();
		}	
	}
	
	public void handleHasLost() {
		Text text = new Text();
		text.setText("GAME OVER!");
		text.setStyle("-fx-font-size: 40px");
		text.setFill(Color.RED);
		gameFeedback.getChildren().clear();
		gameFeedback.getChildren().add(text);
	}
	
	public void handleHasWon() {
		Text text = new Text();
		text.setText("YOU WON!");
		text.setStyle("-fx-font-size: 40px");
		text.setFill(Color.GREEN);
		gameFeedback.getChildren().clear();
		gameFeedback.getChildren().add(text);
	}
	
	public void handleGameFeedback() {
		Text text = new Text();
		text.setText("Pick another field");
		text.setStyle("-fx-font-size: 20px");
		gameFeedback.getChildren().clear();
		gameFeedback.getChildren().add(text);
	}
	
	public void flagCount() {
		Text text = new Text();
		text.setText("FlagCount: " + game.getNumFlags());
		text.setStyle("-fx-font-size: 20px");
		gameFeedback.getChildren().add(text);
	}
		
	public void handleRevealMines() {
	
		paneBoard.getChildren().clear();
		for (int y = 0; y < game.getHeight(); y++)
			for (int x = 0; x < game.getWidth(); x++) {
				Tile tile = game.getTile(x, y);
				StackPane pane = new StackPane();
				Text text = new Text();
				pane.setTranslateY(y*25);
				pane.setTranslateX(x*25);
				pane.setPrefHeight(25);
				pane.setPrefWidth(25);
				if(game.getTile(x, y).isBomb())
					text.setText(getTileValue(tile));
					text.setVisible(true);
				pane.getChildren().add(text);

				//Draws start-board with green tiles
				if((x % 2 == 0 && y % 2 != 0) || (x % 2 != 0 && y % 2 == 0))
					pane.setStyle("-fx-background-color: " + "#ebdaa0;" + "-fx-border-color: black; -fx-border-width: 1px;");
				else {
					pane.setStyle("-fx-background-color: " + "#b3a989;" + "-fx-border-color: black; -fx-border-width: 1px;");
				}
				paneBoard.getChildren().add(pane);
			}
	}

	private String getFilenameToSave() {
    	String filename = this.filenameToSave.getText();
    	if (filename.isEmpty()) {
    		fileNotFoundMessage.setVisible(true);
    	}
    	else {
    		fileNotFoundMessage.setVisible(false);
    	}
    	return filename;
    }
    	
    @FXML
    private void handleSave() {
    	try {
    		saveHandler.save(getFilenameToSave(), game);
    		
    	} catch (FileNotFoundException e) {
    		fileNotFoundMessage.setVisible(true);
    	}
    	if(!fileNotFoundMessage.isVisible()) {
    	   	gamePane.setVisible(false);
        	startMenu.setVisible(true);
    	}
    	getFiles();
    }
    
    private void handleLoad(String filename) {
    	boolean exceptionFound = false;
    	try {
    		String[] fileName = filename.split("\\.");
    		String file = fileName[0];
    		game = saveHandler.load(file);
    	} catch (FileNotFoundException e) {
    		System.out.println("File not found");
    		exceptionFound = true;
    	} catch (NullPointerException e) {
    		System.out.println("Null pointer :(");
    		exceptionFound = true;
    	} catch (InputMismatchException e) {
    		System.out.println("Wrong input. GameFile is probably on wrong format");
    		errorWhenLoadingFile.setVisible(true);
    		exceptionFound = true;
    	}
    	if(!exceptionFound) {
	   		startMenu.setVisible(false);
	    	gamePane.setVisible(true);
	    	fileNotFoundMessage.setVisible(false);
	    	errorWhenLoadingFile.setVisible(false);
	    	filenameToSave.clear();
	    	createPaneBoard();
	    	checkHasWon();
    	}
    }
    
    @FXML
    private void handleMainMenu() {
    	startMenu.setVisible(true);
    	gamePane.setVisible(false);
    }
    
    
	private String getTileValue(Tile tile) {
    	String content;
    	
		if(tile.isBomb())
    		return content = "B";  
    	else if(tile.isOne())
    		return content = "1";  
    	else if(tile.isTwo())
    		return content = "2";  
    	else if(tile.isThree())
    		return content = "3";  
    	else if(tile.isFour())
    		return content = "4";  
    	else if(tile.isFive())
    		return content = "5";  
    	else if(tile.isSix())
    		return content = "6";  
    	else if(tile.isSeven())
    		return content = "7";  
    	else if(tile.isEight())
    		return content = "8";  
    	else 
    		return content = " ";  
    }
	
	
	private String getTileColor(int x, int y, Text textField) {
		String color;
		
		if(textField.isVisible()) {
			if(game.getTile2(x, y).isFlag()) {
				textField.setVisible(false);
				return color = "#FF0000;";
			}
			
			else if((x % 2 == 0 && y % 2 != 0) || (x % 2 != 0 && y % 2 == 0))
				return color = "#ebdaa0;";
			else {
				return color = "#b3a989;";
			}
		}
		else {
			if((x % 2 == 0 && y % 2 != 0) || (x % 2 != 0 && y % 2 == 0))
				return color = "#c2e77e;";
			else {
				return color = "#aedf53;";
			}
		}
	}
 }
