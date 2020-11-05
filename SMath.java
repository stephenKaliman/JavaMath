package projects;

public class SMath {
	public static int factorial(int num) {
		if(num<0){return 0;}
		else if(num==0) {return 1;}
		else {return num*factorial(num-1);
		}
	}
	public static int fibonacci(int place) {
		if(place==0) return 0;
		else if (place==1) return 1;
		else return fibonacci(place-1)+fibonacci(place-2);
	}
	public static double pseudoMod(double num, double denom) {
		while(num>=denom) {
			num-=denom;
		}
		return num;
	}
	public static int pseudoIntegerDivide(double num, double denom) {
		return (int)((num-pseudoMod(num,denom))/denom);
	}
	public static int floor(double num) {
		return (int)(num-pseudoMod(num,1));
	}
	public static int choose(int n, int k) {
		if(k>n || k<0) return 0;
		return factorial(n)/factorial(k)/factorial(n-k);
	}
}
