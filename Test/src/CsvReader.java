

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
public class CsvReader {

	private CSVReader reader = null;
	// private String [] nextLine=null;
	private List<String[]> rows = null;
	private FileInputStream in = null;
	private String[] columnHeaders = null;
	// private String testSuitesFolder="./src/testSuites2/";
	private String testSuitesFolder = "";
	private String parentFolder = "/testdataFiles/";
	private String filePath = "/home/tsadmin/workspaces/rtc_taf/FrequentzFrameworkUpdated/src/testSuites";
	private String sourceFile = null;
	private String tempFile = "test1.csv";
	private File oldFile = null;
	private int rowIndex = 1;
	private int columnIndex = 0;

	public static void main(String[] args) {
		CsvReader csvReader = new CsvReader();

		try {

			// csvReader.openFile("test2.csv");
			csvReader
					.openFile("/home/tsadmin/Framework_workspace/FrequentzFramework/src/testSuites2/test2.csv");
			System.out.println("no. of rows: " + csvReader.getRowCount());
			System.out.println("no. of columns: " + csvReader.getColumnCount());
			for (int i = 0; i < csvReader.columnHeaders.length; i++) {
				System.out.println(csvReader.columnHeaders[i]);
			}

			String[] row = null;
			for (int rowIndex = 0; rowIndex < csvReader.getRowCount(); rowIndex++) {
				row = csvReader.getRow(rowIndex);
				System.out.println();
				for (int cellIndex = 0; cellIndex < csvReader.getColumnCount(); cellIndex++) {
					System.out.print("  " + row[cellIndex]);
				}
			}

			for (int rowIndex = 1; rowIndex < csvReader.getRowCount(); rowIndex++)

			{
				csvReader.updateRow(rowIndex, "Status", "Pass");
			}

			csvReader.closeFile();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/******************************************************
	 * 
	 * open csv file data file should have same name as testscript append
	 * extension .csv to filename (testScriptName)
	 * 
	 * @throws IOException
	 * 
	 *****************************************************/

	public void openFile(String testsuiteName, String testScriptName)
			throws IOException {
		// /home/tsadmin/Framework_workspace/FrequentzFrameworkUpdated/src/testSuites/eventTestSuite/testdataFiles
		sourceFile = "src/testSuites/" + testsuiteName + parentFolder
				+ testScriptName + ".csv";
		// File f= new File("test.csv");
		// System.out.println(f.getAbsolutePath());
		System.out.println("file location : " + "src/testSuites/"
				+ testsuiteName + parentFolder + testScriptName);
		in = new FileInputStream(testSuitesFolder + sourceFile);
		reader = new CSVReader(new InputStreamReader(in));
		rows = reader.readAll();
		getColumnHeader();

	}

	public void openFile(String fileName) throws IOException {
		sourceFile = fileName;
		in = new FileInputStream(testSuitesFolder + sourceFile);
		reader = new CSVReader(new InputStreamReader(in));
		rows = reader.readAll();
		getColumnHeader();

	}

	/********************************************************
	 * 
	 * This method returns total no. of rows in given csv file
	 * 
	 ********************************************************/
	public int getRowCount() throws IOException {
		int rowCount = 0;
		if (rows != null)
			rowCount = rows.size();
		/*
		 * while ((nextLine = reader.readNext()) != null) { if (nextLine !=
		 * null) { // System.out.println(nextLine[0]); rowCount++; } }
		 */

		return rowCount;
	}

	public void setRowIndex(int rowIndex) {
		try {
			if (rowIndex > getRowCount() || rowIndex <= 0) {
				return;
			}

			else
				this.rowIndex = rowIndex;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/******************************************************
	 * 
	 * Count total no. of Columns Assumption: no. of columns in each row are
	 * same if a cell on a row don't have any value it should be in format " "
	 *****************************************************/
	public int getColumnCount() throws IOException {
		int columnCount = 0;
		if (rows != null)
			columnCount = rows.get(0).length;
		/*
		 * while ((nextLine = reader.readNext()) != null) { if (nextLine !=
		 * null) { columnCount=nextLine.length; break; } }
		 */
		return columnCount;
	}

	/******************************************************
	 * 
	 * get a row at a particular index
	 * 
	 *****************************************************/
	public String[] getRow(int index) throws IOException {
		String[] row = null;
		int rowCount = 0;
		if (rows == null || index > getRowCount() || index < 0)
			return null;
		else {
			row = rows.get(index);
			return row;
		}
		/*
		 * while ((nextLine = reader.readNext()) != null) { if (nextLine !=
		 * null) { rowCount++; if(rowCount==index) row=nextLine; break; } }
		 * return row;
		 */
	}

	public String getCell(String columnName) {

		try {
			columnIndex = getColumnIndex(columnName);
			return getCellValue(rowIndex, columnIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/******************************************************
	 * 
	 * get data present on particular cell (row, column)
	 * 
	 *****************************************************/
	public String getCellValue(int rowIndex, int columnIndex)
			throws IOException {
		String[] row = getRow(rowIndex);
		if (columnIndex > row.length || columnIndex < 0) {
			return "index out of bound";
		} else
			return row[columnIndex];
	}

	/*********************************************
	 * 
	 * This method fetch all the column Names and store them into class variable
	 * 
	 ********************************************/
	private void getColumnHeader() throws IOException {
		columnHeaders = getRow(0);
	}

	/***********************************************
	 * 
	 * This method returns columnIndex with respect to given column Name
	 * 
	 * @param columnName
	 * @return
	 ***********************************************/
	private int getColumnIndex(String columnName) {
		int index = -1;

		for (int i = 0; i < columnHeaders.length; i++) {
			if (columnHeaders[i].equalsIgnoreCase(columnName)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/*************************************************
	 * This method update the particular cell value
	 * 
	 * @param rowIndex
	 * @param columnName
	 * @param value
	 * @throws IOException
	 *************************************************/
	public void updateRow(int rowIndex, String columnName, String value)
			throws IOException {
		String[] row = getRow(rowIndex);
		int columnIndex = getColumnIndex(columnName);
		row[columnIndex] = value;
		rows.set(rowIndex, row);

	}

	/****************************************************
	 * This method create a temporary file write data with updated one in that
	 * file replace the old file with temporary file
	 * 
	 *****************************************************/
	public void closeFile() throws IOException {

		/**************************
		 * 
		 * create temporary file
		 * 
		 **************************/

		/*
		 * FileOutputStream out=new FileOutputStream(filePath+tempFile);
		 * CSVWriter writer = new CSVWriter(new OutputStreamWriter(out), ',',
		 * CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
		 * "\n");
		 */

		/*************************************
		 * 
		 * save updated file as temporary file
		 * 
		 *************************************/

		/*
		 * for(int row=0;row<rows.size();row++) writer.writeNext(rows.get(row));
		 * writer.close(); out.close();
		 */

		reader.close();
		in.close();

		/*******************************************
		 * 
		 * Replace the temporary file with old file
		 * 
		 *******************************************/

		// removeOldFile();
		// renameNewFile();
	}

	private void removeOldFile() {
		oldFile = new File(filePath + sourceFile);
		oldFile.delete();
	}

	private void renameNewFile() {
		File newFile = new File(filePath + tempFile);
		newFile.renameTo(oldFile);

	}

}
