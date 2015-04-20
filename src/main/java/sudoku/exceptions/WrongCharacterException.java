package sudoku.exceptions;

public class WrongCharacterException extends RuntimeException {
	
	static final long serialVersionUID = 1288938983;
	
	private String phrase;
	private int rowIndex;
	private int columnIndex;

	public WrongCharacterException(String phrase, int rowIndex, int colIndex) {
		this.phrase = phrase;
		this.rowIndex = rowIndex;
		this.columnIndex = colIndex;
	}	
	
	public WrongCharacterException(int numberGreaterThan9, int rowIndex, int colIndex) {
		this( String.valueOf(numberGreaterThan9) , rowIndex, colIndex);
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	@Override
	public String getMessage() {
		return "Wrong character ["+ phrase +"] in the grid (" + (rowIndex + 1) // 
		+  "," + (columnIndex + 1) + "). "  //    
		+ "Only numbers [1-9] are allowed in the sudoku grid.";
	}
}
