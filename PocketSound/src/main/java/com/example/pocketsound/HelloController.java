package com.example.pocketsound;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
// Controlador
public class HelloController {
    // Atributos
    private Stage primaryStage;
    private Thread songPlayedThread;
    @FXML
    private Label welcomeText;

    @FXML
    private VBox songList = null;

    @FXML
    private ScrollPane sPane;

    @FXML
    private Label fileNameLabel;
    @FXML
    private Label fileTypeLabel;

    // Método downloadSongs, coge todas las pistas de audio de la base de datos (con la ayuda de la clase Audio.java), creando un rectángulo con un título y dos botones para cada audio
    @FXML
    protected void downloadSongs() {
        // Ponerle un texto a las etiquetas (haz caso a la primera. Por favor.)
        welcomeText.setText("Francisco si lees esto ponme un 10");
        fileNameLabel.setText("Nombre del archivo: ");
        fileTypeLabel.setText("Extensión del archivo: ");
        try
        {
            // Recogemos todos los audios de la base de datos con la ayuda de la clase Audio
            ArrayList<Audio> audioList = Audio.getAllAudioTracks();
            // Por cada audio:
            for (int i = 0; i < audioList.size(); i++) {
                // Mostramos la información del audio
                System.out.print(audioList.get(i).getId() + " | ");
                System.out.print(audioList.get(i).getName() + " | ");
                System.out.print(audioList.get(i).getDuration() + " | ");
                System.out.println(audioList.get(i).getPath() + " | ");

                // Creamos un stackpane para amontonar elementos unos encima de otros (alinearlos verticalmente)
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(sPane.getWidth()-20, 50);
                stackPane.setPadding(new Insets(50));
                stackPane.setStyle("-fx-background-color: black;");

                // Creamos un rectángulo negro
                Rectangle rectangle = new Rectangle(500, 30);
                rectangle.setWidth(stackPane.getWidth());
                rectangle.setFill(Color.BLACK);
                stackPane.getChildren().add(rectangle);

                // Creamos un HBox para guardar los elementos del rectángulo
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);

                // Creamos y añadimos una etiqueta con el título de la canción
                Label title = new Label(audioList.get(i).getName());
                title.setTextFill(Color.WHITE);
                title.setMinWidth(Region.USE_PREF_SIZE);
                hbox.getChildren().add(title);

                // Dejamos un espacio
                Region spacer = new Region();
                hbox.getChildren().add(spacer);
                hbox.setHgrow(spacer, Priority.ALWAYS);

                // Creamos y añadimos el botón de play, asignándole el método playSong cuando clicas
                Button buttonPlay = new Button("Play");
                buttonPlay.setMaxWidth(Double.MAX_VALUE);
                buttonPlay.setAlignment(Pos.CENTER_RIGHT);
                int finalI = i;
                buttonPlay.setOnAction(event -> {
                    try {
                        playSong(audioList.get(finalI));
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });

                // Creamos y añadimos el botón de pause, asignándole el método pauseSong cuando clicas
                Button buttonPause = new Button("Pause");
                buttonPause.setMaxWidth(Double.MAX_VALUE);
                buttonPause.setAlignment(Pos.CENTER_RIGHT);
                buttonPause.setOnAction(event -> {
                    try {
                        pauseSong(audioList.get(finalI));
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });

                // Añadimos al HBox ambos botones
                hbox.getChildren().add(buttonPlay);
                hbox.getChildren().add(buttonPause);

                // Añadimos el HBox al stackpane
                stackPane.getChildren().add(hbox);

                // Añadimos el stackPane al VBox principal
                songList.getChildren().add(stackPane);
            }
        }
        // Mostrar excepción en caso de error
        catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    // Método playsong, reproduce la canción correspondiente al botón pulsado
    protected void playSong(Audio audio) throws FileNotFoundException, JavaLayerException, InterruptedException {
        // Definimos un audio por defecto si este no tiene un path definido
        if (audio.getPath() == null) audio.setPath("alonso.mp3");
        // Creamos el reproductor
        final Player apl = new Player(new FileInputStream("audios/" + audio.getPath()));
        System.out.println(this.songPlayedThread != null);
        // Si el thread de la canción actual existe (es decir, ya hay una canción reproduciendose), lo interrumpe para crear uno nuevo con la canción seleccionada
        if (this.songPlayedThread != null) restartThread();
        // Generar un nuevo Thread
        generateSongPlayThread(apl, audio);
    }

    // Método pauseSong, pausa la cancion
    protected void pauseSong(Audio audio) throws FileNotFoundException, JavaLayerException, InterruptedException {
        System.out.println(audio.getPath());
        // Si ya está pausada, return
        if (audio.isPaused()) return;
        // Cambia el estado de la canción y pausa el thread
        audio.changePauseState();
        pauseThread();
    }

    // generateSongPlayThread, genera un nuevo Thread para la canción y lo guarda
    protected void generateSongPlayThread(Player apl, Audio audio) throws FileNotFoundException, JavaLayerException, InterruptedException {
        // Genera el nuevo thread
        this.songPlayedThread = new Thread(() -> {
            try {
                // Se reproduce siempre
                while (true) {
                    // Si la canción es pausada y el reproductor difiere del frame 1, para el bucle
                    if (!audio.isPaused() && !apl.play(1)) break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Comienza el thread
        this.songPlayedThread.start();
    }

    // pauseThread, suspende el thread (deprecated por algún motivo que desconozco y parece ser que Francisco también desconoce...)
    protected void pauseThread () {this.songPlayedThread.suspend();}

    // restartThread, interrumpe el Thread (lo para indefinidamente)
    protected void restartThread () {this.songPlayedThread.interrupt();}

    // Método resumeThread, continúa el Thread por donde se había quedado (deprecated de nuevo... El mundo está lleno de misterios...)
    protected void resumeThread () {this.songPlayedThread.resume();}

    // Método selectAudio, selecciona el fichero de audio
    @FXML
    protected void selectAudio() {
        // Objeto FileChooser para seleccionar un fichero
        FileChooser fileChooser = new FileChooser();
        // Abre una nueva ventana para seleccionar el audio
        File selectedFile = fileChooser.showOpenDialog(this.primaryStage);
        // Si ha seleccionado algo:
        if (selectedFile != null) {
            // Muestra los datos
            System.out.println(selectedFile.getName());
            fileNameLabel.setText("Nombre del archivo: " + selectedFile.getName());
            fileTypeLabel.setText("Tipo de archivo: " + getFileExtension(selectedFile));
            // Lo sube a la base de datos
            uploadToDatabase(selectedFile);
        }
    }

    // getFileExtension, para saber la extensión del fichero
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }

    // uploadToDatabase, hace un insert de la clase Audio
    private void uploadToDatabase(File file) {
        Audio.insertAudio(file.getName(), 300, file.getName());
    }

    // Setter del stage
    public void setStage(Stage stage){
        this.primaryStage = stage;
    }
}


