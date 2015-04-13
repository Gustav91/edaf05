package lab3;

import java.util.Comparator;
import java.util.Map;

public class CompareDists implements Comparator<Map<Integer, String>>{

	@Override
	public int compare(Map<Integer, String> city1, Map<Integer, String> city2) {
		
		int distance1 = 0;
		int distance2 = 0;
		
		for(int dist : city1.keySet()){
			distance1 = dist;
		}
		for(int dist : city2.keySet()){
			distance2 = dist;
		}
		if(distance1 < distance2){
			return -1;
		}else if(distance1 > distance2){
			return 1;
		}
		return 0;
	}
}