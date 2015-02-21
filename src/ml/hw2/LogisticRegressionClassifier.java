package ml.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class LogisticRegressionClassifier{

	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;

//	private static String directoryPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train";
//	private static String directoryTestPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/test";
	Random rand = null;

	/**
	 * Timer function to check the time taken
	 * by a function
	 */
	public static void timer()
	{
		if(phase == 0) {
			startTime = System.currentTimeMillis();
			phase = 1;
		} else {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			System.out.println("\nTime: " + elapsedTime + " msec.");
			//			memory();
			phase = 0;
		}
	}

	/**
	 * Used to assign the weights to every word in the vocab.
	 * @param path
	 * @return
	 */
	public HashMap<String,Double> weightsAssignment(String path,boolean stopWordCheck){
		HashMap<String, Double> weightMap = new HashMap<String, Double>();
		Utilities util = new Utilities();
		rand = new Random();
		Set<String> vocab = util.makeVocab(path,stopWordCheck);
		//		System.out.println(vocab.size());
		for(String word : vocab){
			double weight = -1 + (rand.nextDouble()*(1+1));
			weightMap.put(word, weight);
		}

		return weightMap;

	}

	/**
	 * HashMap that returns the count of all the words of all files 
	 * @param file
	 * @return
	 */

	public HashMap<Integer,HashMap<String,Integer>> wordCountInEachFile(File file, boolean stopWordCheck){
		int fileCount = 0;
		Utilities util = new Utilities();
		HashMap<Integer,HashMap<String,Integer>> wordCountInAllFiles = new HashMap<Integer, HashMap<String,Integer>>();
		for(File f : file.listFiles()){
			HashMap<String,Integer> wordCount = util.wordCount(f, stopWordCheck);
			wordCountInAllFiles.put(fileCount, wordCount);
			fileCount++;
		}

		return wordCountInAllFiles;
	}

	/**
	 * Function to calculate the count of a particular word in a particular file
	 * Used when passing particular a particular key and looped over all files
	 * @param key - the word
	 * @param file - individual files
	 * @return
	 */
	public int countOfKeyInFile(String key, File file, boolean stopWordCheck){
		//		int count = 0;
		System.out.println(file.getName());
		System.out.println("Inside countO");
		Utilities util = new Utilities();
		HashMap<String,Integer> wordCount = util.wordCount(file, stopWordCheck);

		if(wordCount.containsKey(key)){
			return wordCount.get(key);
		}
		else{
			return 0;
		}
	}


	/**
	 * Function to get all the probability
	 * 
	 */

	public double getProbability(HashMap<String,Double> weightMap,HashMap<String,Integer> countMap, File file){
		//		System.out.println("Inside probabilty generation");
		double sum = 0.0;
		for(String key : countMap.keySet()){
			sum = sum + (weightMap.get(key) * countMap.get(key)); 
		}

		sum = 1 + Math.exp(sum);
		//		System.out.println("probability generation success");
		return (1/sum);	
	}



	/**
	 * RegressionLearn
	 * @param path
	 */
	public HashMap<String,Double> regressionLearn(String path, boolean stopWordCheck, double lembda, double learningRate, int noOfIterations){

//		double learningRate = 0.005;
//		double lembda = 0.5;

		HashMap<String, Double> weightMap = weightsAssignment(path, stopWordCheck);

		HashMap<Integer,HashMap<String,Integer>> wordHamCount = null;
		HashMap<Integer,HashMap<String,Integer>> wordSpamCount = null;
		HashMap<Integer,HashMap<String,Integer>> wordCount = null;
		HashMap<String,HashMap<Integer,Double>> probabilityMap = new HashMap<String, HashMap<Integer,Double>>();

		File directory = new File(path);
		File[] files = directory.listFiles();

		if(files[0].getName().charAt(0) == '.'){

			wordHamCount = wordCountInEachFile(files[1], stopWordCheck);
			wordSpamCount = wordCountInEachFile(files[2], stopWordCheck);
		}
		else{
			wordHamCount = wordCountInEachFile(files[0], stopWordCheck);
			wordSpamCount = wordCountInEachFile(files[1], stopWordCheck);
		}


		/*
		 * Calculating the Probability for each file
		 * and storing it in a map.
		 */

		for(File f: files){
			if(f.getName().charAt(0) != '.'){
				HashMap<Integer,Double> probTempMap = new HashMap<Integer, Double>();
				if(f.getName().equalsIgnoreCase("ham")){
					wordCount = wordHamCount;
				}
				else{
					wordCount = wordSpamCount;
				}
				for(int i=0 ; i < f.listFiles().length ; i++){
					double prob = getProbability(weightMap,wordCount.get(i), f);
					probTempMap.put(i, prob);
				}

				probabilityMap.put(f.getName(), probTempMap);
			}
		}

		/*
		 * Regression Learning starts from here
		 */

		for(int k=0 ; k<=noOfIterations; k++){
			for(String word : weightMap.keySet()){

				double result = 0.0;
				double weight = weightMap.get(word);
				double y = 0;
				for(String f: new String[]{"spam","ham"}){
					//if(f.getName().charAt(0) != '.'){
					if(f.equalsIgnoreCase("ham")){
						wordCount = wordHamCount;
						y = 1;
					}
					else{
						wordCount = wordSpamCount;

						y = 0;
					}
					//}


					for(Integer fileNumber : wordCount.keySet()){
						//					File[] filesInFolder = f.listFiles();
						//					if(f.getName().charAt(0) != '.'){
						//						for(int i=0; i< filesInFolder.length ; i++){
						Integer totalWord = wordCount.get(fileNumber).get(word);
						if (totalWord == null){
							totalWord = 0;
						}

						result += totalWord * (y - probabilityMap.get(f).get(fileNumber));
					}


				}

				weight = weight + (learningRate * result) - (learningRate*lembda*weight);
				weightMap.put(word,weight);
			}

		}
		return weightMap;
	}


	public String applyLogisticRegression(HashMap<String, Double> weightMapLearned, File file, boolean stopWordCheck) throws FileNotFoundException{
		double weightZero = 0.1;
		double result = 0.0;
		double weightCurrent = 0.0;
		int countOfWord = 0;

		HashMap<String, Integer> wordCountMap = new Utilities().wordCount(file, stopWordCheck);

		//calculating words in each file
		ArrayList<String> words = new Utilities().wordsInFile(file);

		for(String word: words){
			if(weightMapLearned.containsKey(word)){
				weightCurrent = weightMapLearned.get(word);
			}
			if(wordCountMap.containsKey(word)){
				countOfWord = wordCountMap.get(word);
			}
			result += (weightCurrent * countOfWord);
		}

		if(weightZero + result > 0){
			return "ham";
		}
		else{
			return "spam";
		}

	}

	public void run(String learnDirectoryPath, String testDirectoryPath, boolean stopWordCheck, double lembda, int noOfIterations){
		int success = 0;
		int total = 0;
		double learningRate = 0.01;
		HashMap<String,Double> weightMapLearned = new LogisticRegressionClassifier().regressionLearn(learnDirectoryPath, stopWordCheck, lembda, learningRate, noOfIterations);
		File file = new File(testDirectoryPath);
		File[] files = file.listFiles();
		for(File f: files){
			if(f.getName().charAt(0) != '.'){
				for(File classFile: f.listFiles()){
					if(classFile.isFile()){
						String result;
						try {
							result = new LogisticRegressionClassifier().applyLogisticRegression(weightMapLearned,classFile, stopWordCheck);

							if(result.equals(f.getName()))
								success++;
							total++;
						}

						catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} 

				}
				System.out.println("Accuracy for " + f.getName() + " " +  (double)success/total);
			}
		}

	}

	//	public static void main(String[] args) {	
	//		int success = 0;
	//		int total = 0;
	//		String stopWordPrint = args[0].toString();
	//		boolean stopWords;
	//
	//		if(stopWordPrint.equalsIgnoreCase("Yes")){
	//			stopWords = Boolean.TRUE;
	//		}
	//		else{
	//			stopWords = Boolean.FALSE;
	//		}
	//
	//		//HashMap<String,Double> weights = new LogisticRegressionClassifier().weightsAssignment(directoryPath, stopWords);
	//		//System.out.println(weights.size());
	//		timer();
	//		HashMap<String,Double> weightMapLearned = new LogisticRegressionClassifier().regressionLearn(directoryPath, stopWords);
	//
	//
	//		File file = new File(directoryTestPath);
	//		File[] files = file.listFiles();
	//		for(File f: files){
	//			if(f.getName().charAt(0) != '.'){
	//				for(File classFile: f.listFiles()){
	//					if(classFile.isFile()){
	//						String result;
	//						try {
	//							result = new LogisticRegressionClassifier().applyLogisticRegression(weightMapLearned,classFile, stopWords);
	//
	//							if(result.equals(f.getName()))
	//								success++;
	//							total++;
	//						}
	//
	//						catch (FileNotFoundException e) {
	//							e.printStackTrace();
	//						}
	//					} 
	//
	//				}
	//				System.out.println("Accuracy " + (double)success/total);
	//			}
	//		}
	//		timer();
	//	}
}
