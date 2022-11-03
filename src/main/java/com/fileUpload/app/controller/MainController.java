package com.fileUpload.app.controller;

import com.fileUpload.app.service.FileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;


/**
 * MainController
 *
 * @author NG
 * @version 1.0.0
 */
@Controller
public class MainController implements Initializable {

    @Autowired
    FileService fileService;

    @FXML
    private Button uploadButton;


    private static final Logger logger = Logger.getLogger(MainController.class);


    @FXML
    private void uploadButtonAction(ActionEvent event) throws IOException {
        UUID uuid = UUID.randomUUID();

        logger.debug(uuid+" >>> upload files");

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(uploadButton.getScene().getWindow());
        if(selectedFiles != null) fileService.readFiles(uuid,selectedFiles);
        else System.out.println("You didn't select any text files");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
