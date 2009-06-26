package aima.search.informed.ga;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import aima.search.framework.GoalTest;
import aima.search.framework.Metrics;
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
	private final int individualLength;
	private final Character[] finiteAlphabet;
	private final double mutationProbability;
	private final Random random = new Random();
	//
	int helpArray[] = new int[81];
     
	
	
	public GeneticAlgorithm(int individualLength,
			Set<Character> finiteAlphabet, double mutationProbability, int[] helpArrayp) {
		this.helpArray = helpArrayp;
		this.individualLength = individualLength;
		this.finiteAlphabet = finiteAlphabet
				.toArray(new Character[finiteAlphabet.size()]);
		this.mutationProbability = mutationProbability;
		assert (this.mutationProbability >= 0.0 && this.mutationProbability <= 1.0);
	}

	// function GENETIC-ALGORITHM(population, FITNESS-FN) returns an individual
	// inputs: population, a set of individuals
	// FITNESS-FN, a function that measures the fitness of an individual
	public String geneticAlgorithm(Set<String> population,
			FitnessFunction fitnessFn, GoalTest goalTest) {
		String bestIndividual = null;

		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		// repeat
		int cnt = 0;
		int contador = 0;
		do {
			contador++;
			bestIndividual = ga(population, fitnessFn);
			cnt++;
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
			FitnessFunction fitnessFn, int iterations) {
		String bestIndividual = null;

		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		// repeat
		// until some individual is fit enough, or enough time has elapsed
		for (int i = 0; i < iterations; i++) {
			bestIndividual = ga(population, fitnessFn);
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

	private String ga(Set<String> population, FitnessFunction fitnessFn) {
		// new_population <- empty set
		Set<String> newPopulation = new HashSet<String>();

		// loop for i from 1 to SIZE(population) do
		for (int i = 0; i < population.size(); i++) {
			// x <- RANDOM-SELECTION(population, FITNESS-FN)
			String x = randomSelection(population, fitnessFn);
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
		// population <- new_population
		population.clear();
		population.addAll(newPopulation);

		return retrieveBestIndividual(population, fitnessFn);
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
		//System.out.println(x);
        //System.out.println(y);
        char xArray[] = new char [81];
        char yArray[] = new char [81];
        char arrayResultante[] = new char[81];
        String stringResultante = new String();
        

        xArray = x.toCharArray();
        yArray = y.toCharArray();
        
        //System.out.println(xArray);
        //System.out.println(xArray);
        
        //generar un numero aleatorio para el punto de cruce
        // solo pueden ser en los puntos de corte validos
        
        int c = randomCruce(individualLength);
        
        //System.out.printf("punto de cruce %d\n", c);
        
        // realizar el cruce en el punto de cruce
        for (int i = 0 ; i < 81 ; i++){
                if (i <= c )
                        arrayResultante[i] = xArray[i];
                else
                        arrayResultante[i] = yArray[i];
        }
        
        
        //System.out.println(arrayResultante);
        //convierte a String lo que se obtuvo en el cruce
        stringResultante = String.copyValueOf(arrayResultante);
        
        //System.out.println(stringResultante);
        
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
	     int randomRegion;

         int casilla_1 = 0;
         int casilla_2 = 0;
         char individualArray[] = new char[81];
         char aux;
         int region_valida; // 0-8;
         String string_mutado = new String();
         int cont_vacios = 0;
         int valido = 0;
         int encontro_region=0;


         //verifciar que exista al menos una region para mutar
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

         if (valido == 1){

                 //detectar region a mutar, buscar region valida
                 while(encontro_region == 0){

                         cont_vacios = 0;

                         //generar numero aleatoria de region y verificar que sea valida
                         //para la mutacion
                         randomRegion = random.nextInt(9);

                         if (randomRegion==0){

                                 for(int i = 0 ; i < 9 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9)]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9)]) != 0);
                                 }
                         }
                         if (randomRegion==1){

                                 for(int i = 9 ; i < 18 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 9]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 9]) != 0);
                                 }
                         }
                         if (randomRegion==2){

                                 for(int i = 18 ; i < 27 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 18]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 18]) != 0);
                                 }
                         }
                         if (randomRegion==3){

                                 for(int i = 27 ; i < 36 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 27]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 27]) != 0);
                                 }
                         }
                         if (randomRegion==4){

                                 for(int i = 36 ; i < 45 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 36]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 36]) != 0);
                                 }
                         }
                         if (randomRegion==5){

                                 for(int i = 45 ; i < 54 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 45]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 45]) != 0);
                                 }
                         }
                         if (randomRegion==6){

                                 for(int i = 54 ; i < 63 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 54]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 54]) != 0);
                                 }
                         }
                         if (randomRegion==7){

                                 for(int i = 63 ; i < 72 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 63]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 63]) != 0);
                                 }
                         }
                         if (randomRegion==8){

                                 for(int i = 72 ; i < 81 ; i++)
                                         if (helpArray[i] == 0)cont_vacios++;

                                 if (cont_vacios >= 2){
                                         encontro_region = 1;
                                         while ((helpArray[casilla_1 = random.nextInt(9) + 72]) != 0);
                                         while ((helpArray[casilla_2 = random.nextInt(9) + 72]) != 0);
                                 }

                         }


                 }


                 individualArray = individual.toCharArray();

                 //se realiza la mutacion
                 aux = individualArray[casilla_1];
                 individualArray[casilla_1] = individualArray[casilla_2];
                 individualArray[casilla_2] = aux;


                 string_mutado = String.copyValueOf(individualArray);

                 return string_mutado;
         }


         return individual;
         /*
         StringBuffer mutInd = new StringBuffer(individual);

         int posOffset = randomOffset(individualLength);
         int charOffset = randomOffset(finiteAlphabet.length);

         mutInd.setCharAt(posOffset, finiteAlphabet[charOffset]);

         return mutInd.toString();*/		 

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

	private int randomOffset(int length) {
		return random.nextInt(length);
	}
}