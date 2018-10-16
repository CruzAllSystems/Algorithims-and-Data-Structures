package tictactoe;

public class TicTacToeBoardUtils_Chavez 
{
	//pre: none
	//post: rv = true <==>  NO_MOVE <= x in movesArray.length < movesArray.length,
	//movesArray index of NO_MOVE > movesArray[index] of x
	public static boolean isWellFormedArray(int[] movesArray) 
	{
		boolean isWellFormedArray = true;
		
		for(int index = 0; index < movesArray.length; index++)
		{
			int currentMove = movesArray[index];
			
			if(currentMove > movesArray.length)
				isWellFormedArray = false;
			
			if(currentMove < 0 && currentMove != TicTacToeBoardImpl_Chavez.NO_MOVE)
				isWellFormedArray = false;
			
			if(currentMove == TicTacToeBoardImpl_Chavez.NO_MOVE)
			{
				for(int index2 = index; index2 < movesArray.length; index2++)
				{
					if(movesArray[index2] != TicTacToeBoardImpl_Chavez.NO_MOVE)
						isWellFormedArray = false;
					
				}
				
			}//end of the NO_MOVE order check
				
			//Checks that post conditions are valid for return value
			
		}//end of the main check loop
		
		return isWellFormedArray;
		
	}//end of the isWellFormedArray method
	
	
	//pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//post: "Converts row and column into a movesArray value for specific encoding scheme"
	public static int convertToMove(int row, int column) 
	{
		assert 0 <= row && row < TicTacToeBoardImpl_Chavez.ROW_COUNT : "Error! Row is invalid!";
		assert 0 <= column && column < TicTacToeBoardImpl_Chavez.COLUMN_COUNT : "Error! Column is invalid!";
		
		int move = 0;
		
		if(row == 0 && column == 1)
			move = 1;
		if(row == 0 && column == 2)
			move = 2;
		if(row == 1 && column == 0)
			move = 3;
		if(row == 1 && column == 1)
			move = 4;
		if(row == 1 && column == 2)
			move = 5;
		if(row == 2 && column == 0)
			move = 6;
		if(row == 2 && column == 1)
			move = 6;
		if(row == 2 && column == 2)
			move = 7;
		
		return move;
		
	}//end of the convertToMove method
	

}//end of the Utilites class
