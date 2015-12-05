/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.files;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.klaw.binding.components.LeafHouseComponents;
import org.klaw.binding.config.LeafHouseConfiguration;


import org.klaw.leafhouse.exceptions.LeafHouseComponentsFileException;

/**
 *
 * @author Klaw Strife
 */
public class LeafHouseFiles {
  
    public static final String COMPONENTS_FILENAME = "leafhouse_components.xml";
    public static final String CONFIGURATION_FILENAME = "leafhouse_configuration.xml";   
    

    private static LeafHouseFiles instance;    
    private LeafHouseComponents leafHouseComponents;
    private LeafHouseConfiguration leafHouseConfiguration;

    public static LeafHouseFiles getInstance() throws LeafHouseComponentsFileException {
        if (instance != null) {
            return instance;
        }
        return new LeafHouseFiles();
    }
    
    private LeafHouseFiles() throws LeafHouseComponentsFileException{
        loadComponents();
        loadConfiguration();
    }

    private void loadComponents() throws LeafHouseComponentsFileException {
        try {
            JAXBContext context = JAXBContext.newInstance(org.klaw.binding.components.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            leafHouseComponents = (LeafHouseComponents) unmarshaller.unmarshal(new File(COMPONENTS_FILENAME));
            LeafHouseComponentsFileValidator validator = new LeafHouseComponentsFileValidator(leafHouseComponents);
            if (!validator.isValid()) {
                throw new LeafHouseComponentsFileException(validator.getInvalidEntries());
            }

        } catch (JAXBException ex) {
            Logger.getLogger(LeafHouseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadConfiguration() {
        try {
            JAXBContext context = JAXBContext.newInstance(org.klaw.binding.config.ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            leafHouseConfiguration = (LeafHouseConfiguration) unmarshaller.unmarshal(new File(CONFIGURATION_FILENAME));

        } catch (JAXBException ex) {
            Logger.getLogger(LeafHouseFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LeafHouseComponents getLeafHouseComponents() {
        return leafHouseComponents;
    }

    public LeafHouseConfiguration getLeafHouseConfiguration() {
        return leafHouseConfiguration;
    }




    
    
}
