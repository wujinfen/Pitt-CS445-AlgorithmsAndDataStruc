//class created by Roy Wu - row64
//cs445 assignment 3
//prof: John Ramirez
/*
 * ABOUT:
 * this class utilizes recursive backtracking in Java to solve a word search puzzle.
 * first, the program reads a file to establish a 2d array word search grid
 * then, it handles user interaction by prompting which words to find in the grid
 * it takes the given word and uses methods that use backtracking recursion to find it on
 * the word search
 * 
 * the searchGrid method compiles the solution, it loops through each cell in the grid
 * the findWord method is the primary solution method that uses backtracking recursion to solve the word search
 * the displayResult, displayResultFailed, isPositionValid, and getUpdatedPosition are helper methods
 */

import java.io.*;
import java.util.*;

public class Assig3
{
	public static void main(String [] args) {
		
		//****************************//
		//PT1: CREATING GRID FROM FILE//
		//****************************//
		Scanner scanner = new Scanner(System.in); 
        System.out.println("Please enter grid filename:");
        String fileName = scanner.nextLine();
        
        int R = 0;
        int C = 0;
        char[][] grid = null;
        try (Scanner reader = new Scanner(new File(fileName))) { //NEED TO CHANGE FOR COMPILER
           
            //FILE READER GET ROW AND COLUMN AND CREATE EMPTY GRID
            R = reader.nextInt();
            C = reader.nextInt();
            grid = new char[R][C];
            
            //FILLS IN GRID FROM FILE
            reader.nextLine();
            for (int i = 0; i < R; i++) { 
                String stringToGrid = reader.nextLine(); 
                stringToGrid = stringToGrid.toLowerCase(); 
                char[] charArray = stringToGrid.toCharArray(); 
                for (int j = 0; j < C; j++) { 
                    grid[i][j] = charArray[j];
                }
            }
            
            //PRINT GRID
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
       
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
        
      //******************************//
      //PT2: HANDLING USER INTERACTION//
      //******************************//
        while(true) {
        	System.out.println();
    		System.out.println("Please enter phrase (sep. by single spaces):");
    		String toFind = scanner.nextLine();
    		toFind = toFind.toLowerCase();
    		if (toFind.equals("")) {
    			break;
    		} else {
    			System.out.println("Looking for: " + toFind);
    			String[] pieces = toFind.split(" ");
    			System.out.println("containing " + pieces.length + " words");
    			
    			//SOLVES PUZZLE
    			//NOTE: the solution outputted is the last viable solution in the grid because of stack implementation for visited coordinates
    			//this should still be valid under the rubric and assignment writeup
    			searchGrid(grid, pieces, toFind);  			
    		}
        	
        }
	} //end of main
	
	
    //**********************************************//
    //PT3: RECURSIVE BACKTRACKING AND HELPER METHODS//
    //**********************************************//

	private static void searchGrid(char[][] grid, String[] pieces, String toFind) { 
		boolean found = false;
		int rows = grid.length;
		int cols = grid[0].length;
		Stack<ArrayList> visited = new Stack<ArrayList>(); //keeps track of visited coordinates
		int[] updatedCoords; 
		
		//loop through each cell in grid
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				//tries all directions
				//0: right, 1: down, 2: left, 3: up
				for (int direction = 0; direction < 4; direction++) {
					//gets initial coordinate positions (STEPS: 0)
					updatedCoords = getUpdatedPosition(row, col, 0, direction);
					//uses current position to solve
					if (findWord(grid, pieces, 0, updatedCoords[0], updatedCoords[1], visited, direction)) {
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
			if (found) {
				break;
			}
		}
		
		//prints results
		//if found: prints out capitalized solution board and solution coordinates
		if (found) {
			displayResult(grid, pieces, visited, toFind); 
		} else {
			displayResultFailed(toFind);
		}
	}
	
	
	//if toFind is found, capitalize solution board and get solution coordinates
	@SuppressWarnings("unchecked")
	private static void displayResult(char[][] grid, String[] pieces, Stack<ArrayList> visited, String toFind) { 
		int rows = grid.length;
		int cols = grid[0].length;
		String[] solutionCoordinates = new String[pieces.length];
		
		//create copy of grid
		char[][] solutionBoard = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			System.arraycopy(grid[i], 0, solutionBoard[i], 0, cols);
		}
		//loop through visited stack
		for (int wordIndex = pieces.length - 1; wordIndex >= 0; wordIndex--) {
			for (int charIndex = 0; charIndex < pieces[wordIndex].length(); charIndex++) {
				ArrayList<Integer> coordinate = visited.pop();
				int row = coordinate.get(0);
				int col = coordinate.get(1);
				//capitalizes solution char 
				solutionBoard[row][col] = Character.toUpperCase(solutionBoard[row][col]);
				
				if (charIndex == 0) {
					solutionCoordinates[wordIndex] = ") to (" + row + "," + col + ")";
				}
				if (charIndex == pieces[wordIndex].length() - 1) {
					solutionCoordinates[wordIndex] = pieces[wordIndex] + ": (" + row + "," + col + solutionCoordinates[wordIndex];
				}
			}
		}
		System.out.println("The phrase: " + toFind); 
		System.out.println("was found:");
		for (String coord : solutionCoordinates) {
			System.out.println(coord);
		}
		for (char[] row : solutionBoard) {
			for (char cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//if toFind is not found, print failed output
	private static void displayResultFailed(String toFind) {
		System.out.println("The phrase: " + toFind);
		System.out.println("was not found");
		System.out.println();
	}
	
	
	//RECURSIVE BACKTRACKING METHOD TO FIND WORD
	private static boolean findWord(char[][] grid, String[] pieces, int wordIndex, int row, int col, Stack<ArrayList> visited, int direction) {
		//base case: all words have been found
		if (wordIndex == pieces.length) {
			return true;
		}
		String currentWord = pieces[wordIndex];
		int nextRow = row;
		int nextCol = col;
		//specifies direction and move to next position
		for (int charIndex = 0; charIndex < currentWord.length(); charIndex++) {
			int[] newPosition = getUpdatedPosition(nextRow, nextCol, 1, direction);
			nextRow = newPosition[0];
			nextCol = newPosition[1];
			//checks if new position is valid
			if (isPositionValid(nextRow, nextCol, grid, visited) && grid[nextRow][nextCol] == currentWord.charAt(charIndex)) {
				ArrayList<Integer> newVisited = new ArrayList<>();
				newVisited.add(nextRow);
				newVisited.add(nextCol);
				visited.push(newVisited);
			//if invalid: backtrack
			} else {
				for (int j = 0; j < charIndex; j++) {
					visited.pop();
				}
				return false;
			}
		}
		//tries all possible directions for next word in phrase
		for (int nextDirection = 0; nextDirection < 4; nextDirection++) {
			if (nextDirection != direction - 2 && nextDirection != direction + 2) {
				if (findWord(grid, pieces, wordIndex + 1, nextRow, nextCol, visited, nextDirection)) {
					return true;
				}
			}
		}
		//if cannot find: backtrack
		for (int i = 0; i < currentWord.length(); i++) {
			visited.pop();
		}
		return false;
	}
	
	//checks if position is valid (in bounds and not in visited Stack)
	private static boolean isPositionValid(int row, int col, char[][]grid, Stack<ArrayList> visited) {
		int rows = grid.length;
		int cols = grid[0].length;
		ArrayList<Integer> position = new ArrayList<>();
		position.add(row);
		position.add(col);
		
		return row >= 0 && row < rows && col >= 0 && col < cols && !visited.contains(position);
	}
	
	//gets new coordinates for traversing grid in cardinal direction
	//if called with step is 0: returns current position
	private static int[] getUpdatedPosition(int row, int col, int step, int direction) {
		int newRow = row;
		int newCol = col;
		int[] updatedCoords;
		switch (direction) {
			case 0: //RIGHT
				newCol = col + step;
				break;
			case 1: //DOWN
				newRow = row + step;
				break;
			case 2: //LEFT
				newCol = col - step;
				break;
			case 3: //UP
				newRow = row - step;
				break;
		}
		updatedCoords = new int[]{newRow, newCol};
		return updatedCoords;
	}
	
	}
