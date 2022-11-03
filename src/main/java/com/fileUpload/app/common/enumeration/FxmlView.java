package com.fileUpload.app.common.enumeration;

import com.fileUpload.app.common.standard.GlobalVariable;
import org.apache.log4j.Logger;


/**
 * FXML View
 *
 * @author NG
 * @version 1.0.0
 */
public enum FxmlView {

    MAIN {
        @Override
        public String getTitle()  {
            return getStringFromResourceBundle("main.title");
        }


        @Override
        public String getIcon()  {
            return getStringFromResourceBundle("main.icon");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/main.fxml";
        }

        @Override
        public int getWidth() {
            return Integer.parseInt(getStringFromResourceBundle("main.width"));
        }

        @Override
        public int getHeight() {
            return Integer.parseInt(getStringFromResourceBundle("main.height"));
        }

        @Override
        public boolean isResizable()  {
            return Boolean.getBoolean(getStringFromResourceBundle("main.resizable"));
        }
    };

    private static final Logger logger = Logger.getLogger(FxmlView.class);

    public abstract String getTitle() ;

    public abstract String getIcon() ;

    public abstract String getFxmlFile();

    public abstract int getWidth() ;

    public abstract int getHeight();

    public abstract boolean isResizable() ;

    String getStringFromResourceBundle(String key)  {
        return GlobalVariable.screenProperties.getProperty(key);
    }
}
