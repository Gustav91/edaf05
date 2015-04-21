package lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		String filePath = "/usr/local/cs/edaf05/lab4/";
		String fileName = "a280.tsp";		
		filePath += fileName;

		try {
			Scanner scan = new Scanner(new File(filePath));
			String line;
			do{
				line = scan.nextLine();
			}while(!line.startsWith("NODE"));

			List<Point> points = new LinkedList<Point>();

			line = scan.nextLine();
			while(!line.equals("EOF")){
				String[] data = line.split(" ");
				int indexes[] = {0, 0, 0};
				int varNbr = 0;
				for(int i = 0; i < data.length; i++){
					if(data[i].equals("")){
						indexes[varNbr]++;
					}else{
						varNbr++;
					}
					//System.out.println("..." + data[i] + "...");
				}
				indexes[1] += indexes[0] + 1;
				indexes[2] += indexes[1] + 1;

				//System.out.println("Data size: " + data.length + ", " + indexes[0] + " " + indexes[1] + " " + indexes[2]);
				int name = Integer.parseInt(data[indexes[0]]);
				double x = Double.parseDouble(data[indexes[1]]);
				double y = Double.parseDouble(data[indexes[2]]);

				Point point = new Point(name, x, y);
				points.add(point);

				line = scan.nextLine();
			}

			/*for(int i = 0; i < points.size(); i++){//Prints all the points
				points.get(i).print();
				System.out.println("");
			}*/

			double minDist = calcMinDist(points);
			System.out.println("Min dist: " + minDist);

			closestPair(points);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void closestPair(List<Point> points) {
		//Construct Px, Py
		List<Point> Px = points, Py = points;
		Collections.sort(Px, new ComparatorX());
		Collections.sort(Py, new ComparatorY());	

		/*for(int i = 0; i < points.size(); i++){
			Py.get(i).print();//prints one of the sorted lists
		}*/

		Point[] closestPair = closestPairRec(Px,Py);
	}

	private Point[] closestPairRec(List<Point> px, List<Point> py) {
		Point[] minPair = new Point[2];

		if(px.size() <= 3){
			//return pair with minimum distance
			List<Point> sum = new LinkedList<Point>();
			sum.addAll(px);
			sum.addAll(py);
			double minDist = 1e10;
			for(int i = 0; i < sum.size(); i++){
				for(int j = i + 1; j < sum.size(); j++){
					if(sum.get(i).getName() != sum.get(j).getName()){
						double distance = sum.get(i).distanceTo(sum.get(j));
						if(distance < minDist){
							minDist = distance;
							minPair[0] = sum.get(i);
							minPair[1] = sum.get(j);
						}
					}
				}
			}
			System.out.println(minPair[0].getName() + " " + minPair[1].getName());//---------------------print
			return minPair;
		}

		//Construct Qx, Qy, Rx, Ry
		int mid = px.size() / 2;
		List<Point> Qx = px.subList(0, mid);
		List<Point> Qy = py.subList(0, mid);
		List<Point> Rx = px.subList(mid, px.size());
		List<Point> Ry = py.subList(mid, py.size());


		Point[] q = closestPairRec(Qx, Qy);
		Point[] r = closestPairRec(Rx, Ry);

		double delta = Math.min(q[0].distanceTo(q[1]), r[0].distanceTo(r[1]));
		System.out.println("Delta: " + delta);
		//minPair[0] = new Point(1,1,1);
		//minPair[1] = new Point(1,1,1);
		//----------------------------------------FORTSÄTT HÄÄÄÄR!!!!!!--------------------------------------------
		return minPair;//Should be the correct min (change later)
	}

	private double calcMinDist(List<Point> points) {
		double minDist = 1e10;
		for(int i = 0; i < points.size(); i++){
			for(int j = i + 1; j < points.size(); j++){
				double distance = points.get(i).distanceTo(points.get(j));
				if(distance < minDist){
					minDist = distance;
				}
			}
		}
		return minDist;
	}
}

class Point{
	private int name;
	private double x;
	private double y;
	public Point(int name, double x, double y){
		this.name = name;
		this.x = x;
		this.y = y;
	}
	void print(){
		System.out.print(x + " " + y + "\n");
	}
	double distanceTo(Point other){
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}
	public int getName(){
		return name;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
}

class ComparatorX implements Comparator<Point>{
	@Override
	public int compare(Point p1, Point p2) {
		if(p1.getX() == p2.getX()){
			return 0;
		}
		return p1.getX() < p2.getX() ? -1 : 1;
	}
}

class ComparatorY implements Comparator<Point>{
	@Override
	public int compare(Point p1, Point p2) {
		if(p1.getY() == p2.getY()){
			return 0;
		}
		return p1.getY() < p2.getY() ? -1 : 1;
	}
}
