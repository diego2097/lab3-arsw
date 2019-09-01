package edu.eci.arsw.springdemo;

import org.springframework.stereotype.Service;


@Service("Spanish")
public class SpanishSpellChecker implements SpellChecker {

	@Override
	public String checkSpell(String text) {
		return "revisando ("+text+") con el verificador de sintaxis del espanol";
                
                
	}

}
