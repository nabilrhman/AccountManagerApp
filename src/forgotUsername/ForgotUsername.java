package forgotUsername;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserAccount;
import model.UserAccountManager;
import validate.InputValidator;

/**
 * @author Edgar Sosa
 */
public class ForgotUsername {

    private boolean lockedOut = false;
    private int failedLoginCount = 0;
    private UserAccount account;

    private UserAccountManager accountManager;
    private GridPane gridPaneForgotUsername;
    private InputValidator validator;

    public TextField firstNameTextField; // First name text field.
    public TextField lastNameTextField; // Last name text field.
    public TextField emailTextField;
    public DatePicker birthDatePicker;
    public Button getUsernameButton;
    public Button goBackButton;

    public Text firstNameValidationText; // First name validation text.
    public Text lastNameValidationText; // Last name validation text.
    public Text emailValidationText;
    public Text birthDateValidationText;

    private Boolean isValidFirstName = false;
    private Boolean isValidLastName = false;
    private Boolean isValidEmail = false;
    private Boolean isValidBirthDate = false;

    public ForgotUsername(Stage currentStage, Scene loginScene,
                          UserAccountManager accountManager) {
        validator = new InputValidator();
        this.accountManager = accountManager;

        gridPaneForgotUsername = new GridPane();
        gridPaneForgotUsername.setAlignment(Pos.CENTER);
        gridPaneForgotUsername.setHgap(10);
        gridPaneForgotUsername.setVgap(10);
        gridPaneForgotUsername.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("FORGOT USERNAME");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPaneForgotUsername.add(sceneTitle, 0, 0, 2, 1);

        // First name field functionality.
        Label firstName = new Label("First Name:"); // First name label.
        firstNameTextField = new TextField(); // Initializes firstNameTextField.
        firstNameValidationText = new Text(" ");
        firstNameValidationText.setFont(Font.font("Tahoma", FontWeight.
                NORMAL, 10));
        firstNameValidationText.setFill(Color.RED);
        gridPaneForgotUsername.add(firstName, 0, 1);
        gridPaneForgotUsername.add(firstNameTextField, 1, 1);
        gridPaneForgotUsername.add(firstNameValidationText, 1, 2);

        // Last name field functionality.
        Label lastNameLabel = new Label("Last Name:");
        lastNameTextField = new TextField();
        lastNameValidationText = new Text(" ");
        lastNameValidationText.setFont(Font.font("Tahoma", FontWeight.
                NORMAL, 10));
        lastNameValidationText.setFill(Color.RED);
        gridPaneForgotUsername.add(lastNameLabel, 0, 3);
        gridPaneForgotUsername.add(lastNameTextField, 1, 3);
        gridPaneForgotUsername.add(lastNameValidationText, 1, 4);

        // Email field functionality.
        Label emailLabel = new Label("Email:");
        emailValidationText = new Text(" ");
        emailValidationText.setFont(Font.font("Tahoma", FontWeight.NORMAL,
                10));
        emailValidationText.setFill(Color.RED);
        emailTextField = new TextField();
        gridPaneForgotUsername.add(emailLabel, 0, 5);
        gridPaneForgotUsername.add(emailTextField, 1, 5);
        gridPaneForgotUsername.add(emailValidationText, 1, 6);

        // Birth date field functionality.
        Label birthDateLabel = new Label("Birth Date:");
        birthDateValidationText = new Text(" ");
        birthDateValidationText.setFont(Font.font("Tahoma", FontWeight
                .NORMAL, 10));
        birthDateValidationText.setFill(Color.RED);
        birthDatePicker = new DatePicker();
        birthDatePicker.setPrefWidth(350);
        gridPaneForgotUsername.add(birthDateLabel, 0, 7);
        gridPaneForgotUsername.add(birthDatePicker, 1, 7);
        gridPaneForgotUsername.add(birthDateValidationText, 1, 8);

        // Get username button.
        getUsernameButton = new Button("Get Username");
        getUsernameButton.setDisable(true);
        HBox hBoxGetUsernameButton = new HBox(10);
        hBoxGetUsernameButton.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxGetUsernameButton.getChildren().add(getUsernameButton);
        gridPaneForgotUsername.add(hBoxGetUsernameButton, 1, 15);

        //Button to go back.
        goBackButton = new Button("Go Back");
        gridPaneForgotUsername.add(goBackButton, 0, 15);

        final Text actionTarget = new Text();
        gridPaneForgotUsername.add(actionTarget, 1, 16);

        // Get username action.
        getUsernameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actionTarget.setFill(Color.FIREBRICK);
                if (lockedOut) {
                    actionTarget.setText("TOO MANY FAILED ATTEMPTS\nPlease " +
                            "wait before trying again");
                } else if (accountManager.doesAccountExist(firstNameTextField.getText(),
                        lastNameTextField.getText(), emailTextField.getText()
                        , birthDatePicker.getValue())) {
                    account = accountManager.getUserAccountEmail(emailTextField
                            .getText());
                    account.getUserName();
                    actionTarget.setFill(Color.BLUE);
                    actionTarget.setTextAlignment(TextAlignment.CENTER);
                    actionTarget.setText("Username: " + account.getUserName());
                } else {
                    actionTarget.setFill(Color.RED);
                    actionTarget.setText("LOGIN FAILED");
                    if (++failedLoginCount >= 3) {
                        //reset failedLoginCount
                        failedLoginCount = 0;

                        //lock out
                        lockedOut = true;

                        //wait 60 seconds before unlocking
                        new Timeline(new KeyFrame(
                                Duration.millis(60000),
                                ae -> lockedOut = false))
                                .play();
                    }
                }
            }
        });


        // Functionality for go back button.
        goBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                currentStage.setScene(loginScene);
            }
        });

        addActionToInputControlsWithValidation(firstNameTextField,
                firstNameValidationText);
        addActionToInputControlsWithValidation(lastNameTextField,
                lastNameValidationText);
        addActionToInputControlsWithValidation(emailTextField,
                emailValidationText);
        addActionToInputControlsWithValidation(birthDatePicker,
                birthDateValidationText);
    }

    public GridPane getGridPane() {
        return gridPaneForgotUsername;
    }

    public void addActionToInputControlsWithValidation(Control control, Text validationText) {
        if (validationText == birthDateValidationText) {
            DatePicker birthDatePicker = (DatePicker) control;
            birthDatePicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (birthDatePicker.getValue() != null) {
                        if (validator.validateBirthdate(birthDatePicker.getValue())) {
                            validationText.setText("Valid");
                            validationText.setFill(Color.GREEN);
                            isValidBirthDate = true;
                        } else {
                            validationText.setText("Birthdate must be between 1900 and 2018");
                            validationText.setFill(Color.RED);
                            isValidBirthDate = false;
                        }
                    }
                    checkGetUsernameButtonStatus();
                }
            });

        } else {
            TextField controlTextField = (TextField) control;
            controlTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
                    if (controlTextField == emailTextField) {
                        if (!emailTextField.getText().trim().equalsIgnoreCase("")) {
                            if (validator.validateEmail(emailTextField.getText())) {
                                if (!accountManager.doesEmailExist(emailTextField.getText())) {
                                    validationText.setText("Valid");
                                    validationText.setFill(Color.GREEN);
                                    isValidEmail = true;

                                } else {
//                                    validationText.setText("Email is already in use.");
//                                    validationText.setFill(Color.RED);
//                                    isValidEmail = false;
                                }
                            } else {
                                validationText.setText("Invalid email format");
                                validationText.setFill(Color.RED);
                                isValidEmail = false;
                            }
                        }
                    } else if (controlTextField == firstNameTextField) {
                        if (!firstNameTextField.getText().trim().equalsIgnoreCase("")) {
                            if (validator.validateName(firstNameTextField.getText())) {
                                validationText.setText("Valid");
                                validationText.setFill(Color.GREEN);
                                isValidFirstName = true;
                            } else {
                                validationText.setText("No numbers");
                                validationText.setFill(Color.RED);
                                isValidFirstName = false;
                            }
                        }
                    } else if (controlTextField == lastNameTextField) {
                        if (!firstNameTextField.getText().trim().equalsIgnoreCase("")) {
                            if (validator.validateName(lastNameTextField.getText())) {
                                validationText.setText("Valid");
                                validationText.setFill(Color.GREEN);
                                isValidLastName = true;
                            } else {
                                validationText.setText("No numbers");
                                validationText.setFill(Color.RED);
                                isValidLastName = false;
                            }
                        }
                        checkGetUsernameButtonStatus();
                    }
                    checkGetUsernameButtonStatus();
                }
            });
        }

    }

    public boolean isAllInputValid() {
        return (isValidFirstName && isValidLastName && isValidEmail &&
                isValidBirthDate);
    }

    public void checkGetUsernameButtonStatus() {
        if (isAllInputValid())
            getUsernameButton.setDisable(false);
        else
            getUsernameButton.setDisable(true);
    }
}