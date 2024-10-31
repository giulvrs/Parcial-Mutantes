# Parcial-Mutantes
Desarrollo de Software - 3K9 - Ranzuglia Giuliana 50141

# Consignas:
Crear un programa con un método o función con la siguiente firma:

boolean isMutant(String[] dna);

En donde se recibe como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN. Un humano es mutante si tiene más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical.

![parcial](https://github.com/user-attachments/assets/bce03e99-02f9-4ec1-9cb9-a61767fef8bd)

Desafíos:

- Nivel 1:
Programa  en java spring boot que cumpla con el método pedido por Magneto utilizando una arquitectura en capas de controladores, servicios y repositorios.

- Nivel 2:
Crear una API REST, hostear esa API en un cloud computing libre (Render), crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato: POST → /mutant/ { “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}. En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden

- Nivel 3:
Anexar una base de datos en H2, la cual guarde los ADN’s verificados con la API. Solo 1 registro por ADN.
Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4} Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1 millón de peticiones por segundo). Utilizar Jmeter. Test-Automáticos, Code coverage > 80%, Diagrama de Secuencia / Arquitectura del sistema.

# Resolución:

En el ejercicio debemos evaluar una secuencia de ADN presentada como un arrary de strings que solo permite cuatro caracteres; A, C, G o T. Se considera que el ADN es mutante al contener mas de una secuencia de estos cuatro caracteres consecutivos en cualquier dirección. 

- Endpoints

  1. POST /mutant:

   {
    "dna": [
        "ATATGA",
        "AAGGGA",
        "TTTTGT",
        "ATCCAT",
        "CCAAGA",
        "GTGATT"]}

  Se analizan las secuencias dadas para determinar si la persona es mutante.

  2. GET /stats:

  Según los resultados anteriormente almacenados nos dice la relacón proporcional entre humanos y mutantes, dividiendo 
  mutantes entre humanos.



