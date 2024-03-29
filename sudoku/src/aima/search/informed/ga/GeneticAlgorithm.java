package aima.search.informed.ga;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import aima.search.demos.SudokuDemo;
import aima.search.framework.BestFirstSearch;
import aima.search.framework.GoalTest;
import aima.search.framework.Metrics;
import aima.search.sudoku.SudokuBoard;
import aima.search.sudoku.SudokuFitnessFunction;
import aima.util.Util;

/**
 * Artificial Intelligence A Modern Approach (2nd Edition): Figure 4.17, page 119.
 * 
 * <code>
 * function GENETIC-ALGORITHM(population, FITNESS-FN) returns an individual
 *   inputs: population, a set of individuals
 *           FITNESS-FN, a function that measures the fitness of an individual
 *           
 *   repeat
 *     new_population <- empty set
 *     loop for i from 1 to SIZE(population) do
 *       x <- RANDOM-SELECTION(population, FITNESS-FN)
 *       y <- RANDOM-SELECTION(population, FITNESS-FN)
 *       child <- REPRODUCE(x, y)
 *       if (small random probability) then child <- MUTATE(child)
 *       add child to new_population
 *     population <- new_population
 *   until some individual is fit enough, or enough time has elapsed
 *   return the best individual in population, according to FITNESS-FN
 * --------------------------------------------------------------------------------
 * function REPRODUCE(x, y) returns an individual
 *   inputs: x, y, parent individuals
 *   
 *   n <- LENGTH(x)
 *   c <- random number from 1 to n
 *   return APPEND(SUBSTRING(x, 1, c), SUBSTRING(y, c+1, n))
 * </code>
 * 
 * Figure 4.17 A genetic algorithm. The algorithm is the same as the one diagrammed
 * in Figure 4.15, with one variation: in this more popular version, each mating of 
 * two parents produces only one offspring, not two.
 */

/**
 * @author Ciaran O'Reilly
 * 
 */
public class GeneticAlgorithm {
	//
	protected Metrics metrics = new Metrics();
	protected static final String POPULATION_SIZE = "populationSize";
	protected static final String ITERATIONS = "iterations";

	//
	private final int populationLenght;
	
	private final int individualLength;
	private final Character[] finiteAlphabet;
	private double mutationProbability;
	private final Random random = new Random();
	//
	int helpArray[] = new int[81];
    SudokuBoard board;
    private static long tiempoInicio;
    private static long tiempoFin;
    public static long tiempoMaxEjecucion;
    
    public void setTiempoMaxEjecucion(long tiempo){
    	tiempoMaxEjecucion = tiempo;
    }
    public long getTiempoMaxEjecucion(){
    	return tiempoMaxEjecucion;
    }
    
    public GeneticAlgorithm(int individualLength,
                    Set<Character> finiteAlphabet, double mutationProbabilityInitial, 
                    int[] helpArrayp, SudokuBoard board, int populationLength, long tiempoMaxEjecucion) {
            this.helpArray = helpArrayp;
            this.individualLength = individualLength;
            this.finiteAlphabet = finiteAlphabet
                            .toArray(new Character[finiteAlphabet.size()]);
            this.mutationProbability = mutationProbabilityInitial;
            assert (this.mutationProbability >= 0.0 && this.mutationProbability <= 1.0);
            
            this.board = board;
            this.populationLenght = populationLength;
            setTiempoMaxEjecucion(tiempoMaxEjecucion);
    }


