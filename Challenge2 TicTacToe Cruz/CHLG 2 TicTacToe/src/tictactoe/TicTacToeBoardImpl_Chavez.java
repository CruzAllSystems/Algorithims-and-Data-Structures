package tictactoe;

public class TicTacToeBoardImpl_Chavez implements TicTacToeBoard
{
	public static final int NO_MOVE = -1;
	public static final int NO_MATCH = -1;
	
	protected int [] movesArray;
	
	//Creates an instance of the TicTacToeBoard that is empty
	public TicTacToeBoardImpl_Chavez()
	{
		final int CELL_COUNT = ROW_COUNT * COLUMN_COUNT;
		movesArray = new int [CELL_COUNT];
		
		for(int index = 0; index < CELL_COUNT; index++)
		{
			movesArray[index] = NO_MOVE;
			
		}//end of the populating loop
		
	}//end of the constructor

	//pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//post: rv == null <==> the (row, column) spot on the board is empty
	public Mark getMark(int row, int column) 
	{
		assert 0 <= row && row < ROW_COUNT : "Error! Row is invalid!";
		assert 0 <= column && column < COLUMN_COUNT : "Error! Column is invalid!";
		//Assert statements to verify pre-conditions are true
		
		Mark rv = null;
		
		int move = TicTacToeBoardUtils_Chavez.convertToMove(row, column);
		Integer indexOfMarkedMove = new Integer(0);
		indexOfMarkedMove = null;
		
		for(int index = 0; index < movesArray.length; index++)
			if(movesArray[index] == move)
				indexOfMarkedMove = index;
		
		if(indexOfMarkedMove == null)
			rv = null;
		else if(indexOfMarkedMove % 2 == 0)
			rv = Mark.X;
		else if(indexOfMarkedMove % 2 == 1)
			rv = Mark.O;
		//Checks to see if the move is an X or O based on its position in movesArray
		
		return rv;
		
	}//end of the getMark method

	//pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT, getMark(row, column) == null,
	//!isGameOver()
	//post: "Corresponding position on TicTacToeBoard is set to getTurn();"
	public void setMark(int row, int column) 
	{
		assert 0 <= row && row < ROW_COUNT : "Error! Row is invalid!";
		assert 0 <= column && column < COLUMN_COUNT : "Error! Column is invalid!";
		assert getMark(row, column) == null : "Error! This space is already filled!";
		assert !isGameOver() : "Error! The game is over!";
		//Assert statements to verify pre-conditions are true
		
		int move = TicTacToeBoardUtils_Chavez.convertToMove(row, column);
		
		for(int index = 0; index < movesArray.length; index++)
		{
			if(movesArray[index] == NO_MOVE)
			{
				movesArray[index] = move;
				break;
			}
				
			
		}//end of the mark setter loop
		
		
		
		
	}//end of the setMark method

	//pre: none
	//post: rv == null <==> isGameOver, "number of Marks on board is even" ==> rv == Mark.X,
	//"number of Marks on board is odd" ==> rv == Mark.O
	public Mark getTurn() 
	{
		Mark rv = null;
		
		if(isGameOver())
			return rv;
		
		for(int index = 0; index < movesArray.length; index++)
			if(movesArray[index] == NO_MOVE)
			{
				if(index % 2 == 0)
					rv = Mark.X;		//Checks the turn value based on TicTacToeBoard position
				else
					rv = Mark.O;
				
			}//end of the check forloop
		
		return rv;
		
		
	}//end of the getTurn method

