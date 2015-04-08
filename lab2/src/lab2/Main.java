package lab2;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new Main().run(args);
	}

	void run(String[] args){
		String fileName = args[0];//"words-10.dat";
		
		String fileNameIn = args[1];//"words-10-test.in";
		
		Map<String, ArrayList<LinkedList<String>>> graph = createGraph(fileName);
		BFS(graph, fileNameIn);
	}

	private void BFS(Map<String, ArrayList<LinkedList<String>>> graph, String fileName) {
		String filePath = "";
		if(!fileName.contains("/")){
			filePath = "/usr/local/cs/edaf05/lab2/";
		}
		filePath += fileName;
		
		try {
			Scanner scan = new Scanner(new File(filePath));
			while(scan.hasNextLine()){
				String[] input = scan.nextLine().split(" ");
				if(input[0].equals(input[1])){
					System.out.println("0");
				}else{
					System.out.println(distanceBetween(graph, input[0], input[1]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private int distanceBetween(Map<String, ArrayList<LinkedList<String>>> graph, String from, String to) {
		Map<String, Boolean> discovered = new HashMap<String, Boolean>();
		for(Map.Entry<String, ArrayList<LinkedList<String>>> e : graph.entrySet()){
			if(from.equals(e.getKey())){
				discovered.put(e.getKey(), true);
			}else{
				discovered.put(e.getKey(), false);
			}
		}
		ArrayList<LinkedList<String>> layers = new ArrayList<LinkedList<String>>();
		int layer = 0;
		layers.add(new LinkedList<String>());
		layers.get(layer).add(from);
		while(!layers.get(layer).isEmpty()){
			layers.add(new LinkedList<String>());
			for(String s : layers.get(layer)){
				ArrayList<LinkedList<String>> words = graph.get(s);
				for(int i = 0; i < words.get(1).size(); i++){
					if(!discovered.get(words.get(1).get(i))){
						layers.get(layer+1).add(words.get(1).get(i));
						discovered.put(words.get(1).get(i), true);
					}
					if(to.equals(words.get(1).get(i))){
						return layer + 1;
					}
				}
			}
			layer++;
		}
		return -1;
	}

	private Map<String, ArrayList<LinkedList<String>>> createGraph(String fileName) {
		String filePath = "";
		if(!fileName.contains("/")){
			filePath = "/usr/local/cs/edaf05/lab2/";
		}
		filePath += fileName;
		
		Map<String, ArrayList<LinkedList<String>>> graph = new HashMap<String, ArrayList<LinkedList<String>>>();
		
		try {
			Scanner scan = new Scanner(new File(filePath));
			List<String> words = new ArrayList<String>();
			while(scan.hasNextLine()){
				String word = scan.nextLine();
				words.add(word);
				ArrayList<LinkedList<String>> tempList = new ArrayList<LinkedList<String>>();
				tempList.add(new LinkedList<String>());
				tempList.add(new LinkedList<String>());
				
				graph.put(word, tempList);
			}
			for(int i = 0; i < words.size(); i++){
				String word = words.get(i);
				
				for(int j = 0; j < words.size(); j++){
					String otherWord = words.get(j);
					if(i != j){
						if(wordDirectsTo(word.substring(word.length() - 4), otherWord)){
							ArrayList<LinkedList<String>> valuesTo = graph.get(word);
							valuesTo.get(1).add(otherWord); //Adds to the second list, AKA the TO list

							ArrayList<LinkedList<String>> valuesFrom = graph.get(otherWord);
							valuesFrom.get(0).add(word); //Adds to the first list, AKA the FROM list			
						}
					}
				}
			}
			print(graph, words);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return graph;
	}
	

	private void print(Map<String, ArrayList<LinkedList<String>>> graph, List<String> words) {
		for(int i = 0; i < graph.size(); i++){
			ArrayList<LinkedList<String>> values = graph.get(words.get(i));
			//System.out.print(words.get(i) + " directs to ");
			for(int j = 0; j < values.get(1).size(); j++){
			//	System.out.print(values.get(1).get(j) + " ");
			}
			//System.out.println("");
		}
		//System.out.println("");
		
		for(int i = 0; i < graph.size(); i++){
			ArrayList<LinkedList<String>> values = graph.get(words.get(i));
			//System.out.print(words.get(i) + " comes from ");
			for(int j = 0; j < values.get(0).size(); j++){
			//	System.out.print(values.get(0).get(j) + " ");
			}
			//System.out.println("");
		}
	}

	private boolean wordDirectsTo(String fourLetters, String otherWord) {
		char[] chars = otherWord.toCharArray();
		for(int i = 0; i < fourLetters.length(); i++){
			for(int j = 0; j < otherWord.length(); j++){
				if(fourLetters.charAt(i) == chars[j]){
					chars[j] = '0';
					break;
				}
			}
		}
		int count = 0;
		for(int i = 0; i < otherWord.length(); i++){
			if(chars[i] != '0'){
				count++;
			}
		}
		return count <= 1;
	}
}
