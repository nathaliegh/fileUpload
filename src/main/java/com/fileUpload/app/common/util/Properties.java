package com.fileUpload.app.common.util;



import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;

/**
 * Properties
 *
 * @author NG
 * @version 1.0.0
 */
public class Properties {
    private static final Logger logger = Logger.getLogger(Properties.class);

    private Properties() {
    }

    public static InputStream getConfResource(String propertyFile) {
        PropertyConfigurator.configure(Properties.class.getResource("/properties/"+propertyFile));
        return Properties.class.getClassLoader().getResourceAsStream("properties/" + propertyFile);
    }

}
