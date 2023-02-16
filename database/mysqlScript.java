import java.sql.*;

public class mysqlScript {
    public static void main(String[] args) {
        try
        {
            // create our mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/daw2";
            System.out.println("Hola");
            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, "daw2", "Gimbernat");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT Host, User FROM mysql.user";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                String host = rs.getString("Host");
                String user = rs.getString("User");

                // print the results
                System.out.format("%s, %s\n", host, user);
            }
            st.close();
            conn.close();
            rs.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
