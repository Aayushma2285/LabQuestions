package labQuestions.javaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileChooserApp extends Application{
    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Text File Viewer");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button chooseButton = new Button("Choose Text File");
        Label statusLabel = new Label("No file selected");
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefHeight(300);

        chooseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Text File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );

            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                statusLabel.setText("Selected File: " + file.getName());

                try {
                    Scanner sc = new Scanner(file);
                    StringBuilder content = new StringBuilder();

                    while (sc.hasNextLine()) {
                        content.append(sc.nextLine()).append("\n");
                    }

                    textArea.setText(content.toString());
                    sc.close();

                } catch (FileNotFoundException ex) {
                    textArea.setText("Error reading file.");
                }
            } else {
                statusLabel.setText("No file selected");
                textArea.setText("");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(titleLabel, chooseButton, statusLabel, textArea);

        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("File Chooser Based Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
