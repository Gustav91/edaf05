package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new Main().run(args);
	}

	private void run(String[] args) {
		Map<String, PriorityQueue<Map<Integer, String>>> cities = readInput();

		int spanningDist = MST(cities);

		System.out.println(spanningDist);
	}

	private int MST(Map<String, PriorityQueue<Map<Integer, String>>> cities) {
		int totalDistance = 0;
		String root = "\"San Diego\"";
		ArrayList<String> visitedCities = new ArrayList<String>();
		visitedCities.add(root);
		while(visitedCities.size() < cities.size()){
			PriorityQueue<Map<Integer, String>> minQueue = null;
			String from = "";
			for(String visitedCity : visitedCities){
				PriorityQueue<Map<Integer, String>> currentQueue = cities.get(visitedCity);
				
				boolean firstNull = false;
				if(minQueue == null){
					firstNull = true;
					minQueue = currentQueue;
					from = visitedCity;
				}

				Map<Integer, String> minCity = minQueue.element();
				Map<Integer, String> currentCity = currentQueue.element();

				int minDist = 0;
				for(int dist : minCity.keySet()){
					minDist = dist;
				}
				int currentDist = 0;
				for(int dist : currentCity.keySet()){
					currentDist = dist;
				}
				
				while(!currentQueue.isEmpty()){
					minCity = minQueue.element();
					currentCity = currentQueue.element();
					for(int dist : minCity.keySet()){
						minDist = dist;
					}
					for(int dist : currentCity.keySet()){
						currentDist = dist;
					}
					if(!visitedCities.contains(currentQueue.element().get(currentDist))){
						if(firstNull || currentDist < minDist){
							minQueue = currentQueue;
							from = visitedCity;
						}
						break;
					}
					currentQueue.poll();
				}
			}
			Map<Integer, String> newCity = minQueue.poll();
			int minDist = 0;
			for(int dist : newCity.keySet()){
				minDist = dist;
			}
			totalDistance += minDist;
			visitedCities.add(newCity.get(minDist));
			
			System.out.println(from + " -- " + newCity.get(minDist) + " " + minDist);

		}
		return totalDistance;
	}

	private Map<String, PriorityQueue<Map<Integer, String>>> readInput() {
		String filePath = "/usr/local/cs/edaf05/lab3/";
		String fileName = "USA-highway-miles.in";
		filePath += fileName;

		Comparator<? super Map<Integer, String>> comp = new CompareDists();
		Map<String, PriorityQueue<Map<Integer, String>>> cities = new HashMap<String, PriorityQueue<Map<Integer, String>>>();

		try {
			Scanner scan = new Scanner(new File(filePath));

			while(scan.hasNextLine()){
				String line = scan.nextLine();
				if(!line.contains("[")){
					cities.put(line.substring(0, line.length()-1), new PriorityQueue<Map<Integer, String>>(1,comp));
				}else{
					String[] split = line.split("--");
					String strDist = split[1].split(" \\[")[1];
					strDist = strDist.substring(0, strDist.length()-1);
					int distance = Integer.parseInt(strDist);
					String cityName = split[1].split(" \\[")[0];
					
					Map<Integer, String> city = new HashMap<Integer, String>();
					city.put(distance, cityName);
					cities.get(split[0]).add(city);


					Map<Integer, String> city2 = new HashMap<Integer, String>();
					city2.put(distance, split[0]);
					cities.get(cityName).add(city2);

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cities;
	}
}

