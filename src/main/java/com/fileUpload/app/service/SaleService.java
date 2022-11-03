package com.fileUpload.app.service;


import com.fileUpload.app.model.SaleDetails;
import com.fileUpload.app.validation.FileValidation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleService {

    private static final Logger logger = Logger.getLogger(SaleService.class);

    @Autowired
    private FileValidation fileValidation;

    /**
     * Convert Text Line to sale details object
     *
     * @param uuid
     * @param scan
     * @param saleDetailsLine
     * @return
     * @throws Exception
     */
    public Set<SaleDetails> convertTextLineToSaleDetails(UUID uuid, Scanner scan, String saleDetailsLine) throws Exception {
        logger.debug(uuid+" >>> convertTextLineToSaleDetails "+saleDetailsLine);
        if(!saleDetailsLine.contains("[") || !saleDetailsLine.contains("]")|| !saleDetailsLine.contains(";"))
            fileValidation
                    .closeScanningAndReturnMessage(uuid,scan,
                            "The format of sale details line "+saleDetailsLine+" is wrong ");

        saleDetailsLine = saleDetailsLine.replace("[","");
        saleDetailsLine = saleDetailsLine.replace("]","");
        String[] saleDetailsArray = saleDetailsLine.split(";");
        if(Optional.ofNullable(saleDetailsArray).isEmpty()) fileValidation
                .closeScanningAndReturnMessage(uuid,scan,
                        "The format of sale details line "+saleDetailsLine+" is wrong ");
        logger.debug(uuid+" <<<< convertTextLineToSaleDetails "+saleDetailsLine);
        return splitTheSaleDetailsArray(uuid,scan, saleDetailsArray);
    }

    /**
     * Split the sale details array
     * Sale id, amount,price
     *
     * @param uuid
     * @param scan
     * @param saleDetailsArray
     * @return
     * @throws Exception
     */
    private Set<SaleDetails>  splitTheSaleDetailsArray(UUID uuid,Scanner scan,String[] saleDetailsArray) throws Exception {
        logger.debug(uuid+" >>> splitTheSaleDetailsArray "+saleDetailsArray);
        Set<SaleDetails> saleDetails = new HashSet<>();
        for(int i=0; i< saleDetailsArray.length ; i++){
            var saleDetailLine=   saleDetailsArray[i];
            var saleDetail = saleDetailLine.split("-");
            try{saleDetails.add(new SaleDetails(Integer.parseInt(saleDetail[0]),
                    Integer.parseInt(saleDetail[1]),
                    Double.parseDouble(saleDetail[2])));}
            catch(NumberFormatException ex){
                fileValidation
                        .closeScanningAndReturnMessage(uuid,scan,
                                "The format of sale details line "+saleDetailLine+" is wrong ");
            }
        }
        logger.debug(uuid+" <<< splitTheSaleDetailsArray "+saleDetailsArray);
        return saleDetails;
    }
}
