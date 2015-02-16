package ml.hw2;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingNaiveBayes {
	private Utilities util2;
	
	public void train(String[] classes, ArrayList<String> vocab){
		util2 = new Utilities();
		HashMap<String, Integer> count = util2.countDocsForEachClass();
		
		int noOfFiles = count.get("ham") + count.get("spam");
		
		for(String c : classes){
			int noOfFileEachClass = count.get(c);
			double prior = noOfFileEachClass/noOfFiles;
			
			if(c.equalsIgnoreCase("ham")){
				ArrayList<String> wordsEachClass = new ArrayList<>(util2.vocabHam());
			}
			else{
				ArrayList<String> wordsEachClass = new ArrayList<>(util2.vocabSpam());
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		Utilities util = new Utilities();
//		ArrayList<String> vocab = new ArrayList<>(util.makeVocab()); 
		String[] classVar = {"ham", "spam"};
		new TrainingNaiveBayes().train(classVar,vocab);
	}

}
