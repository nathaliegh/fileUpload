package com.fileUpload.app.validation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class FileValidation {

    private static final Logger logger = Logger.getLogger(FileValidation.class);


    public void closeScanningAndReturnMessage(UUID uuid, Scanner scan, String message) throws Exception{
        scan.close();
        logger.error(uuid+ message);
        System.err.println(uuid+ message);
        throw new Exception(message);
    }

}