	// function GENETIC-ALGORITHM(population, FITNESS-FN) returns an individual
	// inputs: population, a set of individuals
	// FITNESS-FN, a function that measures the fitness of an individual
	public String geneticAlgorithm(Set<String> population,
			FitnessFunction fitnessFn, GoalTest goalTest, int eliteNindividuos) {
		String bestIndividual = null;
		String bestPreviousIndividual = new String();
		Set<String> elitePopulation = new HashSet<String>();;
		Set<String> newPopulation = null;
		
		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		// repeat
		int cnt = 0;
		int evitarMaxLocal = 0;
		SudokuFitnessFunction fitnessValue = new SudokuFitnessFunction();
		tiempoInicio = System.currentTimeMillis();
		do {
			newPopulation = ga(population, fitnessFn, eliteNindividuos);
			bestIndividual = retrieveBestIndividual(newPopulation, fitnessFn);
			
			if(bestIndividual.compareTo(bestPreviousIndividual) == 0){
				evitarMaxLocal++;
			}else{
				evitarMaxLocal = 0;
			}
			bestPreviousIndividual = bestIndividual;
			
			/*Si el bestIndividual de la poblacion se repitio 100 veces, volvemos
			 *a generar la poblacion(la poblacion elite la mantenemos)*/
			if(evitarMaxLocal >= 100){
				System.out.println("entro a evitar maximos locales, regeneramos la poblacion!\n");
				/*Guardamos la poblacion de elite*/
				elitePopulation = elitismoNindividuos(population, eliteNindividuos, fitnessFn);
				/*Borramos la poblacion completa*/
				population.clear();
				/*Regeneramos la poblacion*/
				population.addAll(this.board.initPopulation(this.board));
				
				/*Removemos N individuos randomicamente para luego agregar la elitePopulation*/
				population = removeNindividuosRandom(population, eliteNindividuos);
				/*Agregamos la poblacion elite*/
				population.addAll(elitePopulation);
				
				evitarMaxLocal = 0;
				
			}
				
			cnt++;
			/*Aqui lo que hacemos es ir disminuyendo la probabilidad de mutacion
			 * mientras van avanzando la creacion de generaciones*/
			if(this.mutationProbability < 0.8){
                this.mutationProbability += 0.001;
                System.out.printf("mutProb = %f, ", this.mutationProbability);
			}
			
			System.out.printf("fValue %f\n", fitnessValue.getValue(bestIndividual));
			
			
			tiempoFin = System.currentTimeMillis();
			long tiempoTrans = ((tiempoFin - tiempoInicio)/1000)/60;
			if(tiempoTrans > getTiempoMaxEjecucion())
				break;
			// until some individual is fit enough, or enough time has elapsed
		} while (!goalTest.isGoalBoard(stringToBoard(bestIndividual)) );
		setIterations(cnt);

		// return the best individual in population, according to FITNESS-FN
		return bestIndividual;
	}

