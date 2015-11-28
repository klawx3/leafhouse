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

    public LeafHouse() throws LeafHouseComponentsFileException  {
        super(true);        
        if(!NO_RPI_TEST)
            rpi_components = new LeafHouseRpiComponents(files.getLeafHouseComponents());
    }


    public void startApp()  {
        DummieDataUtil.genDummieData();
    }

    public static void main(String[] args) throws LeafHouseComponentsFileException  {
        new LeafHouse().startApp();
    }


}
