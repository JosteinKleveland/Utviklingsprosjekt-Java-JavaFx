package minesweeper.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class BoardTest {
	
	private Board game;
	
	@BeforeEach
	public void setup() {
		game = new Board(5,5,5);
	}
	
	@Test
	@DisplayName("Tester at konstruktøren fungerer")
	public void testConstructor() {
		assertEquals(game.getHeight(), 5);
		assertEquals(game.getWidth(), 5);
		assertEquals(game.getNumMines(), 5);
		
		try {
			Board game2 = new Board(-5, 5, 5);
			fail("An IllegalArgumentException should be thrown");
		} catch(IllegalArgumentException e) {
		}
	}
	
	@Test
	@DisplayName("Tester at getTile-metoden feiler ved negative argumenter")
	public void testGetTile() {
		try {
			game.getTile(-5, -8);
			fail("An IllegalArgumentException should be thrown");
		} catch(IllegalArgumentException e) {
		}
	}
	@Test
	@DisplayName("Tester at getTile2-metoden feiler ved negative argumenter")
	public void testGetTile2() {
		try {
			game.getTile2(-5, -8);
			fail("An IllegalArgumentException should be thrown");
		} catch(IllegalArgumentException e) {
		}
	}
	
	@Test
	@DisplayName("Tester at spilltilstanden er riktig ved start av nytt spill")
	public void testGameState() {
		assertFalse(game.isGameOver());
		assertFalse(game.isGameWon());
	}
		
	@Test
	@DisplayName("Tester at det genereres korrekt antall miner")
	public void testGenerateMines() {
		assertEquals(5, game.getNumMines());
		//Counts the number of mines on board
		int count = 0;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if(game.getTile(x, y).isBomb()){
					count ++;
				}
			}
		}
		assertEquals(5, count);
	}
	
	@Test
	@DisplayName("Tester at fillSecondBoard() setter alle felter lik 'covered'")
	public void testFillSecondBoard() {
	//Siden denne metoden kjøres ved instansiering av et board-objekt i @BeforeEach 
	//trenger jeg ikke klargjøre noe før jeg tester.
		for(int y = 0; y < game.getHeight(); y++) {
			for(int x = 0; x < game.getWidth(); x++) {
				assertTrue(game.getTile2(x, y).isCovered());
			}
		}
	}
	
	//Lager en metode for å telle antall miner et felt (x,y) grenser til
	public int countAdjacentMines() {
		int count = 0;
		if(game.getTile(1, 1).isBomb())
			count ++;
		if(game.getTile(1, 2).isBomb())
			count ++;
		if(game.getTile(1, 3).isBomb())
			count ++;
		if(game.getTile(2, 1).isBomb())
			count ++;
		if(game.getTile(2, 3).isBomb())
			count ++;
		if(game.getTile(3, 1).isBomb())
			count ++;
		if(game.getTile(3, 2).isBomb())
			count ++;
		if(game.getTile(3, 3).isBomb())
			count ++;
	
		return count;
	}
	@Test
	@DisplayName("Tester at det genereres tall til brettet på riktig måte")
	public void testPlaceNumbersOnAdjacentMines() {
		/*For å sjekke at metoden har regnet ut riktig antall miner et felt 
		  grenser til, tester jeg et tallfelt (x,y), og teller opp
		  om det er tilsvarende antall miner rundt.
		  Fordi programmet mitt er randomisert, lager jeg en test for hver mulige tallverdi
		  i feltet (x,y) som testes. Dermed vil testen kjøres uavhengig av hvilken verdi det er*/
		
		/* 
		 Med tanke på å sjekke koordinater når man står langs en kant, så er dette tatt hånd om 
		 ved å kjøre kode slik som (hvis koordinatet (x,y) ikke står langs venstre side: Sjekk felt
		 til venstre) 
		 */
		if(game.getTile(2, 2).isEmpty()) {
			assertEquals(0, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isOne()) {
			assertEquals(1, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isTwo()) {
			assertEquals(2, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isThree()) {
			assertEquals(3, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isFour()) {
			assertEquals(4, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isFive()) {
			assertEquals(5, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isSix()) {
			assertEquals(6, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isSeven()) {
			assertEquals(7, countAdjacentMines());
		}
		else if(game.getTile(2, 2).isEight()) {
			assertEquals(8, countAdjacentMines());
		}
	}

	@Test 
	@DisplayName("Sjekker at det hentes riktig verdi fra brett 1 og settes til brett 2")
	public void TestCheckOriginalTile() {		
		/*
		 Her sjekker jeg først at et felt i brett2 er uåpnet (isCovered()),
		 så henter jeg verdien fra brett1 som skal være i det feltet, og 
		 sjekker at verdien i brett2 har blitt endret
		 */
		
		if(game.getTile(0, 0).isCovered()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isCovered());
		}
		else if(game.getTile(0, 0).isOne()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isOne());
		}
		else if(game.getTile(0, 0).isTwo()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isTwo());
		}
		else if(game.getTile(0, 0).isThree()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isThree());
		}
		else if(game.getTile(0, 0).isFour()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isFour());
		}
		else if(game.getTile(0, 0).isFive()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isFive());
		}
		else if(game.getTile(0, 0).isSix()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isSix());
		}
		else if(game.getTile(0, 0).isSeven()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isSeven());
		}
		else if(game.getTile(0, 0).isEight()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isEight());
		}
		else if(game.getTile(0, 0).isEmpty()) {
			assertTrue(game.getTile2(0, 0).isCovered());
			game.checkOriginalTile(0, 0);
			assertTrue(game.getTile2(0, 0).isEmpty());
		}
	}
	
	@Test 
	@DisplayName("Tester onClick-metoden min")
	public void testOnClick() {
	    //Tester at koden feiler hvis man prøver å åpne felt når spillet er over
		if(game.isGameOver() || game.isGameWon()) {
			try {
				game.onClick(0, 0);
			} catch (IllegalStateException e) {
				fail("An IllegalStateException should be throwed");
			}
		}
		if(game.getTile(0, 0).isBomb()) {
			game.onClick(0, 0);
			assertTrue(game.isGameOver());	
		}
	}
	
	@Test
	@DisplayName("Tester onRightClick-metoden min")
	public void testOnRightClick() {
		 //Tester at koden feiler hvis man prøver å høyreklikke på felt når spillet er over
		if(game.isGameOver() || game.isGameWon()) {
			try {
				game.onRightClick(0, 0);
			} catch (IllegalStateException e) {
				fail("An IllegalStateException should be throwed");
			}
		}
		//Tester at det settes flagg dersom feltet er uåpnet:
		if(game.getTile2(0, 0).isCovered()) {
			game.onRightClick(0, 0);
			assertTrue(game.getTile2(0, 0).isFlag());
		//Og at det settes tilbake til uåpnet hvis man høyreklikker igjen
			game.onRightClick(0, 0);
			assertTrue(game.getTile2(0, 0).isCovered());
		}
	}

	@Test
	@DisplayName("Tester hasLost")
	public void testHasLost() {
		assertFalse(game.isGameOver());
		game.hasLost();
		assertTrue(game.isGameOver());
	}
	
	@Test 
	@DisplayName("Tester hasWon")
	public void testHasWon() {
		assertFalse(game.isGameWon());
		game.hasWon();
		assertTrue(game.isGameWon());
	}
	
}
