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
@Service("intercalado")
public class SubsamplingFiltering implements Filter {

    @Override
    public Set<Blueprint> filtrar(Set<Blueprint> set) {
        System.out.println(set);
        Set<Blueprint> retornar = new HashSet<>();

        Iterator<Blueprint> it = set.iterator();
        Blueprint temp;
        int rango = 1;
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
                    if (( (x1+rango == x2 && y1 == y2)|| (x1-rango == x2 && y1 == y2) 
                            || (x1 == x2 && y1+rango == y2)) || (x1 == x2 && y1-rango == y2)
                            || (x1+rango == x2 && y1+rango == y2) || (x1-rango == x2 && y1-rango == y2)
                            || (x1+rango == x2 && y1-rango == y2) || (x1-rango == x2 && y1+rango == y2)
                            ) {
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





