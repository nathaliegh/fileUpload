package com.fileUpload.app.service;


import com.fileUpload.app.common.util.DateUtil;
import com.fileUpload.app.model.Client;
import com.fileUpload.app.model.Sale;
import com.fileUpload.app.model.Seller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final Logger logger = Logger.getLogger(FileService.class);

    @Autowired
    private FileLineService fileLineService;

    @Autowired
    private DateUtil dateUtil;

    private static Set<Client> clients = new HashSet<>();
    private static Set<Seller> sellers = new HashSet<>();
    private static Set<Sale> sales = new HashSet<>();

    /**
     * Read multiple files
     *
     * Read all files in parallel then write an output file
     *
     * @param selectedFiles
     */
    public void readFiles(UUID uuid,List<File> selectedFiles){
        logger.debug(uuid+" >>> readFiles");
        for(int i = 1;i < selectedFiles.size() ; i++){
            if(i == 10000) break;
            try {
                FileProcessor fileProcessor = new FileProcessor("ThreadF"+i,uuid,selectedFiles.get(i));
            } catch (Exception e) {
                System.err.println(uuid+"The file "+selectedFiles.get(i).getName()+" has wrong structure");
                logger.error(uuid+"The file "+selectedFiles.get(i).getName()+" has wrong structure");
            }
        }
        logger.debug(uuid+" <<< readFiles");
        writeOutputFile(uuid);
    }


    /**
     * Read a single file
     *
     * and validate the information
     *
     * @param selectedFile
     * @throws Exception
     */
    private void readFile(UUID uuid,File selectedFile) throws Exception{
        try {
            logger.info(uuid+"<<< read file"+selectedFile.getAbsolutePath());
            Scanner scan = new Scanner(selectedFile);
            Set<Client> clientsPerFile = new HashSet<>();
            Set<Seller> sellersPerFile = new HashSet<>();
            Set<Sale> salesPerFile = new HashSet<>();
            fileLineService.validateAndScanLineByLine(uuid,scan, selectedFile,
                    clientsPerFile,sellersPerFile, salesPerFile );
            scan.close();
            sellers.addAll(sellersPerFile);
            clients.addAll(clientsPerFile);
            sales.addAll(salesPerFile);
            selectedFile.deleteOnExit();
            logger.info(uuid+" >>>read file"+selectedFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void writeOutputFile(UUID uuid) {
        logger.debug(uuid+" >>> writeOutputFile");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dateUtil.now()+".done.txt", true))) {
            bw.write("Number of Clients found in the file: "+clients.size());
            bw.newLine();
            bw.write("Number of Sellers found in the file: "+sellers.size());
            bw.newLine();
            if(sales.isEmpty()) {bw.close(); return;}
            List<Sale> result = sales.stream()
                    .sorted(Comparator.comparingDouble(Sale::getTotal)).collect(Collectors.toList());
            bw.write("Sales id of the biggest sale: "+result.get(result.size()-1).getSaleId());
            bw.newLine();
            bw.write("Name of the Seller that earned less money:: "+result.stream().findFirst().get().getSeller().getName());
            bw.newLine();
            bw.close();
            logger.debug(uuid+" <<< writeOutputFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FileProcessor implements Runnable {
        private final File file;
        private Thread thread;
        private UUID uuid;
        private String threadName;

        public FileProcessor(String threadName, UUID uuid, File file) {
            this.threadName = threadName;
            this.file = file;
            this.uuid = uuid;
        }

        @Override
        public void run() {
            try {
                readFile(uuid,file);
                Thread.sleep(5);
            } catch (Exception e) {
                System.err.println(uuid+"The file "+file.getName()+" has wrong structure");
                logger.error(uuid+"The file "+file.getName()+" has wrong structure");
            }
        }

        public void start () {
            if (thread == null) {
                thread = new Thread (this, threadName);
                thread.start ();
            }
        }
    }

}
