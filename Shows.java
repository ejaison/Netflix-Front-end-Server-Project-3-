//Eric Jaison 
//Netflix Web 
//Java 
//Dr. Doderer
//Nov 15, 2022

package servletPackage;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Shows {
	private ArrayList<ShowWeek> showWeeks;
	private String fileName;
	private static Random generator;
	public Shows() {
		showWeeks = new ArrayList<ShowWeek>();
		fileName = null;
		generator = new Random( );
	}

	public Shows( String ln, String fn) {
		this();
		fileName = fn;
		readFile();
	}

	//adds a showWeek to the list
	public void addShowWeek(ShowWeek s) {
		showWeeks.add(s);
	}

	//find method. accepts showTitle and week
	public ShowWeek find(String t, String w) {
		int index = showWeeks.indexOf(new ShowWeek(t,w));
		if(index == -1) {
			return null;
		}
		return showWeeks.get(index);
	}

	//get random ShowWeek 
	public ShowWeek randomShow(){
		while (true) {
			int index = generator.nextInt(0,showWeeks.size());
			ShowWeek sw = showWeeks.get(index);
			if (!sw.isPurged())
			return sw;
		}
	}

	//gets all ShowWeek(s) for a given week
	public ArrayList<ShowWeek> getOneWeek(String w){
		ArrayList<ShowWeek> weekList = new ArrayList<ShowWeek>();
		for (ShowWeek s : showWeeks) {
			if(s.getWeek().equals(w)) {
				weekList.add(s);
			}
		}
		return weekList;
	}
	
	//gets all showWeeks for a given title
	public ArrayList<ShowWeek> getAllWeeks(String t){
		ArrayList<ShowWeek> weekList = new ArrayList<ShowWeek>();
		for (ShowWeek s : showWeeks) {
			if(s.getShowTitle().equals(t)) {
				weekList.add(s);
			}
		}
		return weekList;
	}

	//suggest one ShowWeek based on one watched show  
	public ShowWeek suggestShow(String t, String w){
		return randomShow();
	}

	//suggest 5 shows based on a list of previously watched shows (watchList)
	public ArrayList<ShowWeek> suggestShowTrend(Shows watchList){
		ArrayList<ShowWeek> suggestedShows = new ArrayList<ShowWeek>();
		for(int i=0;i<5;i++) {
			suggestedShows.add(randomShow());
		}
		return suggestedShows;
	}

	//purge show: adds a '*' to show title making it not match for equals method
	public void purgeShow(String t) {
		for (ShowWeek s : showWeeks) {
			if(s.getShowTitle().equals(t)) {
				s.setShowTitle("*"+t);
			}
		}
	}

	//unpurge show removes the '*' added by purgeShow
	public void unPurgeShow(String t) {
		for (ShowWeek s : showWeeks) {
			if(s.getShowTitle().equals("*"+t)) {
				s.setShowTitle(t);
			}
		}
	}

	//check if show title is purged
	public boolean purgeCheck(String showTitle) {
		boolean isPurged = false;
		char tester = showTitle.charAt(0);
		if((tester == '*')){
			isPurged = true;
		}
		return isPurged;
	}

	//creates a new list based on criteria(weeksTop10) of top shows
	public ArrayList<ShowWeek> getTopShows(){
		ArrayList<ShowWeek> topList = new ArrayList<ShowWeek>();
		for (ShowWeek s : showWeeks) {
			if(s.getWeeksTop10().equals("20")) {
				topList.add(s);
			}
		}
		return topList;
	}

	//tostring method with purgeCheck
	public String toString() {
		// returns a string representation of this showWeeks
		String toReturn = "";
		for (ShowWeek s : showWeeks) {
			if(purgeCheck(s.getShowTitle())== true)
				continue;
			toReturn += s.toString();
		}	
		return toReturn;
	}

	//file stuff reworked
	private void readFile () {
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String week = null;
			while ((week = lineReader.readLine())!=null) {
				String category = lineReader.readLine();
				String rank = lineReader.readLine();
				String showTitle = lineReader.readLine();
				String seasonTitle = lineReader.readLine();
				String hrsViewed = lineReader.readLine();
				String weeksTop10 = lineReader.readLine();
				addShowWeek(new ShowWeek(week, category,rank,showTitle,seasonTitle,hrsViewed,weeksTop10));

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("there was a problem with the file reader, try different read type.");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName.substring(1))));
				String week = null;
				while ((week = lineReader.readLine())!=null) {
					//String week = lineReader.readLine();
					String category = lineReader.readLine();
					String rank = lineReader.readLine();
					String showTitle = lineReader.readLine();
					String seasonTitle = lineReader.readLine();
					String hrsViewed = lineReader.readLine();
					String weeksTop10 = lineReader.readLine();
					addShowWeek(new ShowWeek(week, category,rank,showTitle,seasonTitle,hrsViewed,weeksTop10));
				}				
			} catch (Exception e2) {
				e2.printStackTrace();
				System.err.println("there was a problem with the file reader, try again.  either no such file or format error");
			} finally {
				if (lineReader != null)
					try {
						lineReader.close();
					} catch (IOException e2) {
						System.err.println("could not close BufferedReader");
					}
			}			
		} finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("could not close BufferedReader");
				}
		}
	} // end of readFile method	

	public void writeFile () {
		// overloaded method: this calls doWrite with file used to read data
		// use this for saving data between runs
		doWrite(fileName);
	} // end of writeFile method

	public void writeFile(String altFileName) {
		// overloaded method: this calls doWrite with different file name 
		// use this for testing write
		doWrite(altFileName);		
	}// end of writeFile method

	private void doWrite(String fn) {
		// this method writes all of the data in the showWeek array to a file
		try
		{
			FileWriter fw = new FileWriter(fn);
			BufferedWriter myOutfile = new BufferedWriter(fw);			
			for (ShowWeek s : showWeeks) {
				myOutfile.write(s.getWeek()+"\n");
				myOutfile.write(s.getCategory()+"\n");
				myOutfile.write(s.getRank()+"\n");
				myOutfile.write(s.getShowTitle()+"\n");
				myOutfile.write(s.getSeasonTitle()+"\n");
				myOutfile.write(s.getHrsViewed()+"\n");
				myOutfile.write(s.getWeeksTop10()+"\n");
			}
			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Didn't save to " + fn);
		}
	}	

}
