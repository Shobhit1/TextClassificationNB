package ml.hw2;

public class DriverHW2 {
	
	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("Please pass proper arguments.");
			System.exit(0);
		}
		else{
			
			String directoryPathTraining = args[0];
			String directoryPathTest = args[1];
			
			String stopWordPrint = args[2].toString();
			boolean stopWords;

			if(stopWordPrint.equalsIgnoreCase("Yes")){
				stopWords = Boolean.TRUE;
			}
			else{
				stopWords = Boolean.FALSE;
			}
			
			
			int classifierChoice = Integer.parseInt(args[3]);
			int noOfIterations = Integer.parseInt(args[4]);
			double lembda = Double.parseDouble(args[5]);
			
//			String[] classes = {"ham","spam"};
			
			//Naive bayes classifier.
			TrainingNaiveBayes naiveBayes = new TrainingNaiveBayes();
			LogisticRegressionClassifier regression = new LogisticRegressionClassifier();
			
			if(classifierChoice == 1){
				naiveBayes.run(directoryPathTraining, directoryPathTest, stopWords);
			}
			else{
				regression.run(directoryPathTraining, directoryPathTraining, stopWords, lembda, noOfIterations);
			}
			
		}
		
		
		
	}
	
	

}
