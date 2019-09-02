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
