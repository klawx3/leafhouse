/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse;

//import generated.LeafHouseComponents;

import java.io.IOException;
import java.util.List;
import org.klaw.leafhouse.files.LeafHouseFiles;
import org.klaw.leafhouse.db.utils.DummieDataUtil;
import org.klaw.leafhouse.db.utils.HibernateUtil;
import org.klaw.leafhouse.exceptions.LeafHouseComponentsFileException;
import org.klaw.leafhouse.files.SyncFilesDatabaseUtil;
import org.klaw.leafhouse.mail.GMailSender;
import org.klaw.leafhouse.mail.LeafHousePrimaryAuthenticator;

import org.klaw.leafhouse.raspberrypi.LeafHouseRpiComponents;
import org.klaw.leafhouse.ws.HTTPServer;

/**
 *
 * @author Klaw Strife
 */
public class LeafHouse extends HTTPServer {
    private final static boolean NO_RPI_TEST = true;

    private final LeafHouseFiles files = LeafHouseFiles.getInstance();
    private LeafHouseRpiComponents rpi_components;
    private final GMailSender gmailSender;

    public LeafHouse() throws LeafHouseComponentsFileException {
        super(true);
        DummieDataUtil.genDummieData();
        SyncFilesDatabaseUtil.sync(files);
        gmailSender = new GMailSender(new LeafHousePrimaryAuthenticator(
                files.getLeafHouseConfiguration()
                .getMainUser()
                .getGmail(),
                files.getLeafHouseConfiguration()
                .getMainUser()
                .getGmailPassword()));
        if (!NO_RPI_TEST) {
            rpi_components = new LeafHouseRpiComponents(files.getLeafHouseComponents(), gmailSender);
        }
    }

    public void startApp() {
        
    }

    public static void main(String[] args) throws LeafHouseComponentsFileException  {
        new LeafHouse().startApp();
    }


}
