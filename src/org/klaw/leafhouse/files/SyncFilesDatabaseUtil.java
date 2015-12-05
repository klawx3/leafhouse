/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.klaw.leafhouse.files;

import org.hibernate.Session;
import org.klaw.binding.components.LeafHouseComponents;
import org.klaw.binding.config.LeafHouseConfiguration;
import org.klaw.binding.config.LeafHouseConfiguration.MainUser;
import org.klaw.leafhouse.db.dto.Actuator;
import org.klaw.leafhouse.db.dto.Location;
import org.klaw.leafhouse.db.dto.Sensor;
import org.klaw.leafhouse.db.dto.User;
import org.klaw.leafhouse.db.utils.HibernateUtil;
import org.klaw.leafhouse.types.SensorType;

/**
 *
 * @author Klaw Strife
 */
public class SyncFilesDatabaseUtil {

    public static void sync(LeafHouseFiles files) {
        syncUserConfiguration(files);
        syncComponents(files);
    }
    
    private static void syncComponents(LeafHouseFiles files) {
        LeafHouseComponents components = files.getLeafHouseComponents();
        components.getActuator().forEach(SyncFilesDatabaseUtil::syncActuator);
        components.getSensor().forEach(SyncFilesDatabaseUtil::syncSensor);        
    }
    
    private static void syncActuator(LeafHouseComponents.Actuator xmlActuator){
        int gpioActuatorFilePin = xmlActuator.getAttachedGpioPin().intValue();
        Actuator dtoActuator = getActuatorDtoByPin(gpioActuatorFilePin);
        if(dtoActuator != null){ // existe el actuador
            if(!dtoActuator.isEqualsToFileActuator(xmlActuator)){
                saveOrUpdateXmlActuatorToDatabase(xmlActuator, dtoActuator);
            }else{
                System.out.println("Nada que hacer el actuador es igual a la de la bd");
            }
        }else{
            Location location = getLocationByName(xmlActuator.getLocation());
            if(location == null){
                location = new Location(xmlActuator.getLocation());
            }
            saveActuatorToDatabase(Actuator.convertFileActuator(xmlActuator,
                    location));
        }
    }
    
    private static void syncSensor(LeafHouseComponents.Sensor xmlSensor){
        int gpioSensorFilePin = xmlSensor.getAttachedGpioPin().intValue();
        Sensor dtoSensor = getSensorDtoByPin(gpioSensorFilePin);
        if(dtoSensor != null){
            if(!dtoSensor.isEqualsToFileSensor(xmlSensor)){
                saveOrUpdateXmlSensorToDatabase(xmlSensor, dtoSensor);
            }else{
                System.out.println("El sensor es igual a la base de datos");
            }
        }else{
            Location location = getLocationByName(xmlSensor.getLocation());
            if(location == null){
                location = new Location(xmlSensor.getLocation());
            }
            saveSensorToDatabase(Sensor.convertFileSensor(xmlSensor,
                    location));
        }
        
    }

    private static void saveSensorToDatabase(Sensor sensor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(sensor);
        session.getTransaction().commit();
        session.close();
    }

    private static void saveActuatorToDatabase(Actuator actuator) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(actuator);
        session.getTransaction().commit();
        session.close();
    }

    private static void saveOrUpdateXmlSensorToDatabase(
            LeafHouseComponents.Sensor fileSensor, Sensor dtoSensor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Sensor sensor = (Sensor) session.get(Sensor.class, dtoSensor.getSensorId());
        sensor.setDescription(fileSensor.getDescription());
        sensor.setSensorGpioPin(fileSensor.getAttachedGpioPin().intValue());
        sensor.getSensorLocation().setLocationName(fileSensor.getLocation());
        sensor.setSensorName(fileSensor.getName());
        sensor.setSensorType(SensorType.valueOf(fileSensor.getType()));
        session.saveOrUpdate(sensor);
        session.getTransaction().commit();
        session.close();

    }
    
    private static void saveOrUpdateXmlActuatorToDatabase(
            LeafHouseComponents.Actuator fileActuator,Actuator dtoActuator) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Actuator actuator = (Actuator) session.get(Actuator.class, dtoActuator.getActuatorId());
        actuator.setActuatorDescription(fileActuator.getDescription());        
        actuator.getActuatorLocation().setLocationName(fileActuator.getLocation()); // ver si esto funciona. si actualiza la ubicacion relacionada
        actuator.setActuatorName(fileActuator.getName());
        actuator.setActuatorGpioPin(fileActuator.getAttachedGpioPin().intValue()); // no deberia pasar ya que se compara abtes
        session.saveOrUpdate(actuator);
        session.getTransaction().commit();
        session.close();

    }
    
    private static Location getLocationByName(String locationName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from Location where locationName = :locationName";
        Location location = (Location) session.createQuery(query)
                .setParameter("locationName", locationName)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return location;
    }

    private static Sensor getSensorDtoByPin(int pin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from Sensor where sensorGpioPin = :pin";
        Sensor sensor = (Sensor) session.createQuery(query)
                .setParameter("pin", pin)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return sensor;
    }

    private static Actuator getActuatorDtoByPin(int pin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from Actuator where actuatorGpioPin = :pin";
        Actuator actuator = (Actuator) session.createQuery(query)
                .setParameter("pin", pin)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return actuator;
    }
    

    
    private static void syncUserConfiguration(LeafHouseFiles files) {
        LeafHouseConfiguration configuration = files.getLeafHouseConfiguration();
        MainUser xmlUser = configuration.getMainUser();
        User databaseUser = getLeafHouseMainUser();
        if (databaseUser != null) {
            if (!isUserEquals(xmlUser, databaseUser)) {
                setXmlUserToDatabase(xmlUser, databaseUser);
            }else{
                System.err.println("User "+databaseUser.getUserName() +" already added. skipping");
            }
        } else {
            addXmlUserToDatabase(xmlUser);
        }
    }

    private static boolean isUserEquals(MainUser xmlUser, User databaseUser) {
        return xmlUser.getName().equals(databaseUser.getUserName())
                && xmlUser.getGmail().equalsIgnoreCase(databaseUser.getUserEmail())
                && xmlUser.getGmailPassword().equals(databaseUser.getUserPassword());
    }
    
    private static void addXmlUserToDatabase(MainUser xmlUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User();
        user.setUserName(xmlUser.getName());
        user.setUserPassword(xmlUser.getGmailPassword());
        user.setUserEmail(xmlUser.getGmail());
        user.setUserLeafHouse(true);
        user.setUserAdmin(true);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    private static void setXmlUserToDatabase(MainUser xmlUser,User databaseUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, databaseUser.getUserId());
        user.setUserName(xmlUser.getName());
        user.setUserEmail(xmlUser.getGmail());
        user.setUserPassword(xmlUser.getGmailPassword());
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();

    }

    private static User getLeafHouseMainUser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User user = (User) session.createQuery("from User where userLeafHouse = :param1")
                .setParameter("param1", Boolean.TRUE)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }


}
