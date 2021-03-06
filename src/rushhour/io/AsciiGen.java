package rushhour.io;

import rushhour.core.*;

import java.util.ArrayList;
import java.util.Arrays;


public final class AsciiGen {
	
	final static int len = 6;
	final static int hei = 6;
	//print height
	final static int phei = 8;
	//print length
	final static int plen = 8;
	final static int exitL = 3;

	public static String getGridString(Board board){
		String[] pGrid = getPrintableGrid(board.getGrid());
		String ret = "";
		int i;
		for(i=0; i<pGrid.length-1; i++) {
			ret += pGrid[i] + "\n";
		}
		return ret + pGrid[i];
	}
	
	/**
	 * outputs a string[] of 2D char array
	 * @param inputG char[][] input grid 
	 * @return String[] of grid
	 * @todo this prints the transpose of the board. Probably due to the fact that int[][] is really more like (int[])[] so indices are switched around as you read from outside in. 
	 */
	private static String[] getPrintableGrid(Grid inputG){
		String[] fin = new String[phei]; //6 rows plus ceiling and floors = 8 for now
		//visual delimiters for top and bottom
		fin[0] = " .=============.";
		fin[7] = " `=============`";
		//gets individual lines
		for(int i = 0;i<hei;i++){
			fin[i+1] = extractLine(inputG.getRow(i));
		}
		
		//marking the exit path
		fin[exitL] = fin[exitL].substring(0, 14);
		
		return fin;
	}
	
	/**
	 * extracts visual grid String from char[]
	 * @param line char[]
	 * @return String in visual format
	 */
	
	private static String extractLine(int[] line){
		String t = "|| ";
		for(int i=0;i<len;i++){
			if (line[i]==-1) {
				t = t + "  ";
			}
			else { 
				t = t + symbolList().get(line[i]) + " ";
			}
		}
		t = t + "||";
		return t;
	}
	
	
	/** creates empty char[][] 6x6 currently
	 * @return empty char[len][hei]
	 */
	private static int[][] emptyGrid(){
		int[][] arr = new int[len][hei];
		for(int i=0;i<len;i++){
			for (int j=0;j<hei;j++){
				arr[i][j] = '_';
			}
		}
		
		return arr;
	}
	
	/** for now just adds cars manually by user
	 * code for testing out visual
	 * @param grid char[][] an array to put cars into
	 * @return char[][] with cars added to it
	 */
	//replace arr with internal call? replace xylenhor with Car object call maybe
	private static Grid addCar(Grid grid, int index, Car car){
		grid.set(car.x, car.y, index);
		if (car.horizontal){ 
			grid.set(car.x+1, car.y, index);
			if (car.length > 2){ grid.set(car.x+2, car.y, index); }
		} else { 
			grid.set(car.x, car.y+1, index); 
			if (car.length > 2){ grid.set(car.x, car.y+2, index);  }
		}		
		
		return grid;
	}
	
	private static ArrayList<String> symbolList(){
		//change this to whatever you want
		String symbols = "ABCDEFGHIJKLMNOPQRST";
		String[] symbs = symbols.split("");
		ArrayList<String> symb = new ArrayList<String>();
		
		for(int i = 0; i < symbs.length;i++){
			symb.add(symbs[i]);
		}	
		
		return symb;
	}

}
