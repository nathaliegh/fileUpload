package com.fileUpload.app.service;


import com.fileUpload.app.model.Client;
import com.fileUpload.app.model.Sale;
import com.fileUpload.app.model.Seller;
import com.fileUpload.app.validation.FileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

@Service
public class FileLineService {

    @Autowired
    private FileValidation fileValidation;

    @Autowired
    private SaleService saleService;

    /**
     * Scan the file line by line and validate it
     *
     * @param scan
     * @param selectedFile
     * @param clientsPerFile
     * @param sellersPerFile
     * @param salesPerFile
     * @throws Exception
     */
    public void validateAndScanLineByLine(UUID uuid,
                                          Scanner scan,
                                          File selectedFile,
                                          Set<Client> clientsPerFile,
                                          Set<Seller> sellersPerFile,
                                          Set<Sale> salesPerFile) throws Exception {
        while(scan.hasNext()){
            String line = scan.nextLine();
            if (line == null) fileValidation
                    .closeScanningAndReturnMessage(uuid,scan,
                            "The line " + line + " has null value " +selectedFile.getName() + "file");
            String[] splitLine = line.split(",");
            if (splitLine == null) fileValidation
                    .closeScanningAndReturnMessage(uuid,scan,
                            "The structure line is " + line + " in the file " + selectedFile.getName() + "file");
            String lineId = splitLine[0];
            if (lineId == null) fileValidation
                    .closeScanningAndReturnMessage(uuid,scan,
                            "The line code is wrong " + splitLine[0]);
            scanLine(uuid,scan, lineId, splitLine, clientsPerFile, sellersPerFile, salesPerFile);
        }
    }

    /**
     * Scan line and recognize which type it is and validate it.
     *
     * @param scan
     * @param lineId
     * @param splitLine
     * @param clientsPerFile
     * @param sellersPerFile
     * @param salesPerFile
     * @throws Exception
     */
    private void scanLine(UUID uuid,
                          Scanner scan,
                  String lineId,
                  String[] splitLine,
                  Set<Client> clientsPerFile,
                  Set<Seller> sellersPerFile,
                  Set<Sale> salesPerFile
    ) throws Exception {
        switch (lineId) {
            case "001": sellersPerFile.add(new Seller(splitLine[1])); break;
            case "002": clientsPerFile.add(new Client(splitLine[1],splitLine[2])); break;
            case "003":
                Optional<Seller> sellerOptional = sellersPerFile.stream().filter(sel -> sel.getName().equals(splitLine[3])).findFirst();
                if(sellerOptional.isEmpty()) fileValidation
                        .closeScanningAndReturnMessage(uuid,scan,
                                "The seller not exist "+splitLine[3]);
                var sale = new Sale(Integer.parseInt(splitLine[1]), sellerOptional.get());
                sale.setSaleDetails(saleService.convertTextLineToSaleDetails(uuid,scan, splitLine[2]));
                salesPerFile.add(sale);
                break;
            default: fileValidation
                    .closeScanningAndReturnMessage(uuid,scan,
                            "It's a wrong line kindly modified " + splitLine);
        }
    }

}
