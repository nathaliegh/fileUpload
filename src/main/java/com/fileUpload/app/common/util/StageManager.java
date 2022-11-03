package com.fileUpload.app.common.util;

import com.fileUpload.app.common.enumeration.FxmlView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Stage Manager
 *
 * @author NG
 * @version 1.0.0
 */
@Component
public class StageManager {

    private static final Logger logger = Logger.getLogger(StageManager.class);
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader) {
        logger.info(">>>>>>>>>>>>>>>>>> 1 , new stage manage constructor ");
        this.springFXMLLoader = springFXMLLoader;
    }

    /**
     * Switch scene
     * <p>
     * Load view node hierarchy
     * <p>
     * show the screen
     *
     * @param uuid
     * @param view
     */
    public void switchScene(UUID uuid, final FxmlView view , Stage stage) throws IOException {
        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 2 , switch scene " + view.name());
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(uuid, view.getFxmlFile());
        show(uuid, viewRootNodeHierarchy, view , stage);
    }

    /**
     * show the stage
     *
     * @param uuid
     * @param rootnode
     * @param view
     */
    private void show(UUID uuid, final Parent rootnode, FxmlView view , Stage stage) throws IOException {
        logger.info(uuid + " >>>>>>>>>>>>>>>>>> 3 , show >>>");

        Scene scene = prepareScene(uuid, rootnode, view.getWidth(), view.getHeight() , stage);

        Image icon = new Image(view.getIcon());
        stage.getIcons().add(icon);
        stage.setTitle(view.getTitle());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(view.isResizable());


        try {
            stage.show();
            logger.info(uuid + " <<<<<<<<<<<<<<<<<< 3 , show <<<");
        } catch (Exception exception) {
        }
    }

    /**
     * Prepare screen
     *
     * @param uuid
     * @param rootnode
     * @param width
     * @param height
     * @return
     */
    private Scene prepareScene(UUID uuid ,Parent rootnode, int width, int height , Stage stage) {
        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 4 , prepare scene >> ");

        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 4 , prepare scene >> scene has width :: " + width + " height :: " + height);
        Scene scene = stage.getScene();
        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 4 , prepare scene >> scene  :: " + scene);

        if (scene == null) {
            logger.info(uuid + ">>>>>>>>>>>>>>>>>> 4 , prepare scene >> scene  is null ");
            scene = new Scene(rootnode, width, height);
        }
        scene.setRoot(rootnode);
        logger.info(uuid + "<<<<<<<<<<<<<<<<<<< 4 , prepare scene << ");
        return scene;
    }


    /**
     * Load view node hierarchy
     *
     * @param uuid
     * @param fxmlFilePath
     * @return
     */
    private Parent loadViewNodeHierarchy(UUID uuid, String fxmlFilePath) {
        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 5 , loadViewNodeHierarchy");
        logger.info(uuid + ">>>>>>>>>>>>>>>>>> 5 path: " + fxmlFilePath);
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(uuid,fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logger.error(uuid + ">>>>>>>>>>>>>>>>>> 5 error while load view for fxml file ??? : " + fxmlFilePath);
            exception.printStackTrace();
        }
        return rootNode;
    }
}
