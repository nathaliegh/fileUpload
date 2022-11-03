package com.fileUpload.app.common.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Spring FXML Loader
 *
 * @author NG
 * @version 1.0.0
 */
@Component
public class SpringFXMLLoader {

    private static final Logger logger = Logger.getLogger(SpringFXMLLoader.class);


    private final ApplicationContext context;

    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    /**
     * load
     *
     * @param uuid
     * @param fxmlPath
     * @return
     * @throws IOException
     */
    public Parent load(UUID uuid, String fxmlPath) throws IOException {

        logger.info(uuid+" load the fxml screen >>>>  "+fxmlPath);
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource(fxmlPath));
        logger.info(uuid+" the fxml screen is loaded <<< "+fxmlPath);
        return loader.load();


    }
}
