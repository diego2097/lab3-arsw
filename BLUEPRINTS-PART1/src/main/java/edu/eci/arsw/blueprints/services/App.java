/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author luisp
 */
public class App {
    
    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException{
        ApplicationContext aplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bluePrintS = aplicationContext.getBean(BlueprintsServices.class);
        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15,20)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(40, 40), new Point(20, 30)};
        Blueprint bp1 = new Blueprint("john", "mypaintJ", pts1);
        Point[] pts2 = new Point[]{new Point(40, 40), new Point(25, 40)};
        Blueprint bp2 = new Blueprint("mack", "mypaint2", pts2);
        Point[] pts3 = new Point[]{new Point(40, 40), new Point(30, 30)};
        Blueprint bp3 = new Blueprint("mack", "mypaint3", pts3);
        Blueprint bp4 = new Blueprint("john", "mypaintJ2", pts0);
        Point[] pts5 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp5 = new Blueprint("luis", "mypaintFrits", pts5);
        bluePrintS.addNewBlueprint(bp0);
        bluePrintS.addNewBlueprint(bp1);
        bluePrintS.addNewBlueprint(bp2);
        bluePrintS.addNewBlueprint(bp3);
        bluePrintS.addNewBlueprint(bp4);
        bluePrintS.addNewBlueprint(bp5);
        System.out.println(bluePrintS.getBlueprint("luis", "mypaintFrits").toString());
        System.out.println(bluePrintS.getBlueprintsByAuthor("mack").toString());
        System.out.println(bluePrintS.getBlueprintsByAuthor("john").toString());
        System.out.println(bluePrintS.getAllBlueprints().toString());
    }
}