	//pre: TicTacToeBoardUtils_Chavez.isWellFormedArray(movesArray)
	//post: rv == true if getTurn() == null (A gameover condition has been met)
	public boolean isGameOver() 
	{
		assert TicTacToeBoardUtils_Chavez.isWellFormedArray(movesArray) 
		: "Error! TicTacToe Board is invalidly marked!";
		//Assert statement to verify pre-condition is true
		
		boolean isGameOver = true;
		
		
		if(getMark(0,0) == getMark(0,1) && getMark(0,1) == getMark(0,2) && getMark(0,1) != null)
			return isGameOver;
		else if(getMark(1,0) == getMark(1,1) && getMark(1,1) == getMark(1,2) && getMark(1,1) != null)
			return isGameOver;
		else if(getMark(2,0) == getMark(2,1) && getMark(2,1) == getMark(2,2) && getMark(2,1) != null)
			return isGameOver;
		else if(getMark(0,0) == getMark(1,0) && getMark(1,0) == getMark(2,0) && getMark(1,0) != null)
			return isGameOver;
		else if(getMark(0,1) == getMark(1,1) && getMark(1,1) == getMark(2,1) && getMark(1,1) != null)
			return isGameOver;
		else if(getMark(0,2) == getMark(1,2) && getMark(1,2) == getMark(2,2) && getMark(1,2) != null)
			return isGameOver;
		else if(getMark(0,0) == getMark(1,1) && getMark(1,1) == getMark(2,2) && getMark(1,1) != null)
			return isGameOver;
		else if(getMark(0,2) == getMark(1,1) && getMark(1,1) == getMark(2,0) && getMark(1,1) != null)
			return isGameOver;
		//Checks all possible win conditions for TicTacToe accounting for empty spaces
		
		for(int row = 0; row < ROW_COUNT; row++)
		{
			for(int column = 0; column < COLUMN_COUNT; column++)
			{
				if(getMark(row,column) == null)
				{
					isGameOver = false; //Checks if TicTacToeBoard has empty spaces (also checks for a tie)
				}
			}//end of the column check
			
		}//end of the row check
		
		return isGameOver;
	}

	//pre: isGameOver()
	//post: rv == null <==> nobody won
	public Mark getWinner() 
	{
		assert isGameOver() : "Error! The game is not over!";
		
		Mark rv = null; //Marks a tie (Will not change without win condition)
		
		if(getMark(0,0) == getMark(0,1) && getMark(0,1) == getMark(0,2))
			rv = getMark(0,0);
		else if(getMark(1,0) == getMark(1,1) && getMark(1,1) == getMark(1,2))
			rv = getMark(1,0);
		else if(getMark(2,0) == getMark(2,1) && getMark(2,1) == getMark(2,2))
			rv = getMark(2,0);
		else if(getMark(0,0) == getMark(1,0) && getMark(1,0) == getMark(2,0))
			rv = getMark(0,0);
		else if(getMark(0,1) == getMark(1,1) && getMark(1,1) == getMark(2,1))
			rv = getMark(0,1);
		else if(getMark(0,2) == getMark(1,2) && getMark(1,2) == getMark(2,2))
			rv = getMark(0,2);
		else if(getMark(0,0) == getMark(1,1) && getMark(1,1) == getMark(2,2))
			rv = getMark(0,0);
		else if(getMark(0,2) == getMark(1,1) && getMark(1,1) == getMark(2,0))
			rv = getMark(0,2);
		
		//Checks the various win conditions
		
		return rv;
		
	}//end of the getWinner method
	
	public String toString()
	{
		String zero = " ";
		String one =  " ";
		String two = " ";
		String three = " ";
		String four =  " ";
		String five =  " ";
		String six = " ";
		String seven =  " ";
		String eight = " ";
		
		if(getMark(0,0) != null)
			zero = getMark(0,0) + "";
		if(getMark(0,1) != null)
			one = getMark(0,1) + "";
		if(getMark(0,2) != null)
			two = getMark(0,2) + "";
		if(getMark(1,0) != null)
			three = getMark(1,0) + "";
		if(getMark(1,1) != null)
			four = getMark(1,1) + "";
		if(getMark(1,2) != null)
			five = getMark(1,2) + "";
		if(getMark(2,0) != null)
			six = getMark(2,0) + "";
		if(getMark(2,1) != null)
			seven = getMark(2,1) + "";
		if(getMark(2,2) != null)
			eight = getMark(2,2) + "";
		
		
		String r = zero + "|" +  one + "|" +  two + "\n" +"\n"
				+ "-----" + "\n" + three + "|" +  four + "|" +  five + "\n" +"\n"
				+ "-----" + "\n" + six + "|" +  seven + "|" +  eight;
		
		return r;
		
	}//end of the toString method


}
