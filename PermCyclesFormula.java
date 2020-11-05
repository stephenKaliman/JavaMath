package projects;

public class PermCyclesFormula {
	public static int permutationCycles(int n, int k) {
		//returns number of permutations of n with max cycle of size k
		int total = 0;
		for(int m = 1; m<=n/k; m++) {
			int tab = 1;
			for(int i = 0; i<m; i++) {
				tab *= SMath.choose(n-i*k,k)*SMath.factorial(k-1);
			}
			tab /= SMath.factorial(m);
			int tab2 = SMath.factorial(n-m*k);//start with the number of possible permutations of the remaining n-m*k elements
			for(int j = k; j<=n-m*k;j++) {
				tab2 -= permutationCycles(n-m*k, j);//remove the number of these permutations that involve a cycle of size k or greater
			}
			tab *= tab2;
			total += tab;
		}
		return total;
	}
	//note that for k>n/2, this simply returns n!/k.
	//Choose the k out of n elements to be in the main k-cycle, C(n,k) ways to do so
	//permute the k elements into a single cycle, (k-1)! ways to do so
	//permute the remaining (n-k) elements into cycles amongst themselves,  (n-k)! ways to do so
		//note that when permuting the remaining (n-k) elements, we cannot let any cycles greater than size k be created, since we only want permutations of n with maximum appearing cycle size = k
		//also note that when permuting the remaining (n-k) elements, we cannot let any cycles equal to size k be created, since this would cause issues with over-counting
		//there will not be any cycles of size >=k among the permuted (n-k) elements, since k>n/2 --> n-k<n/2<k, so we can permute the remaining (n-k) elements in any way we like, so all (n-k)! possible permutations are allowed
	//C(n,k)*(k-1)!*(n-k)! = n!/k
	
	//the fact that this implementation was my original was honestly kind of lucky. 
	//Mathematically, {Sum _1 ^(k-1) (permutationCycles(n,k))} + {Sum _k ^n (permutationCycles(n,k))} = n!, so {Sum _1 ^(k-1) (permutationCycles(n,k))}  = n! - {Sum _k ^n (permutationCycles(n,k))}, so (permutations of n with largest cycle of size less than k) = n!-(permutations of n with largest cycle of size greater than or equal to k)
	//so using (n-m*k)!-(permutations of n-m*k with largest cycle size k or larger) vs. (permutations of n-m*k with largest cycle size less than k) for tab2 would be the same thing mathematically
	//however, issues can arise when implementing it as code when n=0 or n-m*k = 0, since the code implementation of (permutations of n-m*k (where n-m*k=0) with largest cycle being any natural number) = 0, so 0! - (permutations of 0 elements with largest cycle size k or larger) = 1, which is not equal to (permutations of 0 elements with largest cycle size less than k) = 0
	//this is a discrepancy between the math and the code, but evidently, the original permutationCycles way, using (n-m*k)!-(permutations of n-m*k with largest cycle size k or larger) does not run into this issue
	//I don't know exactly why I chose to do that instead of (permutations of n-m*k with largest cycle size less than k), but I was lucky to have chosen that way since I was able to write the code directly from the formula (picture in projectsDrafts album on phone) in one go
	
	//a couple months later I looked back at the formula and realized that (n-mk)! - (permutations of n-m*k with largest cycle size k or larger) = (permutations of n-m*k with largest cycle size less than k), so I decided to code up the formula using tab2 = (permutations of n-m*k with largest cycle size less than k), in addition to the coding of the formula I had already done above, with tab2  = (n-m*k)! - (permutations of n-m*k with largest cycle size k or greater)
	//I ran into the math-code discrepancy where 0!-(permutations of 0 with largest cycle size k or larger) = (permutations of 0 with largest cycle size less than k) = 1 mathematically, but not in code
	//below are two ways to work around this discrepancy, still using tab2 = (permutations of n-m*k with largest cycle size less than k)
	
	public static int permutationCycles2(int n, int k) {
		//returns number of permutations of n with max cycle of size k, this time using a different approach for the inner loop
		int total = 0;
		for(int m = 1; m<=n/k; m++) {
			int tab = 1;
			for(int i = 0; i<m; i++) {
				tab *= SMath.choose(n-i*k,k)*SMath.factorial(k-1);
			}
			tab /= SMath.factorial(m);
			if(n!=m*k) {// if n = m*k, then there are zero remaining elements, so we've already finished our permutation and we don't need to worry about multiplying it by the permutations of the remaining elements (since there are zero, and there are only 0! = 1 ways of arranging these elements, and we obviously won't get any issues from a cycle of these elements being larger than any k where k>0)
				//notice that because of recursion, we will always get to a point where n-m*k=0. If we do not catch the n-m*k=0 at that point and stop the recursive tab*=tab2 multiplication there, all of the recursion work will be multiplied by zero, and we will just get zeros for everything. So before we even do anything with tab2, we stop to check if n-m*k is equal to zero. If it is, we stop and essentially just multiply tab by 1 then add it to total. If not, we figure out what number tab2 is equal to and then go through with tab *= tab2.
				//this "catching" also helps to make the recursion more efficient, since without it, we would simply be decreasing "k" by one each time and continue the recursion until k=0 (due to the for loop, see two lines below this), as opposed to in the normal permutationCycles implementation above where we simply stop the recursion when n-m*k < k (due to the for loop), since we don't have to worry about any k-size cycles at that point so we can just multiply tab by (n-m*k)!, since no k-or-larger size cycles means nothing to subtract from that.
				int tab2 = 0;//start with zero new permutations
				for(int j = 1; j<k;j++) {
					tab2 += permutationCycles2(n-m*k, j);//add one for each permutation of the remaining n-m*k elements with largest cycles less than k 
				}
				tab *= tab2;
			}
			total += tab;
		}
		return total;
	}
	public static int permutationCycles3(int n, int k) {
		//returns number of permutations of n with max cycle of size k, modified from permutationCycles2 to give a new way of catching the issue with the permutationCycles(0,k)
		if(n==0) {//the total of the permutations of 0 elements has to add up to 0! (just as the total of n elements has to add up to n!)
			if(k==0) {
				return 1;//there is one way to permute zero elements, and there are only size-zero cycles when we do this. This gives us our total of 0! permutations of 0 elements.
			}
			else {
				return 0; //you can't make a cycle of n with k<0 or k>n, so you can't make a cycle of zero with k=/=0
			}
		}
		else {
			if(k<1) {return 0;}//for n=/=0, this will come in handy later, see notes below; also catches negative cycle size error
		}
		int total = 0;
		for(int m = 1; m<=n/k; m++) {
			int tab = 1;
			for(int i = 0; i<m; i++) {
				tab *= SMath.choose(n-i*k,k)*SMath.factorial(k-1);
			}
			tab /= SMath.factorial(m);
			int tab2 = 0;//start with zero new permutations
			for(int j = 0; j<k;j++) {//since we are counting permutationCycles(0,0)=1, we need to start with j=0 so that we can make sure we don't get everything flushed to zero as we discussed in permutationCycles2. This is also where the else{if(k<1){return 0;}} comes in, since k = 0 gives a "divide by zero" error if we don't catch it before the first for loop (for(int m = 1; m<=n/k; m++))
				tab2 += permutationCycles3(n-m*k, j);//add one for each permutation of the remaining n-m*k elements with largest cycles less than k 
			}
			tab *= tab2;
			total+=tab;
			}
		return total;
	}
}
