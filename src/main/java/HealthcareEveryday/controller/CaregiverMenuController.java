package HealthcareEveryday.controller;

import HealthcareEveryday.MainApp;
import HealthcareEveryday.service.AuthService;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Controller for the caregiver menu view.
 */
public class CaregiverMenuController {
    private MainApp mainApp;
    private AuthService authService;

    /**
     * Sets the main application reference for scene switching and service access.
     *
     * @param mainApp Main application instance.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.authService = mainApp.getAuthService();
    }

    /**
     * Opens the user selection scene for editing routines.
     */
    @FXML
    private void handleEditRoutine() {
        mainApp.showCaregiverSelectUserScene();
    }

    /**
     * Opens the history period selection scene.
     */
    @FXML
    private void handleViewHistory() {
        mainApp.showHistoryPeriodScene();
    }

    /**
     * Opens the summary generation user selection scene.
     */
    @FXML
    private void handleGenerateSummary() {
        mainApp.showGenerateSummarySelectUserScene();
    }

    /**
     * Handles adding a new user.
     */
    @FXML
    private void handleAddUser() {
        Optional<String> result = promptForInput(
                "Add New User",
                "Please enter the name of the new user:"
        );

        if (result.isEmpty()) {
            return;
        }

        String name = result.get().trim();

        if (name.isEmpty()) {
            showInfo("Invalid name", "User name cannot be empty.");
            return;
        }

        boolean added = authService.addUser(name);

        if (added) {
            showInfo("Success", "User \"" + name + "\" has been added.");
        }
        else {
            showInfo("Failed", "A user with that name already exists, or the name is invalid.");
        }
    }

    /**
     * Handles changing the caregiver password.
     */
    @FXML
    private void handleChangePassword() {
        Optional<String> oldResult = promptForInput(
                "Change Password",
                "Enter old password:"
        );

        if (oldResult.isEmpty()) {
            return;
        }

        Optional<String> newResult = promptForInput(
                "Change Password",
                "Enter new password:"
        );

        if (newResult.isEmpty()) {
            return;
        }

        String oldPassword = oldResult.get();
        String newPassword = newResult.get();

        boolean changed = authService.changeCaregiverPassword(oldPassword, newPassword);

        if (changed) {
            showInfo("Success", "Password updated successfully.");
        }
        else {
            showInfo("Failed", "Old password is incorrect or new password is empty.");
        }
    }

    /**
     * Shows a text-input dialog and returns the entered value.
     *
     * @param title Dialog title.
     * @param contentText Prompt text shown in the dialog.
     * @return Entered value, if any.
     */
    private Optional<String> promptForInput(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(contentText);
        return dialog.showAndWait();
    }

    /**
     * Returns the user to the login scene.
     */
    @FXML
    private void handleBack() {
        mainApp.showLoginScene();
    }

    /**
     * Shows an information alert with the given title and message.
     *
     * @param title Title of the alert.
     * @param message Message to display.
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
