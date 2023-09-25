package minesweeper.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterAll;

public class SaveHandlerTest {
	
	private Board game;
	private SaveHandler saveHandler = new SaveHandler();
	
	
	@BeforeEach
	public void setup() {
		game = new Board(8,8,6);
	}
	
	@Test
	@DisplayName("Tester at det kastes exception når mn prøver å laste en fil som ikke eksisterer")
	public void testLoadNonExistingFile() {
		assertThrows(
			FileNotFoundException.class,
			() -> game = saveHandler.load("foo")
		);
	}
	
	@Test
	@DisplayName("Tester at det kastes exception hvis tekstfil er på feil format")
	public void testLoadWrongFormatFile() {
		assertThrows(
			Exception.class,
			() -> game = saveHandler.load("errorFile"),
			"An exception should be thrown if loaded file is on wrong format!"
			);
	}
	
	@Test
	public void testLoad() {
		Board savedGame = game; //Required to ignore Eclipse warning
		try {
			savedGame = saveHandler.load("loadTest");
		} catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		/*Fordi koden forventer spesifikke "char"s fra tile.klassen vil den ikke sammenlikne 
		  med vanlige java.lang.Character. Derfor kjører jeg en assertTrue for hvert tilfelle.
		 */
		String boardString = "EE1@@@@@EE12211@EEEEEE1@EEEE123@EEEE1@@@EEEE1@@@EEE11@@@EEE1@@@@";
		for (int y = 0; y < savedGame.getHeight(); y++ ) {
			for(int x = 0; x < savedGame.getWidth(); x++) {
				char type = boardString.charAt(y * savedGame.getWidth() + x);
				if(type == '1')
					assertTrue(savedGame.getTile2(x, y).isOne());
				else if(type == '2')
					assertTrue(savedGame.getTile2(x, y).isTwo());
				else if(type == '3')
					assertTrue(savedGame.getTile2(x, y).isThree());
				else if(type == '4')
					assertTrue(savedGame.getTile2(x, y).isFour());
				else if(type == '5')
					assertTrue(savedGame.getTile2(x, y).isFive());
				else if(type == '6')
					assertTrue(savedGame.getTile2(x, y).isSix());
				else if(type == '7')
					assertTrue(savedGame.getTile2(x, y).isSeven());
				else if(type == '8')
					assertTrue(savedGame.getTile2(x, y).isEight());
				else if(type == 'B')
					assertTrue(savedGame.getTile2(x, y).isBomb());
				else if(type == 'F')
					assertTrue(savedGame.getTile2(x, y).isFlag());
				else if(type == 'E')
					assertTrue(savedGame.getTile2(x, y).isEmpty());
				else if(type == '@')
					assertTrue(savedGame.getTile2(x, y).isCovered());
			}
		}
		String boardString2 = "EE1BB1EEEE122111EEEEEE1BEEEE1232EEEE1BB1EEEE1221EEE111EEEEE1B1EE";
		for (int y = 0; y < savedGame.getHeight(); y++ ) {
			for(int x = 0; x < savedGame.getWidth(); x++) {
				char type = boardString2.charAt(y * savedGame.getWidth() + x);
				if(type == '1')
					assertTrue(savedGame.getTile(x, y).isOne());
				else if(type == '2')
					assertTrue(savedGame.getTile(x, y).isTwo());
				else if(type == '3')
					assertTrue(savedGame.getTile(x, y).isThree());
				else if(type == '4')
					assertTrue(savedGame.getTile(x, y).isFour());
				else if(type == '5')
					assertTrue(savedGame.getTile(x, y).isFive());
				else if(type == '6')
					assertTrue(savedGame.getTile(x, y).isSix());
				else if(type == '7')
					assertTrue(savedGame.getTile(x, y).isSeven());
				else if(type == '8')
					assertTrue(savedGame.getTile(x, y).isEight());
				else if(type == 'B')
					assertTrue(savedGame.getTile(x, y).isBomb());
				else if(type == 'F')
					assertTrue(savedGame.getTile(x, y).isFlag());
				else if(type == 'E')
					assertTrue(savedGame.getTile(x, y).isEmpty());
			}
		}
	}	
	
	@Test
	@DisplayName("Tester at jeg kan lagre spillet jeg brukte til loadTest, med et annet navn, og at spillene samsvarer")
	public void testSave() {
		Board gameToSave = null;
		
		try {
			gameToSave = saveHandler.load("loadTest");
		} catch (FileNotFoundException e1) {
			fail("Could not load file");
		}
		try {
			saveHandler.save("saveTest", gameToSave);
		} catch (FileNotFoundException e) {
			fail("Could not save file");
		}
		
		byte[] testFile = null, newFile = null;
		
		try {
			testFile = Files.readAllBytes(Path.of(SaveHandler.getFilePath("loadTest")));
		} catch(IOException e) {
			fail("Could not load testFile");
		}
		
		try {
			newFile = Files.readAllBytes(Path.of(SaveHandler.getFilePath("saveTest")));
		} catch(IOException e) {
			fail("Could not load saved file");
		}
		
		assertNotNull(testFile);
		assertNotNull(newFile);
		assertTrue(Arrays.equals(testFile, newFile));
	}
	
	@AfterAll
	@DisplayName("Sletter filen jeg lagret, slik at det er konsistens i appen, slik det var før testen ")
	static void teardown() {
		File newSaveTestFile = new File(SaveHandler.getFilePath("saveTest"));
		newSaveTestFile.delete();
	}
}
