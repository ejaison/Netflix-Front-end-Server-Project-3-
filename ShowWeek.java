//Eric 
//Netflix Web 
//Java 

package servletPackage;

public class ShowWeek {
	private String week;
	private String category;
	private String rank;
	private String showTitle;
	private String seasonTitle;
	private String hrsViewed;
	private String weeksTop10;
	
	//default constructor
	public ShowWeek() {
		week = "not set";
		category = "not set";
		rank = "not set";
		showTitle = "not set";
		seasonTitle = "not set";
		hrsViewed = "not set";
		weeksTop10 = "not set";	
	}
	
	//generic constructor
	public ShowWeek(String w, String c, String r, String t, String t2, String h, String wt) {
		week = w;
		category = c;
		rank = r;
		showTitle = t;
		seasonTitle = t2;
		hrsViewed = h;
		weeksTop10 = wt;
	}
	
	//constructor using showTitle and week
	public ShowWeek( String t, String w) {
		week = w;
		category = getCategory();
		rank = getRank();
		showTitle = t;
		seasonTitle = getSeasonTitle();
		hrsViewed = getHrsViewed();
		weeksTop10 = getWeeksTop10();
	}
	
	//setters and getters
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public String getSeasonTitle() {
		return seasonTitle;
	}

	public void setSeasonTitle(String seasonTitle) {
		this.seasonTitle = seasonTitle;
	}

	public String getHrsViewed() {
		return hrsViewed;
	}

	public void setHrsViewed(String hrsViewed) {
		this.hrsViewed = hrsViewed;
	}

	public String getWeeksTop10() {
		return weeksTop10;
	}

	public void setWeeksTop10(String weeksTop10) {
		this.weeksTop10 = weeksTop10;
	}
	//toString method
	public String toString() {
		return week +", "+category+", "+rank+", "+showTitle+", "+seasonTitle+", "+hrsViewed+", "+weeksTop10+"\n";
	}
	//equals method uses showTitle and week as unique identifier
	public boolean equals(Object o) {
		ShowWeek s = (ShowWeek) o;
		return showTitle.equals(s.getShowTitle()) && week.equals(s.getWeek());
	}
	public boolean isPurged () {
		return getShowTitle().charAt(0)=='*';
	}

}
