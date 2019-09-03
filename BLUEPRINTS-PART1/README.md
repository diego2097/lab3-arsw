# Blueprint Management 1

## Part I

1. Anadimos las anotaciones necesarias. Para que sea inyectado BlueprintPersistence cuando se cree BlueprintServices. Como tambien se anadieron 
los metodos faltantes y sus implementaciones. 

### Clase BlueprintPersistence

```java 
@Service
public interface BlueprintsPersistence {
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;
    
    /**
     * 
     * @param author blueprint's author
     * @return A list of blueprints that belong to the author.  
     * @throws BlueprintNotFoundException if there is no such author. 
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;
```

### Implementaciones de la clase InMemoryBlueprintPersistence 

```java
@Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException  {
       Set<Blueprint> retornar=new HashSet<>();
       for(Tuple<String,String> e : blueprints.keySet()){
          if( e.getElem1().equals(author)){
              retornar.add( blueprints.get(e));
          }
       }
       return retornar;
    }
```

Como tambien se realizaron las debidas pruebas para estos metodos. 

```java
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
```

2. Creamos la clase App haciendo la inyeccion por medio de spring para probar cada funcionalidad: Registrar, consultar, etc. 

```java
public class App {
    
    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException{
        ApplicationContext aplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bluePrintS = aplicationContext.getBean(BlueprintsServices.class);
        
        Point[] pts0 = new Point[]{new Point(40, 30), new Point(30,40),new Point(40,30),new Point(40,80),new Point(40,100),new Point(100,40)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(20, 15), new Point(40, 40)};
        Blueprint bp1 = new Blueprint("john", "mypaintJ", pts1);
        Point[] pts2 = new Point[]{new Point(40, 40), new Point(25, 40),new Point(40, 25)};
        Blueprint bp2 = new Blueprint("mack", "mypaint2", pts2);
        Point[] pts3 = new Point[]{new Point(40, 25), new Point(30, 30)};
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
```

3 y 4.  Primero se implemento el metodo getAllBlueprints() que sera al que se le aplicara el debido filtro. 

```java
public Set<Blueprint> getAllBlueprints(){
	return filtro.filtrar(bpp.getAllBlueprints());
}
```

Creamos la interfaz filter, con el metodo filtrar. 


```java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public interface Filter {

    public Set<Blueprint> filtrar(Set<Blueprint> set);
    
}
```
   
En la clase blueprintsServices se anadio la anotacion @Qualifier que nos permite elegir la instancia que queremos que sea inyectada. 

```java
@Autowired 
@Qualifier ("redundante")         
Filter filtro=null;
```    
   
Creamos la implementacion RedundancyFiltering que elimina los puntos repetidos. 

```java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service ("redundante")
public class RedundancyFiltering implements Filter {

    @Override
    public Set<Blueprint> filtrar(Set<Blueprint> set) {
        System.out.println(set);
        Set<Blueprint> retornar = new HashSet<>();

        Iterator<Blueprint> it = set.iterator();
        Blueprint temp;
        while (it.hasNext()) {
            List<Point> lista = new ArrayList<Point>();
            boolean f = true;
            temp = it.next();
            lista.add(temp.getPoints().get(0));
            for (int i = 1; i < temp.getPoints().size(); i++) {
                f = true;
                int x1 = temp.getPoints().get(i).getX();
                int y1 = temp.getPoints().get(i).getY();
                for (int j = 0; j < lista.size() && f; j++) {
                    int x2 = lista.get(j).getX();
                    int y2 = lista.get(j).getY();
                    if (( (x1 == x2 && y1 == y2))) {
                        f = false;
                    }
                }
                if (f) {
                    lista.add(temp.getPoints().get(i));
                }
            }   
            temp.setPoints(lista);
            retornar.add(temp);
        }
        return retornar;
    }
}
```

Creamos las pruebas necesarias para esta funcionalidad. 

```java
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
```
