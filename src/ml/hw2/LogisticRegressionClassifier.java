package ml.hw2;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class LogisticRegressionClassifier{

	private static String directoryPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train";
	//	private static String directoryTestPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/test";
	Random rand = null;


	/**
	 * Used to assign the weights to every word in the vocab.
	 * @param path
	 * @return
	 */
	public HashMap<String,Double> weightsAssignment(String path){
		HashMap<String, Double> weightMap = new HashMap<String, Double>();
		Utilities util = new Utilities();
		rand = new Random();
		Set<String> vocab = util.makeVocab(path);
		System.out.println(vocab.size());
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

	public HashMap<Integer,HashMap<String,Integer>> wordCountInEachFile(File file){
		int fileCount = 0;
		Utilities util = new Utilities();
		HashMap<Integer,HashMap<String,Integer>> wordCountInAllFiles = new HashMap<Integer, HashMap<String,Integer>>();
		for(File f : file.listFiles()){
			HashMap<String,Integer> wordCount = util.wordCount(f);
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
	public int countOfKeyInFile(String key, File file){
		//		int count = 0;
		System.out.println(file.getName());
		System.out.println("Inside countO");
		Utilities util = new Utilities();
		HashMap<String,Integer> wordCount = util.wordCount(file);

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
	public void regressionLearn(String path){
		double learningRate = 0.01;
		double lembda = 0.01;

		//		Utilities util = new Utilities();
		//		Set<String> vocab = util.makeVocab(path);

		HashMap<String, Double> weightMap = weightsAssignment(path);
		File directory = new File(path);
		File[] files = directory.listFiles();

		HashMap<Integer,HashMap<String,Integer>> wordHamCount = wordCountInEachFile(files[1]);
		HashMap<Integer,HashMap<String,Integer>> wordSpamCount = wordCountInEachFile(files[2]);
		HashMap<Integer,HashMap<String,Integer>> wordCount = null;
		HashMap<String,HashMap<Integer,Double>> probabilityMap = new HashMap<String, HashMap<Integer,Double>>();

		for(File f: files){
			if(f.getName().charAt(0) != '.'){
				HashMap<Integer,Double> probTempMap = new HashMap<Integer, Double>();
				//			double prob = 0.0;
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


		for(String word : weightMap.keySet()){
			double result = 0;
			double weight = weightMap.get(word);

			for(File f: files){
				if(f.getName().equalsIgnoreCase("ham")){
					wordCount = wordHamCount;
				}
				else{
					wordCount = wordSpamCount;
				}
				File[] filesInFolder = f.listFiles();
				if(f.getName().charAt(0) != '.'){
					for(int i=0; i< filesInFolder.length ; i++){
						String key = f.getName();
						Integer totalWord = wordCount.get(i).get("fileWord");

						result += totalWord * (1 - probabilityMap.get(f.getName()).get(i));
					}
				}
			}

			weight = weight + learningRate * result - (learningRate*lembda*weight);
			weightMap.put(word,weight);
		}
		//			for(File f : files){
		//				if(f.getName().charAt(0) != '.'){
		//
		//					if(f.getName().equalsIgnoreCase("ham")){
		//						wordCount = wordHamCount;
		//					}
		//					else{
		//						wordCount = wordSpamCount;
		//					}
		//					for(int i=0 ; i < f.listFiles().length ; i++){
		//						countX = wordCount.get(i).get(word);
		//						if(countX == null){
		//							countX = 0;
		//						}
		//						prob = getProbability(weightMap,wordCount.get(i), f);
		//
		//						if(f.getName().equalsIgnoreCase("spam")){
		//							countY = 1;
		//						}
		//						else{
		//							countY = 0;
		//						}
		//		result = result + (double)countX*((double)countY - prob);
		//		tempWeightMap.put(word, (weightMap.get(word) + ((learningRate)*(result)) - (learningRate * lembda * weightMap.get(word))));
		//}
		//					System.out.println();

		//}

		//			System.out.println("word = " + word + "  - 1st Weight : " + weightMap.get(word) + " - changing to : --> " + tempWeightMap.get(word));
		//weightMap.put(word, tempWeightMap.get(word));

		//}

		System.out.println(weightMap.toString());
	}

	
	
	
	
	public static void main(String[] args) {
		HashMap<String,Double> weights = new LogisticRegressionClassifier().weightsAssignment(directoryPath);

		System.out.println(weights.size());

		new LogisticRegressionClassifier().regressionLearn(directoryPath);



	}
}
