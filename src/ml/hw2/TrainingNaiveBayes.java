package ml.hw2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TrainingNaiveBayes {
	//	private Utilities util2;
	private static String directoryPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train";
	private static String directoryTestPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/test";


	//	public NBTrainModel train(String[] classes, Set<String> vocab){
	public NBTrainModel train(String[] classes){
		Utilities util = new Utilities();
		Set<String> vocab = util.makeVocab(directoryPath);
		HashMap<String,HashMap<String,Double>> conditionalProbMapFinal = new HashMap<>();
		
		int hamNoOfFiles = util.countHam;
		int SpamNoOfFiles = util.countSpam;
		int totalFileCount = hamNoOfFiles + SpamNoOfFiles;

		double[] prior = {0,0};

		for(String c : classes){
			HashMap<String,Double> conditionalProbMap = new HashMap<String, Double>();
			if(c.equalsIgnoreCase("ham")){
				prior[0] = (double)hamNoOfFiles/totalFileCount;
			}
			else{
				prior[1] = (double)SpamNoOfFiles/totalFileCount;
			}
			
			conditionalProbMap.put("dummy",(double) 1 / (double)((util.tokenHashMap.get(c).get("totalWordsInClass")) + vocab.size()));
			
			
			
			for(String word : vocab){
				Integer wordCount = util.tokenHashMap.get(c).get(word);
				if(wordCount == null){
					wordCount = 0;
				}
				double conditionProb = (double)(wordCount + 1) / (double)((util.tokenHashMap.get(c).get("totalWordsInClass")) + vocab.size());

				conditionalProbMap.put(word, conditionProb);
			}
			
			conditionalProbMapFinal.put(c, conditionalProbMap);
		}

		NBTrainModel nbModel = new NBTrainModel(prior, conditionalProbMapFinal, vocab);

		return nbModel;
	}


	public String applyNB(NBTrainModel nbTrainModel, String[] classes, File file) {
		ArrayList<Double> scores = new ArrayList<>();
		double score = 0;
		ArrayList<String> words = new Utilities().wordsInFile(file);
		for(String c : classes){
			if(c.equalsIgnoreCase("ham")){
				score = Math.log(nbTrainModel.getPrior()[0]);
			}
			else{
				score = Math.log(nbTrainModel.getPrior()[1]);
			}

			for(String word : words){
				if(nbTrainModel.getConditionalProbMap().get(c).containsKey(word)){
					score = score + Math.log(nbTrainModel.getConditionalProbMap().get(c).get(word));
				}
				else{
					score = score + Math.log(nbTrainModel.getConditionalProbMap().get(c).get("dummy"));
				}
			}
			scores.add(score);
		}
		if(scores.get(0) > scores.get(1)){
			return "ham";
		}
		else{
			//			scores.get(1);
			return "spam";

		}
	}

	public static void main(String[] args) {
		String[] classes = {"ham","spam"};
		NBTrainModel nb = new TrainingNaiveBayes().train(classes);
		int success = 0;
		int total = 0;
		File file = new File(directoryTestPath);
		File[] files = file.listFiles();
		for(File f: files){
			System.out.println("Class " + f.getName());
			for(File classFile: f.listFiles()){
				if(classFile.isFile()){
					String result = new TrainingNaiveBayes().applyNB(nb, classes, classFile);
					//System.out.print(" - "+result);
					if(result.equals(f.getName()))
						success++;
					total++;
				}
			}
			System.out.println("Accuracy " + (double)success/total);
		}

	}

}
