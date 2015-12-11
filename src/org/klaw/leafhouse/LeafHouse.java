/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse;

//import generated.LeafHouseComponents;



import java.util.logging.Handler;
import org.klaw.leafhouse.files.LeafHouseFiles;
import org.klaw.leafhouse.exceptions.LeafHouseComponentsFileException;
import org.klaw.leafhouse.files.SyncFilesDatabaseUtil;
import org.klaw.leafhouse.mail.GMailSender;
import org.klaw.leafhouse.mail.LeafHousePrimaryAuthenticator;

import org.klaw.leafhouse.raspberrypi.LeafHouseGpioHandler;
import org.klaw.leafhouse.ws.HTTPServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author Klaw Strife
 */
public class LeafHouse extends HTTPServer {
    private static final Logger logger = LogManager.getLogger(HTTPServer.class.getSimpleName());
    private final static boolean NO_RPI_TEST = true;
    
    

    private final LeafHouseFiles files = LeafHouseFiles.getInstance();
    private LeafHouseGpioHandler rpi_components;
    private final GMailSender gmailSender;

    public LeafHouse() throws LeafHouseComponentsFileException {
        super(true);

        //DummieDataUtil.genDummieData();
        SyncFilesDatabaseUtil.sync(files);
        gmailSender = new GMailSender(new LeafHousePrimaryAuthenticator(
                files.getLeafHouseConfiguration()
                .getMainUser()
                .getGmail(),
                files.getLeafHouseConfiguration()
                .getMainUser()
                .getGmailPassword()));
        if (!NO_RPI_TEST) {
            rpi_components = new LeafHouseGpioHandler(files.getLeafHouseComponents(), gmailSender);
        }
    }

    public void startApp() {
        
    }

    public static void main(String[] args) throws LeafHouseComponentsFileException  {
        disableOtherLoggers();
        logger.info("Starting Leafhouse app");
        new LeafHouse().startApp();
    }

    public static void disableOtherLoggers() {
        java.util.logging.LogManager.getLogManager().reset();
    }


}
