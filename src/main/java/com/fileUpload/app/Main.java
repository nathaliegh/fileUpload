package com.fileUpload.app;

import com.fileUpload.app.common.enumeration.FxmlView;
import com.fileUpload.app.common.standard.GlobalVariable;
import com.fileUpload.app.common.util.Properties;
import com.fileUpload.app.common.util.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.UUID;

/**
 * Application
 */
@SpringBootApplication
@ComponentScan({"com.fileUpload.app.controller", "com.fileUpload.app.service", "com.fileUpload.app.common","com.fileUpload.app.validation"})
public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class);
    protected StageManager stageManager;
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //init the parameters
        UUID uuid = UUID.randomUUID();
        //declare logging file
        initProperties();
        logger.info(uuid + " >>>  start fileUpload application ");
        stageManager = springContext.getBean(StageManager.class);
        displayInitialScene(uuid, primaryStage);


    }

    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    public void stop() throws Exception {
        springContext.close();
    }

    /**
     * Display initial scene
     *
     * @param uuid
     */
    protected void displayInitialScene(UUID uuid, Stage stage) throws IOException {
        logger.info(uuid + " >>> displayInitialScene");
        stageManager.switchScene(uuid, FxmlView.MAIN, stage);
    }

    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }


    public void initProperties() {
        try {
            GlobalVariable.screenProperties.load(Properties.getConfResource("screen.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
