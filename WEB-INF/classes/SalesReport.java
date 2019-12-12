import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.*;
import java.util.*;

@WebServlet("/SalesReport")

public class SalesReport extends HttpServlet {

  static Connection conn = null;

  public static void getConnection()
  {

    try
    {
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledatabase2","root","1066");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  protected void doGet(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
    String name = "Trending";


    Utilities utility = new Utilities(request, pw);
    utility.printHtml("Header.html");
    utility.printHtml("LeftNavigationBar.html");
    pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
    pw.print("<a style='font-size: 24px;'>How many items of every Product Sold and Left:</a>");
    pw.print("</h2><div class='entry'><table id='bestseller'>");

    pw.print(" <tr> <th>Product Name</th> <th>Price </th>  <th>Total Quantity </th><th>quantity sold</th> <th>Quantity Left </th>  <th>Total Revenue</th></tr>");
    try{
      getConnection();
      Statement stme = conn.createStatement();
      String query = "Select * from productdetails";
      ResultSet rs = stme.executeQuery(query);
      while(rs.next()){
        Integer quantityLeftOut = Integer.parseInt(rs.getString("AvailableCount"));
Integer quantitySold = 10 - quantityLeftOut;
Double totalRev = quantitySold * Double.parseDouble(rs.getString("productPrice"));
totalRev = Math.round(totalRev*100D)/100D;
        /*int productsLeft = 15-Integer.parseInt(rs.getString("count"));*/
        pw.print("<tr>");
        pw.print("<td>");
        pw.print(rs.getString("productName"));
        pw.print("</td>");
        pw.print("<td>");
        pw.print(rs.getString("productPrice"));
        pw.print("</td>");
        /*pw.print("<td>");
        pw.print("productsLeft");
        pw.print("</td>");*/
        pw.print("<td>");
        pw.print(rs.getString("count"));
        pw.print("</td>");
        pw.print("<td>");
        pw.print(quantitySold);
        pw.print("</td>");
        pw.print("<td>");
        pw.print(rs.getString("AvailableCount"));
        pw.print("</td>");
        pw.print("<td>");
        pw.print(totalRev);
        pw.print("</td>");
        pw.print("</tr>");
      }
    }catch(Exception e){
      e.printStackTrace();
      System.out.print(e);
    }
    pw.print("</table></div></div></div>");

/*pw.print("<div style='margin-top:3%;'>");
pw.print("<a style='font-size: 28px;'><u>Bar Chart for Sales Report:</u></a>");
pw.print("<span style='margin-left:5%;'><a href='DataVisualization1'><span class='glyphicon'>Click_here!</span></a></span>");
pw.print("<hr style='margin-top: 4%'>");*/


    pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
    pw.print("<a style='font-size: 24px;'>Total Daily Sales Transactions:</a>");
    pw.print("</h2><div class='entry'><table id='bestseller'>");
    pw.print(" <tr> <th>Sold Date</th> <th>Total Quantity </th>  <th>Total Sales</th> </tr>");



    try{
      getConnection();
      Statement stme = conn.createStatement();
      String query2 = "select count(*) as count, DATE_FORMAT(orderDate, '%e %b, %Y') as date, sum(orderPrice) as totalPrice from customerorders group by orderDate";
      ResultSet rs = stme.executeQuery(query2);
      while(rs.next()){
        Double price = Double.parseDouble(rs.getString("totalPrice"));
Double totalRevenue = Math.round(price*100D)/100D;
        pw.print("<tr>");
        pw.print("<td>");
        pw.print(rs.getString("date"));
        pw.print("</td>");
        pw.print("<td>");
        pw.print(rs.getString("count"));
        pw.print("</td>");
        pw.print("<td>");
        pw.print(totalRevenue);
        pw.print("</td>");
        pw.print("</tr>");
      }
    }catch(Exception e){
      e.printStackTrace();
      System.out.print(e);
    }
    pw.print("</table></div></div></div>");

     pw.print("<div style='margin-top:3%;'>");
pw.print("<a style='font-size: 28px;'><u>Bar Chart for Sales Report:</u></a>");
pw.print("<span style='margin-left:5%;'><a href='DataVisualization1'><span class='glyphicon'>Click_here!</span></a></span>");
pw.print("<hr style='margin-top: 4%'>");

    utility.printHtml("Footer.html");
  }

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

  }

}
