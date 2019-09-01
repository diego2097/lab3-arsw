# Parte 1 

3. Se utilizan las anotaciones @Service y @Autowired para crear los beans y hacer la inyeccion de SpellCheck


Clase GrammarChecker. Se agregaron las anotaciones @Service para crear el bean de esta clase, @Autowired para realizar 
la inyeccion del SpellChecker y @Qualifier para elegir que implementacion se desea inyectar. 

![alt text](https://github.com/diego2097/lab3-arsw/blob/master/Grammar-Checker/img/1.gramachecker.PNG)

Clase SpanishSpellChecker. Se agregaron las anotaciones @Service para crear el bean de esta clase

![alt text](https://github.com/diego2097/lab3-arsw/blob/master/Grammar-Checker/img/1.SpanishChecker.PNG)

Clase EnglishSpellChecker. Se agregaron las anotaciones @Service para crear el bean de esta clase

![alt text](https://github.com/diego2097/lab3-arsw/blob/master/Grammar-Checker/img/1.EnglishChecker.PNG)

Clase SpellChecker. Se agregaron las anotaciones @Service para crear el bean de esta clase

![alt text](https://github.com/diego2097/lab3-arsw/blob/master/Grammar-Checker/img/1.SpellChecker.PNG)

# Parte 2 

Se cambia la notacion qualifier para que inyecte la implementacion de english 
![alt text](https://github.com/diego2097/lab3-arsw/blob/master/Grammar-Checker/img/parte2.english.PNG)
