package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new Main().run(args);
	}

	void run(String[] args){
		//Reading the file
		String fileName = args[0];//"sm-friends.in";
		String filePath = "/usr/local/cs/edaf05/lab1/";
		filePath += fileName;
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line;
		do{
			line = sc.nextLine();
		}while(line.startsWith("#"));

		int n = Integer.parseInt(line.substring(2));

		int[][] men = new int[n][n];
		int[][] women = new int[n][n];
		int[][] rankings = new int[n][n];
		int[] proposedTo = new int[n];//index = man, value = index of the woman he should propose to
		int[] pairs = new int[n];
		int[] menToWomen = new int[n];

		String[] menNames = new String[n];
		String[] womenNames = new String[n];

		LinkedList<Integer> freeMen = new LinkedList<Integer>();

		for(int i = 0; i < n; i++){
			menNames[i] = sc.nextLine().split(" ")[1];
			womenNames[i] = sc.nextLine().split(" ")[1];
		}
		sc.nextLine(); //Moves past the empty line
		for(int i = 0; i < n; i++){
			String[] menPref = sc.nextLine().split(" ");
			String[] womenPref = sc.nextLine().split(" ");
			for(int j = 0; j < n; j++){
				men[i][j] = Integer.parseInt(menPref[j + 1]) / 2 - 1; 
				women[i][j] = Integer.parseInt(womenPref[j + 1]) / 2;
				rankings[i][women[i][j]] = j;
			}
			pairs[i] = -1;//0 = free, index of pairs = the woman, the value at the index = the man
			proposedTo[i] = 0;
			freeMen.add(i);
		}


		while(!freeMen.isEmpty() && proposedTo[freeMen.element()] != n){
			int man = freeMen.getFirst();
			for(int i = proposedTo[man]; i < n; i++){
				int woman = men[man][i];
				if(pairs[woman] == -1){//the woman is free
					pairs[woman] = man;
					menToWomen[man] = woman;
					freeMen.removeFirst();
					break;
				}else{
					int currentMan = pairs[woman];
					if(rankings[woman][currentMan] > rankings[woman][man]){//she prefers the new man
						freeMen.addLast(pairs[woman]);
						pairs[woman] = man;
						menToWomen[man] = woman;
						freeMen.removeFirst();
						break;
					}
				}
				proposedTo[man]++;
			}		
		}

		for(int i = 0; i < n; i++){
			System.out.println(menNames[i] + " -- " + womenNames[menToWomen[i]]);
		}
	}
}
