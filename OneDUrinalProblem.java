package projects;
//explicit (exp) and recursive (rec) implementations of formulas to compute how many ways a "people" people can occupy a row of "length" spots (urinals) with at least "spaces" spots between any two people
public class OneDUrinalProblem {
	
	public static int oneDimensionalExp(int length, int spaces,int people) {
		return SMath.factorial(length-spaces*(people-1))/SMath.factorial(people)/SMath.factorial(length-(spaces+1)*(people-1)-1);
	}
	public static int oneDimensionalExp(int length, int people) {
		return oneDimensionalExp(length,1,people);
	}
	public static int oneDimensionalRec(int length) {
		if(length==0)return 1;
		else if(length==1)return 2;
		else return oneDimensionalRec(length-1)+oneDimensionalRec(length-2);
	}
}
