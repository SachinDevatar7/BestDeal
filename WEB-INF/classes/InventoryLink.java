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

@WebServlet("/InventoryLink")

public class InventoryLink extends HttpServlet {

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
pw.print("<a style='font-size: 24px;'>Product Details which are Currently Available in Store:</a>");
/*pw.print(" <tr> <th>Month</th> <th>Savings</th> </tr>");*/
pw.print("</h2><div class='entry'><table id='bestseller'>");
pw.print(" <tr> <th>Product Name</th> <th>Price</th>  <th>Total Product </th> <th>Available Product </th></tr>");
    try{
      getConnection();
      Statement stme = conn.createStatement();
      String query = "Select * from productdetails";
      ResultSet rs = stme.executeQuery(query);
      while(rs.next()){
        pw.print("<tr>");
    pw.print("<td>");
    pw.print(rs.getString("productName"));
    pw.print("</td>");
        pw.print("<td>");
    pw.print(rs.getString("productPrice"));
    pw.print("</td>");
    pw.print("<td>");
    pw.print(rs.getString("count"));
    pw.print("</td>");
    pw.print("<td>");
    pw.print(rs.getString("AvailableCount"));
    pw.print("</td>");
    pw.print("</tr>");

      }
    }catch(Exception e){
      e.printStackTrace();
      System.out.print(e);
    }
pw.print("</table></div></div></div>");

    pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
pw.print("<a style='font-size: 24px;'>Product Details with Discount Values:</a>");
pw.print("</h2><div class='entry'><table id='bestseller'>");
pw.print(" <tr> <th>Product Name</th> <th>Price</th>  <th>Discount %</th> <th>After Discount</th></tr>");
    try{
      getConnection();
      Statement stme = conn.createStatement();
      String query1 = "Select * from productdetails where productDiscount>0";
      PreparedStatement pst1 = conn.prepareStatement(query1);
      ResultSet rs = pst1.executeQuery();
      while(rs.next()){
        Double afterDiscount = Double.parseDouble(rs.getString("productPrice"))*(1-(Double.parseDouble(rs.getString("productDiscount"))/100));
        afterDiscount = Math.round(afterDiscount*100D)/100D;
        pw.print("<tr>");
    pw.print("<td>");
    pw.print(rs.getString("productName"));
    pw.print("</td>");
        pw.print("<td>");
    pw.print(rs.getString("productPrice"));
    pw.print("</td>");
    pw.print("<td>");
    pw.print(rs.getString("productDiscount"));
    pw.print("&nbsp;%</td>");
        pw.print("<td>");
    pw.print(afterDiscount);
    pw.print("</td>");
    pw.print("</tr>");
      }
    }catch(Exception e){
      e.printStackTrace();
      System.out.print(e);
    }
pw.print("</table></div></div></div>");

pw.print("<div style='margin-top:3%;'>");
pw.print("<a style='font-size: 28px;'><u>For Inventory Bar Chart:</u></a>");
pw.print("<span style='margin-left:5%;'><a href='DataVisualization2'><span class='glyphicon'>Click_here!</span></a></span>");
pw.print("<hr style='margin-top: 4%'>");


    pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
pw.print("<a style='font-size: 24px;'>Product Details Which are on Sale and Manufacturer Rebates:</a>");
pw.print("</h2><div class='entry'><table id='bestseller'>");
pw.print(" <tr> <th>Products on Sale</th> <th>Price</th>  <th>Manufacturer Rebates</th> </tr>");

    try{
      getConnection();
      Statement stme = conn.createStatement();
      String query2 = "Select * from productdetails where sale=?";
      PreparedStatement pst2 = conn.prepareStatement(query2);
      pst2.setString(1,"Yes");
      ResultSet rs = pst2.executeQuery();
      while(rs.next()){
        pw.print("<tr>");
    pw.print("<td>");
    pw.print(rs.getString("productName"));
    pw.print("</td>");
        pw.print("<td>");
    pw.print(rs.getString("productPrice"));
    pw.print("</td>");
   pw.print("<td>");
    pw.print(rs.getString("sale"));
    pw.print("</td>");
    pw.print("</tr>");
      }
    }catch(Exception e){
      System.out.print(e);
    }
pw.print("</table></div></div></div>");




utility.printHtml("Footer.html");
}

protected void doPost(HttpServletRequest request,
HttpServletResponse response) throws ServletException, IOException {

}

}