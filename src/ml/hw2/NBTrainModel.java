package ml.hw2;

import java.util.HashMap;
import java.util.Set;

public class NBTrainModel {
	double[] prior;
//	double conditionalProb;
	HashMap<String,HashMap<String, Double>> conditionalProbMap;
	Set<String> vocab;
	
	public NBTrainModel(double[] prior, HashMap<String, HashMap<String, Double>> conditionalProbMap, Set<String> vocab) {
		// TODO Auto-generated constructor stub
		this.prior = prior;
		this.conditionalProbMap = conditionalProbMap;
		this.vocab = vocab;
		
	}
	
	public double[] getPrior() {
		return prior;
	}
	public void setPrior(double[] prior) {
		this.prior = prior;
	}
	public Set<String> getVocab() {
		return vocab;
	}
	public void setVocab(Set<String> vocab) {
		this.vocab = vocab;
	}

	public HashMap<String, HashMap<String, Double>> getConditionalProbMap() {
		return conditionalProbMap;
	}

	public void setConditionalProbMap(HashMap<String, HashMap<String, Double>> conditionalProbMap) {
		this.conditionalProbMap = conditionalProbMap;
	}
	
	
}
