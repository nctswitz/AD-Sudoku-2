package sudoku.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

import sudoku.exceptions.WrongCharacterException;

/**
 * These are unit tests for SudokuValidator class.
 * 
 * @author Andrzej Dabkowski
 * @version 1.0
 *
 * @see SudokuValidator
 */
public class SudokuValidatorTest {

	@Test
	public void validSudokuGrid() throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("validSudoku.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertTrue(SudokuValidator.isValid(gridFromFile));
	}
	
	// This Sudoku grid is a reversed grid from the 1st test
	@Test
	public void validSudokuGrid2() throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("validSudoku2.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertTrue(SudokuValidator.isValid(gridFromFile));
	}


	@Test
	public void invalidSudokuGridFromFile() throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("invalidSudoku.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertFalse(SudokuValidator.isValid(gridFromFile));
	}

	@Test
	public void invalidSudoku2GridFromFile() throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("invalidSudoku2.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertFalse(SudokuValidator.isValid(gridFromFile));
	}

	@Test(expected = WrongCharacterException.class)
	public void incorrectCharacterInSudokuGridFromFile()
			throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("incorrectCharacterGrid.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertFalse(SudokuValidator.isValid(gridFromFile));
	}
	
	@Test(expected = WrongCharacterException.class)
	public void incorrectNumber11InSudokuGridFromFile()
			throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("incorrectNumber11Grid.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertFalse(SudokuValidator.isValid(gridFromFile));
	}	

	@Test(expected = FileNotFoundException.class)
	public void nonExistentSudokuGridFile() throws FileNotFoundException {
		File testSudokuGridFile = new File("/" + "nonExistentFile.txt");

		SudokuValidator.readSudokuGridFromFile(testSudokuGridFile);
	}

	@Test(expected = Exception.class)
	public void not9x9SudokuGridFromFile() throws FileNotFoundException {
		File testSudokuGridFile = getTestGridFile("not9x9Grid.txt");

		int[][] gridFromFile = SudokuValidator
				.readSudokuGridFromFile(testSudokuGridFile);
		assertFalse(SudokuValidator.isValid(gridFromFile));
	}

	private File getTestGridFile(String testFileName) {
		assertNotNull("Test file missing", //
				getClass().getResource("/" + testFileName));
		URL url = this.getClass().getResource("/" + testFileName);
		File testSudokuGridFile = new File(url.getFile());

		return testSudokuGridFile;
	}
}
