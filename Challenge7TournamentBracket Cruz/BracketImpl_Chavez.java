package tournament;
import java.util.*;

public class BracketImpl_Chavez<P> extends BracketAbstract<P>
{
	public BracketImpl_Chavez(List<P> participantMatchups)
	{
		super(participantMatchups);
		
	}//end of the constructor
	
	//pre: none
	//post: getGroupings(rv).size() == 1
	public int getMaxLevel()
	{
		int participantCount = 0;
		
		for(int index = 0; index < predictions.size(); index++)
		{
			if(predictions.get(index) == null)
				continue;
			else
				participantCount++;
			
		}
		
		return (int) (Math.log10(participantCount) / Math.log10(2));
		//Calculate maxLevel through Log base 2 of participants
		
	}//end of the getMaxLevel method

	//pre: 0 <= level <= getMaxLevel()
	//post: rv != null, !rv.contains(null), rv.contains(s) ==> (s != null),
	//rv.contains(s) ==> (!s.contains(null)), rv.contains(s) ==> (s.size() == 2^(level)),
	//(rv.contains(s) && rv.contains(t)) ==> (s.equals(t) || (s.removeAll(t).size() == s.size())),
	//(rv.contains(s) && level > 0) ==> s.equals(a.addAll(b)) for some a, b in getGroupings(level-1)
	public Set<Set<P>> getGroupings(int level)
	{
		assert 0 <= level && level <= getMaxLevel() : "Error! Level is invalid!";
		//assert to make sure precondition is true
		
		Set<Set<P>> groupings = new HashSet<Set<P>>();
		int participantNumber = (int) Math.pow(2,getMaxLevel());
		int levelParticipants = (int) Math.pow(2,level); //Number of participants in the level given
		int participantCountIndex = participantNumber - 1;
		
		if(level == 0)
			level++;
		//prevents division by 0 due to indexing
		
		for(int index = 0; index < participantNumber / levelParticipants; index++)
		{
			Set<P> grouping = new HashSet<P>();
			
			for(int i2 = 0; i2 < levelParticipants; i2++)
			{
				
				grouping.add(predictions.get(participantCountIndex));
				participantCountIndex++;
				
			}
			
			groupings.add(grouping);
			
		}//end of the groupings making loop
		
		return groupings;
		
	}//end of the getGroupings method

	
	public Set<P> getViableParticipants(Set<P> grouping)
	{
		P [] groupingArray = (P[])grouping.toArray();
		List<P> viableList = new ArrayList<P>();
		Set<P> rv = new HashSet<P>();
		
		for(int index = 0; index < Math.pow(2, getMaxLevel()); index++)
		{
			if(predictions.get(index) == null)
				continue;
			else
				viableList.add(predictions.get(index));
			
			
		}//Documents participants that made it past level 0
		
		//Used to calculate currently viable participants based on duplication in viableList
		int highestCount = 0;
		
		
		for(int index = 0; index < groupingArray.length; index++)
		{
			if(viableList.contains(groupingArray[index]))
			{
				int count = 0;
				P checkValue = groupingArray[index];
				
				for(int index2 = 0; index2 < viableList.size(); index2++)
				{
					if(checkValue == viableList.get(index2))
						count++;
					
					
				}
				
				if(highestCount < count)
					highestCount = count;
				
				
			}
			
		}//end of highestCount assessing loop
		
		for(int index = 0; index < groupingArray.length; index++)
		{
			if(viableList.contains(groupingArray[index]))
			{
				int count = 0;
				P checkValue = groupingArray[index];
				
				for(int index2 = 0; index2 < viableList.size(); index2++)
				{
					if(checkValue == viableList.get(index2))
						count++;
					
					
				}
				
				if(highestCount == count)
					rv.add(groupingArray[index]);
				
				
			}
			
		}//end of viable participants check loop
	
		
		return rv;
		
		
	}//end of the getViableParticipants method
	
	//pre: participant != null, participant is in getGroupings(getMaxLevel()).iterator().next(),
	//0 <= exactWinCount, exactWinCount <= getMaxLevel()
	//post: (0 < level <= exactWinCount())) ==>
	//getViableParticipants(getGrouping(level)).contains(participant),
	//(getViableParticipants(getGrouping(exactWinCount)).contains(t) && 
	//(exactWinCount < level <= getMaxLevel())) ==> !getViableParticipants(getGrouping(level)).contains(t)
	public void setPredictedWinCount(P participant, int exactWinCount)
	{
		assert participant != null : "Error! Participant is null!";
		assert getGroupings(getMaxLevel()).iterator().next().contains(participant);
		assert 0 <= exactWinCount && exactWinCount <= getMaxLevel() : "Error! exactWinCount is invalid!";
		//Asserts to ensure preconditions are true
		
		if(exactWinCount == 0)
			return;
		
		for(int i = 0; i < exactWinCount; i++)
		{
			int predictionBase = getParticipantIndex(participant);
			P setParticipant = predictions.get(predictionBase);
			
			predictions.set((predictionBase + 1) / 2 - 1, setParticipant);
			//Formula used to put participants in the right predicted win positions
			
		}//end of prediction setting loop
		
		
	}//end of the setPredictedWinCount method
	
	//pre: none
	//post: Checks if the bracket e is equal in value to the current bracket instance
	public boolean equals(Object e)
	{
		boolean rv = true;
		
		if(e == null)
			rv = false;
		//Checks if e is null
		
		if(!(e instanceof Bracket))
		{
			rv = false;
	
		}//Checks if e can be typeCast or is a Bracket
		
		
		
		Bracket<P> eThat = (Bracket<P>) e;
		int thisMaxLevel = getMaxLevel();
		int eMaxLevel = eThat.getMaxLevel();
		
		if(thisMaxLevel != eMaxLevel)
		{
			rv = false; //Checks if maxLevel matches
			
		}
		else
		{
			
			for(int index = 0; index < getMaxLevel(); index++)
			{
				Set<Set<P>> thisChecker  = getGroupings(index);
				Set<Set<P>> eChecker  = eThat.getGroupings(index);
				
				if(!(thisChecker.equals(eChecker)))
					rv = false;
				
			}//end of else that checks for matching participants and grouping size
			
		}
		
		
		return rv;
		
	}//end of the equals method
	
	
	//pre: none
	//post: predictions.get(rv) == participant && rv is the first known index, 
	//!predictions.contains(participant) ==> rv = -1
	private int getParticipantIndex(P participant)
	{
		int index = 0;
		
		while(index < predictions.size() &&  predictions.get(index) !=  participant)
		{
			if(predictions.get(index) == null)
			{
				index++;
				continue;
				
			}
			
			if(index == predictions.size() - 1)
				if(predictions.get(index) != participant)
					return -1;
				
			
			index++;
			
		}//end of the finding while loop
			
		
		return index;
		
	}//end of the getParticipantantIndex helper method
	
	

}//end of the BracketImpl class
