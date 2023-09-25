package minesweeper.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class TileTest {
	
	private Tile tile;

	@BeforeEach
	public void setup() {
		tile = new Tile(0,0);
	}
	
	@Test
	@DisplayName("Test at konstruktør  kaster IllegalArgumentException ved negative verdier")
	public void testConstructor() {
		try {
			Tile tile2 = new Tile(-2,2);
			fail("An IllegalArgumentException should be thrown");
		} catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	@DisplayName("Test at getter for x og y fungerer som de skal")
	public void testGetters() {
		assertEquals(0, tile.getX());
		assertEquals(0, tile.getY());
	}
	
	@Test
	@DisplayName("Test at tile settes til riktig verdi med de ulike setter-metodene")
	public void testAllSetters() {
		tile.setBomb();
		assertTrue(tile.isBomb());
		tile.setCovered();
		assertTrue(tile.isCovered());
		tile.setEmpty();
		assertTrue(tile.isEmpty());
		tile.setFlag();
		assertTrue(tile.isFlag());
		
		tile.setOne();
		assertTrue(tile.isOne());
		tile.setTwo();
		assertTrue(tile.isTwo());
		tile.setThree();
		assertTrue(tile.isThree());
		tile.setFour();
		assertTrue(tile.isFour());
		tile.setFive();
		assertTrue(tile.isFive());
		tile.setSix();
		assertTrue(tile.isSix());
		tile.setSeven();
		assertTrue(tile.isSeven());
		tile.setEight();
		assertTrue(tile.isEight());
	}
}
