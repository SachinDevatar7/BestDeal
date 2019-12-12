import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


@WebServlet("/DataVisualization1")
public class DataVisualization1 extends HttpServlet {

    static DBCollection allReviews;

    static Connection conn = null;
    HttpSession session;
    String username;

    public static void getConnection()
    {

        try
        {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledatabase2?autoReconnect=true&useSSL=false","root","1066");                         
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayPage(request, response, pw);
    }

    protected void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
            throws ServletException, IOException {

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("Pre-Content.html");

        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Bar Chart with Product Name and Total sales:</a></h2>"
                + "<div class='entry'>");

        pw.print("<h3><button id='btnGetChartData'>View Chart</h3>");
        pw.println("<div id='chart_div'></div>");
        pw.println("</div></div></div>");
        pw.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
        pw.println("<script type='text/javascript' src='SalesVisualisation.js'></script>");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            try{
                getConnection();
                Statement stmt = conn.createStatement();
                String query = "Select * from productDetails where AvailableCount>0 order by AvailableCount";
                ResultSet rs = stmt.executeQuery(query);

                JSONArray json = new JSONArray();
                ResultSetMetaData rsmd = rs.getMetaData();

                while(rs.next()){

                    JSONObject obj = new JSONObject();
                    obj.put("product_name",rs.getString("productName"));
                    obj.put("product_quantity",(Integer.parseInt(rs.getString("count"))-Integer.parseInt(rs.getString("AvailableCount"))));
                    json.put(obj);
                }       

                String reportJson = new Gson().toJson(json);
                response.setContentType("application/JSON");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(reportJson);
            }catch(Exception e){
                System.out.print(e);
            }

    }   
}
