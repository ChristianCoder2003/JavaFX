import java.sql.*;
public class script {
    public static void main(String[] args) {
        try
        {
            // create our mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/daw2";
            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, "daw2", "Gimbernat");

            String createUserTable = "CREATE TABLE IF NOT EXISTS user (\n" +
                    "    ID INT(5) AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    mail VARCHAR(50),\n" +
                    "    name VARCHAR(20),\n" +
                    "    surnames VARCHAR(40),\n" +
                    "    password VARCHAR(20),\n" +
                    "    photo TEXT\n" +
                    ");";

            String createAudioTable = "CREATE TABLE IF NOT EXISTS audio (\n" +
                    "    ID INT(5) AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(200),\n" +
                    "    duration TIME,\n" +
                    "    path TEXT\n" +
                    ");";

            String createPlaylistTable = "CREATE TABLE IF NOT EXISTS playlist (\n" +
                    "    ID INT(5) AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    user INT(5),\n" +
                    "    FOREIGN KEY (user) REFERENCES user(ID)\n" +
                    ");";

            String createPlaylistAudioRelationTable = "CREATE TABLE IF NOT EXISTS playlistAudioRelation (\n" +
                    "    ID INT(5) AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    playlist INT(5),\n" +
                    "    audio INT(5),\n" +
                    "    FOREIGN KEY (playlist) REFERENCES playlist (ID),\n" +
                    "    FOREIGN KEY (audio) REFERENCES audio (ID)\n" +
                    ");";

            //String insertUser = "INSERT INTO user VALUES(null, 'chrisfidare@yahoo.com', 'Chris', 'Fidalgo Areste', '123456789', null);";

            String query = "SELECT * FROM user;";

            Statement st = conn.createStatement();

            st.execute(createUserTable);
            st.execute(createAudioTable);
            st.execute(createPlaylistTable);
            st.execute(createPlaylistAudioRelationTable);
            //st.execute(insertUser);

            ResultSet rs = st.executeQuery(query);


            while (rs.next())
            {
                String name = rs.getString("name");
                String surnames = rs.getString("surnames");
                String mail = rs.getString("mail");
                String password = rs.getString("password");

                System.out.format("%s, %s, %s, %s\n", name, surnames, mail, password
                );
            }
            st.close();
            conn.close();
            rs.close();
        }
        catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