	// function GENETIC-ALGORITHM(population, FITNESS-FN) returns an individual
	// inputs: population, a set of individuals
	// FITNESS-FN, a function that measures the fitness of an individual
	public String geneticAlgorithm(Set<String> population,
			FitnessFunction fitnessFn, int iterations, int eliteNindividuos) {
		String bestIndividual = null;

		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		// repeat
		// until some individual is fit enough, or enough time has elapsed
		for (int i = 0; i < iterations; i++) {
			//bestIndividual = ga(population, fitnessFn, eliteNindividuos);
			
		}
		setIterations(iterations);

		// return the best individual in population, according to FITNESS-FN
		return bestIndividual;
	}

	
	//convierte el string al tablero de sudoku
	public static int [][] stringToBoard(String individual){
		int contador_cadena = 0; 
		int columna, fila;
		char vector_char[] = new char [81];
		int [][] board = new int[9][9];
		
		if(individual != null){
			vector_char = individual.toCharArray();
			
			for (int i = 0 ; i < 9 ;  i=i+3)
				for (int j = 0 ; j < 9 ;  j=j+3)
					for (fila = i ; fila < i+3 ; fila++)
						for (columna = j ; columna < j+3 ; columna++){
							board[fila][columna] = Character.getNumericValue(vector_char[contador_cadena]);
							contador_cadena++;
						}
		}
		return board;
		
	}
	
	
	public void clearInstrumentation() {
		setPopulationSize(0);
		setIterations(0);
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public int getPopulationSize() {
		return metrics.getInt(POPULATION_SIZE);
	}

	public void setPopulationSize(int size) {
		metrics.set(POPULATION_SIZE, size);
	}

	public int getIterations() {
		return metrics.getInt(ITERATIONS);
	}

	public void setIterations(int cnt) {
		metrics.set(ITERATIONS, cnt);
	}

	//
	// PRIVATE METHODS
	//
	private void validatePopulation(Set<String> population) {
		// Require at least 1 individual in population in order
		// for algorithm to work
		assert (population.size() >= 1);
		// String lengths are assumed to be of fixed size,
		// therefore ensure initial populations lengths correspond to this
		for (String individual : population) {
			assert (individual.length() == this.individualLength);
		}
	}

	private Set<String> ga(Set<String> population, FitnessFunction fitnessFn, int eliteNindividuos) {
		// new_population <- empty set
		Set<String> newPopulation = new HashSet<String>();
		Set<String> elitePopulation = new HashSet<String>();

		
		// loop for i from 1 to SIZE(population) do
		for (int i = 0; i < population.size() /*- eliteNindividuos*/; i++) {
			// x <- RANDOM-SELECTION(population, FITNESS-FN)
			String x = randomSelection(population, fitnessFn);
			//String x = retrieveBestIndividual(population, fitnessFn);
			// y <- RANDOM-SELECTION(population, FITNESS-FN)
			String y = randomSelection(population, fitnessFn);
			// child <- REPRODUCE(x, y)
			String child = reproduce(x, y);
			// if (small random probability) then child <- MUTATE(child)
			if (random.nextDouble() <= this.mutationProbability) {
				child = mutate(child);
			}
			
			// add child to new_population
			newPopulation.add(child);
		}
		
		/*Seleccionamos los N mejores de la poblacion anterior*/
		elitePopulation = elitismoNindividuos(population, eliteNindividuos, fitnessFn);
		/*Agregamos la nueva poblacion*/
		population.clear();
		population.addAll(newPopulation);
		/*Removemos N individuos para luego agregar la elitePopulation*/
		population = removeNindividuosRandom(population, eliteNindividuos);
		population.addAll(elitePopulation);

		System.out.println(population.size());

		return population;
	}
	
	private Set<String> removeNindividuosRandom(Set<String> population, int eliteNindividuos){
		
		String[] popArray = population.toArray(new String[population.size()]);
		
		for (int i = 0; population.size() > (this.populationLenght - eliteNindividuos); i++) {
			population.remove(popArray[random.nextInt(popArray.length)]);
		}
		
		return population;
	}
	private Set<String> elitismoNindividuos(Set<String> population, int n, FitnessFunction fitnessFn){
		Set<String> elitePopulation = new HashSet<String>();
		// Determine all of the fitness values
		double[] fValues = new double[population.size()];
		String[] popArray = population.toArray(new String[population.size()]);
		for (int i = 0; i < popArray.length; i++) {
			fValues[i] = fitnessFn.getValue(popArray[i]);
		}
		// Normalize the fitness values
		fValues = Util.normalize(fValues);
		double mayorValue = fValues[0];
		int mayorValueIndice = 0;
		/*Encontramos el nodo con mayor valor de fitness function*/
		for(int j = 0; j < n; j++){
			for (int i = 1; i < fValues.length; i++) {
				if(fValues[i] > mayorValue){
					mayorValue = fValues[i];
					mayorValueIndice = i;
				}
			}
			elitePopulation.add(popArray[mayorValueIndice]);
			fValues[mayorValueIndice] = 0.0;
			mayorValue = 0;
		}
		
		
		return elitePopulation;
	}

	private String randomSelection(Set<String> population,
			FitnessFunction fitnessFn) {
		String selected = null;

		// Determine all of the fitness values
		double[] fValues = new double[population.size()];
		String[] popArray = population.toArray(new String[population.size()]);
		for (int i = 0; i < popArray.length; i++) {
			fValues[i] = fitnessFn.getValue(popArray[i]);
		}

		// Normalize the fitness values
		fValues = Util.normalize(fValues);
		double prob = random.nextDouble();
		double totalSoFar = 0.0;
		for (int i = 0; i < fValues.length; i++) {
			// Are at last element so assign by default
			// in case there are rounding issues with the normalized values
			totalSoFar += fValues[i];
			if (prob <= totalSoFar) {
				selected = popArray[i];
				break;
			}
		}

		// selected may not have been assigned
		// if there was a rounding error in the
		// addition of the normalized values (i.e. did not total to 1.0)
		if (null == selected) {
			// Assign the last value
			selected = popArray[popArray.length - 1];
		}

		return selected;
	}

	// function REPRODUCE(x, y) returns an individual
	// inputs: x, y, parent individuals
	private String reproduce(String x, String y) {
        char xArray[] = new char [81];
        char yArray[] = new char [81];
        char arrayResultante[] = new char[81];
        String stringResultante = new String();
        

        xArray = x.toCharArray();
        yArray = y.toCharArray();
        
        /* Generar un numero aleatorio para el punto de cruce
         solo pueden ser en los puntos de corte validos*/
        int c = randomCruce(individualLength);
        int d = randomCruce(individualLength);
        while(d == c){
        	d = randomCruce(individualLength);
        }
        
        int cruceUnico = random.nextInt(10);
        /*Aquie basicamente hacemos con probabilidad aproximada al 80%
         * un cruce de tipo simple(un solo punto de cruce)*/
        if(cruceUnico < 8){
        	// realizar el cruce en el punto de cruce
	        for (int i = 0 ; i < 81 ; i++){
	            if (i <= c )
	                    arrayResultante[i] = xArray[i];
	            else 
	            		arrayResultante[i] = yArray[i];
	        }
	        
	        stringResultante = String.copyValueOf(arrayResultante);
        }else{/*y con la otra probabilidad aproximada a 20% hacemos un cruce 
        		de tipo triple(dos puntos de cruce)*/
		     if (d > c){
		        for (int i = 0 ; i < 81 ; i++){
		                if (i <= c )
		                        arrayResultante[i] = xArray[i];
		                else{
		                	if(i <= d)
		                        arrayResultante[i] = yArray[i];
		                	else
		                		arrayResultante[i] = xArray[i];
		                }		
		        }
	        }else{
		        for (int i = 0 ; i < 81 ; i++){
	                if (i <= d )
	                        arrayResultante[i] = xArray[i];
	                else {
	                	if(i <= c)
	                        arrayResultante[i] = yArray[i];
	                	else
	                		arrayResultante[i] = xArray[i];
	                }		
		        }
	        }
		    stringResultante = String.copyValueOf(arrayResultante);
        }

        return stringResultante;
        
	}

	
    private int randomCruce(int length) {
        int randomN;
        randomN = 7;
        int vector[] = {8,17,26,35,44,53,62,71};
                 randomN = random.nextInt(8);
                
        
        return vector[randomN] ;
    }


	
	
	
	private String mutate(String individual) {
         int casilla_1 = 0, casilla_2 = 0, casilla_3 = 0;
         char individualArray[] = new char[81];
         char aux;
         String string_mutado = new String();
         int cont_vacios = 0, valido = 0;

         /*Verifciar que exista al menos una region para mutar en el tablero*/
         for (int i = 0 ; i < 81 ; i++){
                 cont_vacios=0;
                 for(int j = i ; j < i+9 ; j++){
                         if (helpArray[j] == 0)cont_vacios++;
                 }
                 if(cont_vacios >= 2){
                         valido = 1;
                         break;
                 }
                 if (valido == 1)break;
         }
         
         individualArray = individual.toCharArray();

         SudokuFitnessFunction fitness = new SudokuFitnessFunction();
         
         double fitnessValueIndividual = fitness.getValue(individual);
         
	         if (valido == 1){
	                         cont_vacios = 0;
	                         boolean mutateMultiplesRegiones =  random.nextBoolean();
	                         boolean muto = false;
	                         /*Region 1*/
	                         if ( random.nextInt(100) <= this.mutationProbability){
	
	                                 for(int i = 0 ; i < 9 ; i++)
	                                         if (helpArray[i] == 0)cont_vacios++;
	                                 	
	                                 int tipoMutacion = random.nextInt(1);
	                                 
	                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
	                                         while ((helpArray[casilla_1 = random.nextInt(9)]) != 0);
	                                         while ((helpArray[casilla_2 = random.nextInt(9)]) != 0 || (casilla_1 == casilla_2));
	                                         aux = individualArray[casilla_1];
	                                         individualArray[casilla_1] = individualArray[casilla_2];
	                                         individualArray[casilla_2] = aux;
	                                         muto = true;
	                                 }else if (cont_vacios > 2){
		                                     while ((helpArray[casilla_1 = random.nextInt(9)]) != 0);
		                                     while ((helpArray[casilla_2 = random.nextInt(9)]) != 0 || (casilla_1 == casilla_2));
		                                     while ((helpArray[casilla_3 = random.nextInt(9)]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
		                                     
		                                     aux = individualArray[casilla_1];
		                                     individualArray[casilla_1] = individualArray[casilla_2];
		                                     individualArray[casilla_2] = individualArray[casilla_3];
		                                     individualArray[casilla_3] = aux;
		                                     muto = true;
	                                 }
	                         }
	                         cont_vacios = 0;
                             casilla_1 = casilla_2 = casilla_3 = 0;	                         
	                         /*Region 2*/
                             if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 9 ; i < 18 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 9]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 9]) != 0 || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
		                                	     while ((helpArray[casilla_1 = random.nextInt(9)+ 9]) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)+ 9]) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)+ 9]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 
		                             }
		                         }
                             }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	    
                    	     /*Region 3*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 18 ; i < 27 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 18]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 18]) != 0 || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)+ 18]) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)+ 18]) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)+ 18]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	        
                    	     /*Region 4*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 27 ; i < 36 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 27]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 27]) != 0 || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)+ 27]) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)+ 27]) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)+ 27]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	      
                    	     /*Region 5*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 36 ; i < 45 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 36]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 36]) != 0 || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)+ 36]) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)+ 36]) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)+ 36]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	
                    	     /*Region 6*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 45 ; i < 54 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 45]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 45]) != 0  || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9) + 45]) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9) + 45]) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9) + 45]) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
			                                 
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	   
                    	     /*Region 7*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 54 ; i < 63 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 54]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 54]) != 0  || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)]+ 54) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)]+ 54) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)]+ 54) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;
                    	     /*Region 8*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 63 ; i < 72 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 63]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 63]) != 0 || (casilla_1 == casilla_2));
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)]+ 63) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)]+ 63) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)]+ 63) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
		                                 
		                                 }
		                         }
                    	     }
	                         cont_vacios = 0;
                    	     casilla_1 = casilla_2 = casilla_3 = 0;	      
                    	     /*Region 9*/
                    	     if( (!muto) || (muto && mutateMultiplesRegiones) ){
		                         if (random.nextInt(100) <= this.mutationProbability){
		
		                                 for(int i = 72 ; i < 81 ; i++)
		                                         if (helpArray[i] == 0)cont_vacios++;
		
		                                 int tipoMutacion = random.nextInt(1);
	
		                                 if (cont_vacios == 2 || (cont_vacios > 2 && tipoMutacion == 0)){
		                                         while ((helpArray[casilla_1 = random.nextInt(9) + 72]) != 0);
		                                         while ((helpArray[casilla_2 = random.nextInt(9) + 72]) != 0  || (casilla_1 == casilla_2));
		                                         //se realiza la mutacion
		                                         aux = individualArray[casilla_1];
		                                         individualArray[casilla_1] = individualArray[casilla_2];
		                                         individualArray[casilla_2] = aux;
		                                         muto = true;
		                                 }else if (cont_vacios > 2){
			                                     while ((helpArray[casilla_1 = random.nextInt(9)]+ 72) != 0);
			                                     while ((helpArray[casilla_2 = random.nextInt(9)]+ 72) != 0 || (casilla_1 == casilla_2));
			                                     while ((helpArray[casilla_3 = random.nextInt(9)]+ 72) != 0 || (casilla_3 == casilla_2 && casilla_3 == casilla_1 && casilla_2 == casilla_1));
			                                     
			                                     aux = individualArray[casilla_1];
			                                     individualArray[casilla_1] = individualArray[casilla_2];
			                                     individualArray[casilla_2] = individualArray[casilla_3];
			                                     individualArray[casilla_3] = aux;
			                                     muto = true;
			                                 
		                                 }
		
		                         }
                    	     }
	                 string_mutado = String.copyValueOf(individualArray);
	         }
       
         return string_mutado;
}

	private String retrieveBestIndividual(Set<String> population,
			FitnessFunction fitnessFn) {
		String bestIndividual = null;
		double bestSoFarFValue = Double.MIN_VALUE;

		for (String individual : population) {
			double fValue = fitnessFn.getValue(individual);
			if (fValue > bestSoFarFValue) {
				bestIndividual = individual;
				bestSoFarFValue = fValue;
			}
		}

		return bestIndividual;
	}


}