package org.example.javafxreg;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationForm extends Application {

    private static final String NAME_REGEX = "^[A-Za-z]{2,25}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@farmingdale\\.edu$";
    private static final String DOB_REGEX = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/([12][0-9]{3})$";
    private static final String ZIP_REGEX = "^[0-9]{5}$";

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField dobField;
    private TextField zipCodeField;
    private Button addButton;

    private Label firstNameValidation;
    private Label lastNameValidation;
    private Label emailValidation;
    private Label dobValidation;
    private Label zipValidation;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration");

        // Create main container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Text headerText = new Text("Create Account");
        headerText.setFont(Font.font("Helvetica", 24));
        headerText.setFill(Color.web("#2c3e50"));

        // Form container
        VBox formContainer = new VBox(15);
        formContainer.setMaxWidth(400);
        formContainer.setStyle("-fx-background-color: white; " +
                "-fx-padding: 30px; " +
                "-fx-background-radius: 10px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        initializeFields();

        // Add form fields
        formContainer.getChildren().addAll(
                createFormField("First Name", firstNameField, firstNameValidation),
                createFormField("Last Name", lastNameField, lastNameValidation),
                createFormField("Email", emailField, emailValidation),
                createFormField("Date of Birth", dobField, dobValidation),
                createFormField("Zip Code", zipCodeField, zipValidation)
        );

        // Style the add button
        addButton.setStyle("-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 12px 30px; " +
                "-fx-background-radius: 5px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;");

        HBox buttonContainer = new HBox(addButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));

        mainContainer.getChildren().addAll(headerText, formContainer, buttonContainer);

        Scene scene = new Scene(mainContainer, 500, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

        setupValidation();
    }

    private void initializeFields() {
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        dobField = new TextField();
        zipCodeField = new TextField();

        firstNameValidation = new Label();
        lastNameValidation = new Label();
        emailValidation = new Label();
        dobValidation = new Label();
        zipValidation = new Label();

        addButton = new Button("Add");
        addButton.setDisable(true);

        // Set field styles and prompts
        TextField[] fields = {firstNameField, lastNameField, emailField, dobField, zipCodeField};
        String[] prompts = {"Enter First Name", "Enter Last Name", "Enter Farmingdale Email",
                "MM/DD/YYYY", "5-digit Zip Code"};

        for (int i = 0; i < fields.length; i++) {
            fields[i].setPromptText(prompts[i]);
            fields[i].setStyle("-fx-background-color: #f8f9fa; " +
                    "-fx-padding: 12px; " +
                    "-fx-background-radius: 5px; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-border-color: #e0e0e0; " +
                    "-fx-border-width: 1px; " +
                    "-fx-font-size: 14px;");
        }
    }

    private VBox createFormField(String labelText, TextField field, Label validationLabel) {
        VBox fieldContainer = new VBox(5);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: #2c3e50; " +
                "-fx-font-weight: bold;");

        validationLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-padding: 8px 0;");

        HBox inputContainer = new HBox(10);
        inputContainer.getChildren().addAll(field, validationLabel);

        fieldContainer.getChildren().addAll(label, inputContainer);
        return fieldContainer;
    }

    private void setupValidation() {
        // First Name validation
        firstNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(firstNameField, NAME_REGEX, firstNameValidation,
                        "First name must be 2-25 characters long");
            }
        });

        // Last Name validation
        lastNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(lastNameField, NAME_REGEX, lastNameValidation,
                        "Last name must be 2-25 characters long");
            }
        });

        // Email validation
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(emailField, EMAIL_REGEX, emailValidation,
                        "Must be a valid Farmingdale email address");
            }
        });

        // DOB validation
        dobField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                if (dobField.getText().matches(DOB_REGEX) && isValidDate(dobField.getText())) {
                    setValidStyle(dobField, dobValidation, "Valid date");
                } else {
                    setInvalidStyle(dobField, dobValidation, "Use format MM/DD/YYYY");
                }
            }
        });

        // Zip Code validation
        zipCodeField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(zipCodeField, ZIP_REGEX, zipValidation,
                        "Must be a 5-digit number");
            }
        });

        // Add button handler
        addButton.setOnAction(e -> {
            Stage successStage = new Stage();
            Label successLabel = new Label("Registration Successful!");
            successLabel.setStyle("-fx-font-size: 18px; -fx-padding: 20px;");

            VBox successBox = new VBox(successLabel);
            successBox.setAlignment(Pos.CENTER);
            successBox.setStyle("-fx-background-color: white;");

            Scene successScene = new Scene(successBox, 300, 200);
            successStage.setScene(successScene);
            successStage.show();

            ((Stage) addButton.getScene().getWindow()).close();
        });


        // Add listeners for form validation
        TextField[] fields = {firstNameField, lastNameField, emailField, dobField, zipCodeField};
        for (TextField field : fields) {
            field.textProperty().addListener((obs, old, newVal) -> checkFormValidity());
        }
    }
    /**
     * Validates the text field contents against a specified regex pattern.
     * This method performs validation and updates the UI to provide immediate
     * response to the user.
     *
     * @param field The TextField component to be validated
     * @param regex The regex pattern to validate against
     * @param validationLabel The Label component used to display validation messages
     * @param errorMessage The message to display when validation fails
     * @throws IllegalArgumentException if any of the parameters are null
     * @see java.util.regex.Pattern
     */
    private void validateField(TextField field, String regex, Label validationLabel, String errorMessage) {
        if (field.getText().matches(regex)) {
            setValidStyle(field, validationLabel, "Valid");
        } else {
            setInvalidStyle(field, validationLabel, errorMessage);
        }
    }


    private void setValidStyle(TextField field, Label validationLabel, String message) {
        field.setStyle(field.getStyle() + "-fx-border-color: #2ecc71;");
        validationLabel.setText("✓");
        validationLabel.setTextFill(Color.web("#2ecc71"));
    }

    private void setInvalidStyle(TextField field, Label validationLabel, String message) {
        field.setStyle(field.getStyle() + "-fx-border-color: #e74c3c;");
        validationLabel.setText("✗");
        validationLabel.setTextFill(Color.web("#e74c3c"));
    }

    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void checkFormValidity() {
        boolean isValid = firstNameField.getText().matches(NAME_REGEX) &&
                lastNameField.getText().matches(NAME_REGEX) &&
                emailField.getText().matches(EMAIL_REGEX) &&
                dobField.getText().matches(DOB_REGEX) &&
                isValidDate(dobField.getText()) &&
                zipCodeField.getText().matches(ZIP_REGEX);

        addButton.setDisable(!isValid);

        if (isValid) {
            addButton.setStyle("-fx-background-color: #3498db;");
        } else {
            addButton.setStyle("-fx-background-color: #bdc3c7;");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}