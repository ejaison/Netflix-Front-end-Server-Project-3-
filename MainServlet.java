//Eric
//Netflix Web 
//Java 

package servletPackage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Shows allData;
	private ShowWeek w;

	//conncection to the server
	public final static String DB_CONNECTION = "http://Localhost:8081/Project3/MainServlet";       
	/**
	 * @throws MalformedURLException 
	 * @see HttpServlet#HttpServlet()
	 */

	//the path to the data
	public MainServlet() throws MalformedURLException {
		super();
		String loc = this.getServletContext().getResource("djfdsjfkldsa").getPath();
		Shows shows = new Shows ("allData", loc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("username"); 
		request.setAttribute("username",user);
		String passWord = request.getParameter("password"); 	
		request.setAttribute("password",passWord);
		// authenticate user by querying database for the input username and see if the query result matches the 
		// input password

		if(request.getParameter("login")!=null) { 

			java.sql.Connection conn = null;  	
			Statement st = null;	

			try {
				// set up communication
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = java.sql.DriverManager.getConnection(DB_CONNECTION); 
				st = conn.createStatement();		

				// build query
				String toProcess ="SELECT * FROM acedb.users WHERE user_name = \""+user+"\";";
				// run query
				ResultSet rs = st.executeQuery(toProcess); 
				boolean verified = false;
				//process all results from query
				while (rs.next()) {
					// get specific column from result set (password column)
					String password = rs.getString("password");	
					if (password.equals(passWord)) {
						verified = true; 						
					}
				}
				if (!verified) {
					// either username did not exist or passwords didn't match restart
					RequestDispatcher rd=request.getRequestDispatcher("/index.html");   
					rd.forward(request,response);  					
				}
				else {
					// build data for drop down list of patient identifiers and forward
					// to jsp page to display
					String label1="selectionList"; 
					String label1Value = "<select name=\"ids\">";   
					ShowWeek[] myShow = null;
					for (ShowWeek showWeek : myShow) {
						String title = w.getShowTitle();
						label1Value += "<option value=\""+title +"\">"+title+"</option>";  
					}

					label1Value += "</select>"; 
					request.setAttribute(label1,label1Value); 
					RequestDispatcher rd=request.getRequestDispatcher("/onepage.jsp");   
					rd.forward(request,response);  
				}
				if (st != null) { st.close(); }
				if (conn != null) { conn.close(); }

			} catch (Exception e) {
				e.printStackTrace();
			}
			} 
				//setting the paramter 
				else if (request.getParameter("select")!=null){
					request.setAttribute("titleShow","" +w.getShowTitle()); 
					request.setAttribute("showRank","" +w.getRank());  
					request.setAttribute("showHrs","" +w.getHrsViewed()); 
					request.setAttribute("showCat","" +w.getCategory()); 
					RequestDispatcher rd=request.getRequestDispatcher("/secondpage.jsp");   
					rd.forward(request,response); 
				}

			}

			/**
			 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
			 */
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// TODO Auto-generated method stub
				doGet(request, response);
			}
		}
