package sudoku.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.BitSet;
import java.util.Scanner;

import sudoku.exceptions.WrongCharacterException;


/**
 * This is a programming exercise to create a java based command line tool for validating a standard 9x9 Sudoku puzzle.
 * 
 * There are 2 methods implemented to validate a Sudoku grid:
 * - isValid that is more intuitive, but has worst performance
 * - isValidBitSets - has a better performance
 * 
 * @author Andrzej Dabkowski
 * @version 1.0
 *
 */
public class SudokuValidator {

	public static void main(String[] args) {

		File inputFile = null;
		int[][] grid = null;
		boolean isValid = false;

		if (0 < args.length) {
			inputFile = new File(args[0]);
		} else {
			System.err
					.println("Argument missing. You must specify file name containing Sudoku grid.");
			System.exit(1);
		}

		try {
			// Read a Sudoku grid from file
			grid = readSudokuGridFromFile(inputFile);
			isValid = isValidBitSets(grid);  // isValid(grid);
		} catch (FileNotFoundException e) {
			// 
			System.out.println("INVALID. File not found - " + args[0] + ".");
			System.out.println("STATUS CODE: 2");
			System.exit(2);
		} catch (WrongCharacterException e) {
			System.out.println("INVALID. " + e.getMessage());
			System.out.println("STATUS CODE: 3");
			System.exit(3);
		} catch (Exception e) {
			// 
			System.out
					.println("INVALID. Some problem with reading sudoku grid from file.");
			System.out.println("STATUS CODE: 4");
			e.printStackTrace();			
			System.exit(4);
		}

		
		String finalResult = isValid ? "VALID" : "INVALID";

		System.out.println(finalResult);
		System.out.println("STATUS CODE: " + (isValid ? "0" : "1"));
	}

	/**
	 * Reads a Sudoku solution from the file.
	 * 
	 * @param File input file
	 * 
	 * @return    a grid - 2 dimentional table, of int representing Sudoku grid
	 * @exception FileNotFoundException
	 */
	public static int[][] readSudokuGridFromFile(File inputFile)
			throws FileNotFoundException {
		int[][] grid = null;
		Scanner inputScanner = null;
		try {
			inputScanner = new Scanner(inputFile);
			inputScanner.useDelimiter("[\\s,\r\n]+");

			grid = new int[9][9];
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (inputScanner.hasNextInt()) {
						grid[i][j] = inputScanner.nextInt();
						if (grid[i][j] < 1 || grid[i][j] > 9)
							throw new WrongCharacterException(grid[i][j], i, j);
					}
					else
						throw new WrongCharacterException(inputScanner.next(),
								i, j);
				}
			}
		} finally {
			if (inputScanner != null)
				inputScanner.close();
		}

		return grid;
	}

	/**
	 * Performs a check whether a grid is a valid Sudoku grid.
	 * 
	 * @param grid 
	 * 
	 * @return boolean
	 */	
	public static boolean isValid(int[][] grid) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {				
				if (!isValid(i, j, grid)) {
					// if element [i,j] is not valid then 
					// we have a conclusion, the Sudoku puzzle is not valid.
					return false;
				}
			}
		}
		
		// The whole Sudoku grid is valid
		return true;
	}

	/**
	 *  Performs a check whether an element grid[i][j] is valid in the Sudoku puzzle.
	 *  
	 *  @param i row
	 *  @param j column
	 *  @param   grid 
	 * 
	 * @return boolean
	 */
	private static boolean isValid(int i, int j, int[][] grid) {
		// Checks whether element grid[i][j] is valid in the i-th row
		for (int column = 0; column < 9; column++) {
			if (column != j && grid[i][column] == grid[i][j]) {
				return false;
			}
		}

		// Checks whether element grid[i][j] is valid in the j-th column
		for (int row = 0; row < 9; row++) {
			if (row != i && grid[row][j] == grid[i][j]) {
				return false;
			}
		}

		// Checks whether grid[i][j] is valid in its 3 x 3 box
		for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {
			for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
				if (row != i && col != j && grid[row][col] == grid[i][j]) {
					return false;
				}
			}
		}

		// The current element grid[i][j] is valid.
		return true;
	}
	
    // A Java implementation using bit sets that has a better performance than the 1st one
	public static boolean isValidBitSets(int[][] grid) {
		// Check rows and columns
		for (int i = 0; i < grid.length; i++) {
			BitSet bsRow = new BitSet(9);
			BitSet bsColumn = new BitSet(9);
			for (int j = 0; j < grid[i].length; j++) {
				if (bsRow.get(grid[i][j] - 1) || bsColumn.get(grid[j][i] - 1)) {
					return false;
				} else {
					bsRow.set(grid[i][j] - 1);
					bsColumn.set(grid[j][i] - 1);
				}
			}
		}
		// Check within 3 x 3 grid
		for (int rowOffset = 0; rowOffset < 9; rowOffset += 3) {
			for (int columnOffset = 0; columnOffset < 9; columnOffset += 3) {
				BitSet threeByThree = new BitSet(9);
				for (int i = rowOffset; i < rowOffset + 3; i++) {
					for (int j = columnOffset; j < columnOffset + 3; j++) {						
						if (threeByThree.get(grid[i][j] - 1)) {
							return false;
						} else {
							threeByThree.set(grid[i][j] - 1);
						}
					}
				}
			}
		}
		return true;
	}

}
