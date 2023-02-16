package com.example.pocketsound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

// Clase audio
public class Audio {
    // Atributos
    private int id;
    private String name;
    private int duration;
    private String path;

    private boolean isPaused;

    // Constructor
    public Audio(int id, String name, int duration, String path) {
        try {
            // Cogemos la clase del JDBC para la conexión MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.path = path;
            this.isPaused = false;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // getAllAudioTracks, devuelve todas las pistas de audio de la base de datos.
    public static ArrayList<Audio> getAllAudioTracks() {
        try {
            // Crear el array list de audios
            ArrayList<Audio> audioList = new ArrayList<>();
            // Preparar el query, crear la conexión y hacer el execute
            String query = "SELECT * FROM audio;";
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/daw2", "daw2", "Gimbernat");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            // Mientras haya resultados:
            while (rs.next()) {
                // Guardar todos los atributos en variables
                int id = rs.getInt("ID");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                String path = rs.getString("path");
                // Crear un nuevo objeto audio
                Audio newAudio = new Audio(id, name, duration, path);
                // Añadirlo a la lista
                audioList.add(newAudio);
            }
            // Cerrar variables
            st.close();
            rs.close();
            // Devolver lista de audios
            return audioList;
        } catch (Exception e) {
            // Excepciones
            System.err.println("Got an exception! ");
            System.err.println("XD");
            System.err.println(e.getMessage());
            return null;
        }
    }

    // insertAudio, inserta en la base de datos un nuevo audio
    public static boolean insertAudio(String name, int duration, String path) {
        // Prepara el query con formato pasandole los diferentes valores
        String query = String.format("INSERT INTO audio VALUES(null, \"%s\", \"%d\", \"%s\");", name, duration, path);
        Connection conn = null;
        // Declaramos la variable rowsAffected
        int rowsAffected;
        try {
            // Creamos la conexión, preparamos el statemente y:
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/daw2", "daw2", "Gimbernat");
            Statement statement = conn.createStatement();
            // si el execute del query es correcto, habrá afectado 1 fila, por lo tanto, rowsAffected == 1
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            // Excepción
            throw new RuntimeException(e);
        }
        // Devolvemos el booleano de rowsAffected != 0 (es decir, si todo ha ido correcto, 1 != 0 por lo tanto true)
        return rowsAffected != 0;
    }


    /*



    Getters y setters de los diferentes atributos:



    */

    public int getId () { return this.id; }

    public String getName () {
        return this.name;
    }

    public int getDuration () {
        return this.duration;
    }

    public String getPath () { return this.path != null ? this.path : null; }

    public boolean isPaused() { return this.isPaused; }
    public void changePauseState() { this.isPaused = !this.isPaused; }
    public void setPath (String path) { this.path = path; }
}
