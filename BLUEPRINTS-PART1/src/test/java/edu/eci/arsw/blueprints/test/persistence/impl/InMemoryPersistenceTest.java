/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.SubsamplingFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }

    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }

    }

    @Test
    public void getBluePrint() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        try {
            assertNotNull("An exception was expected after getting the bluePrint  with the  name and autor", ibpp.getBlueprint("john", "thepaint"));
        } catch (BlueprintNotFoundException ex) {
            fail("An exception was expected after getting the bluePrint  with the  name and autor");
        }

    }

    @Test
    public void getBlueprintsByAuthor() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        ibpp.saveBlueprint(bp0);
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        ibpp.saveBlueprint(bp);
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts);
        ibpp.saveBlueprint(bp2);
        Set<Blueprint> bpn = new HashSet<Blueprint>();
        bpn.add(bp);
        bpn.add(bp2);
        assertNotNull("Loading a previously stored blueprints returned null.", ibpp.getBlueprintsByAuthor(bp.getAuthor()));
        assertEquals("Loading a previously stored blueprints returned a different blueprints.", ibpp.getBlueprintsByAuthor(bp.getAuthor()), bpn);
    }

    @Test
    public void RedundancyFiltering() {
        SubsamplingFiltering filtro = new SubsamplingFiltering();

        Point[] pts0 = new Point[]{new Point(40, 30), new Point(30, 40), new Point(40, 30), new Point(40, 80), new Point(40, 100), new Point(100, 40)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(20, 15), new Point(40, 40)};
        Blueprint bp1 = new Blueprint("john", "mypaintJ", pts1);
        Point[] pts2 = new Point[]{new Point(40, 40), new Point(25, 40), new Point(40, 25),new Point(40, 25)};
        Blueprint bp2 = new Blueprint("mack", "mypaint2", pts2);

        Set<Blueprint> set = new HashSet<Blueprint>();
        set.add(bp0);
        set.add(bp2);

        HashMap<Blueprint,Integer> map = new HashMap<>();
        
        Blueprint temp = new Blueprint();
        Iterator<Blueprint> it = set.iterator();
        while (it.hasNext()) {
            temp = it.next();
            map.put(temp, temp.getPoints().size());
        }

        while (it.hasNext()) {
            temp = it.next();
            assertTrue(map.get(temp) > temp.getPoints().size());
        }

    }
}
