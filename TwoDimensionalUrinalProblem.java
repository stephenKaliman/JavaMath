package projects;

public class TwoDimensionalUrinalProblem {
	public static int TwoBy(int length) {
		if(length==0) return 1;
		else if(length==1) return 3;
		else return TwoBy(length-1)+2*TwoBy(length-2);
	}
	
	public static int ThreeBy(int length) {
		if(length==0) return 1;
		else if (length==1) return 5;
		else if (length==2) return 11;
		else return 2*ThreeBy(length-1)+3*ThreeBy(length-2)-2*ThreeBy(length-3);
	}
	public static int FourBy(int length) {
		if(length==0)return 1;
		else if(length==1)return 8;
		else {
			int sum = SMath.fibonacci(length+2);
			for(int i =2; i<=length;i++) {
				sum+=SMath.fibonacci(i+1)*FourBy(length-i);
			}
			return FourBy(length-1)+3*FourBy(length-2)+2*sum;
		}
	}
}
