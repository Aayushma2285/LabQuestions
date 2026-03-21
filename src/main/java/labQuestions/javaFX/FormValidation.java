package labQuestions.javaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class FormValidation extends Application{
    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Personal Details");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
// Local commit
        Label nameLabel = new Label("Name:");
        Label emailLabel = new Label("Email:");
        Label ageLabel = new Label("Age:");
        Label phoneLabel = new Label("Phone:");
        Label addressLabel = new Label("Address:");
        Label genderLabel = new Label("Gender:");
        Label passwordLabel = new Label("Password:");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();
        TextField phoneField = new TextField();
        TextArea addressArea = new TextArea();
        PasswordField passwordField = new PasswordField();

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");

        Button submitBtn = new Button("Submit");
        Label messageLabel = new Label();

        addressArea.setPrefRowCount(3);

        submitBtn.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String ageText = ageField.getText();
            String phone = phoneField.getText();
            String address = addressArea.getText();
            String gender = genderBox.getValue();
            String password = passwordField.getText();

            if (name.isEmpty()) {
                messageLabel.setText("Name cannot be empty");
            } else if (!email.contains("@")) {
                messageLabel.setText("Enter a valid email");
            } else if (!phone.matches("98\\d{8}")) {
                messageLabel.setText("Phone number must start with 98 and be 10 digits");
            } else if (address.isEmpty()) {
                messageLabel.setText("Address cannot be empty");
            } else if (gender == null) {
                messageLabel.setText("Please select gender");
            } else if (password.length() < 4) {
                messageLabel.setText("Password must be at least 4 characters");
            } else {
                try {
                    int age = Integer.parseInt(ageText);
                    if (age <= 0) {
                        messageLabel.setText("Age must be positive");
                    } else {
                        messageLabel.setText("Form submitted successfully");
                    }
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Age must be a number");
                }
            }
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        root.add(titleLabel, 1, 0);

        root.add(nameLabel, 0, 1);
        root.add(nameField, 1, 1);

        root.add(emailLabel, 0, 2);
        root.add(emailField, 1, 2);

        root.add(ageLabel, 0, 3);
        root.add(ageField, 1, 3);

        root.add(phoneLabel, 0, 4);
        root.add(phoneField, 1, 4);

        root.add(addressLabel, 0, 5);
        root.add(addressArea, 1, 5);

        root.add(genderLabel, 0, 6);
        root.add(genderBox, 1, 6);

        root.add(passwordLabel, 0, 7);
        root.add(passwordField, 1, 7);

        root.add(submitBtn, 1, 8);
        root.add(messageLabel, 1, 9);

        Scene scene = new Scene(root, 450, 450);
        stage.setTitle("Personal Details");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
