package ml.hw2;

import java.util.HashMap;
import java.util.Set;

public class TrainingNaiveBayes {
	private Utilities util2;
	private static String directoryPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train";
	
	public void train(String[] classes, Set<String> vocab){
		
		HashMap<String,Double> conditionalProbMap = new HashMap<>();
		
		int hamNoOfFiles = Utilities.countHam;
		int SpamNoOfFiles = Utilities.countSpam;
		int totalFileCount = hamNoOfFiles + SpamNoOfFiles;
		
		
		
		for(String c : classes){
			if(c.equalsIgnoreCase("ham")){
				double priorHam = hamNoOfFiles/totalFileCount;
			}
			else{
				double priorSpam = SpamNoOfFiles/totalFileCount;
			}
			
			for(String word : vocab){
				int wordCount = Utilities.tokenHashMap.get(c).get(word);
				double conditionProb = (wordCount + 1) / (Utilities.tokenHashMap.get(c).get("totalWordsInClass")) + vocab.size();
				
				conditionalProbMap.put(word, conditionProb);
			}
		}
		
		NBTrainModel nbModel = new N
	}
	
	public static void main(String[] args) {
		Utilities util = new Utilities();
		Set<String> vocab = util.makeVocab(directoryPath);
		
	}

}
