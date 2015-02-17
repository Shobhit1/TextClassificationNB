package ml.hw2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utilities {
	String directoryPathHam = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/ham";
	String directoryPathSpam  = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/spam";
	
	public static HashMap<String, HashMap<String,Integer>> tokenHashMap = new HashMap<>();
	public static int countSpam = 0;
	public static int countHam = 0;
	
	
	public Set<String> makeVocab(String directoryPath){

		Set<String> vocab = new HashSet<>();
		HashMap<String, Integer> mapForWordCount = null;
		for(File classFile : new File(directoryPath).listFiles()){
			if(classFile.getName().charAt(0) != '.'){

				mapForWordCount = wordCount(classFile);
				tokenHashMap.put(classFile.getName(), mapForWordCount);
			}
		}

		for(String s  : tokenHashMap.keySet()){
			if(s.equalsIgnoreCase("ham")){
				countHam++;
			}
			else{
				countSpam++;
			}
			
			
			Set<String> temp = tokenHashMap.get(s).keySet();
			
			vocab.addAll(temp);
		}

		return vocab;
	}


	/**
	 * 
	 * Map for word count
	 * @param directoryPath
	 * @return
	 */

	public HashMap<String, Integer> wordCount(File file){
		//File file = new File(directoryPath);
		int totalWordsInClass = 0;
		HashMap<String, Integer> mapForWordCount = new HashMap<String, Integer>();
		File[] files = file.listFiles();
		for(File f : files){
			if(f.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(f);
					scan.useDelimiter("[^a-zA-Z]+");
					while(scan.hasNext()){
						String word = scan.next();
						totalWordsInClass++;
						if(mapForWordCount.containsKey(word)){
							mapForWordCount.put(word,mapForWordCount.get(word) + 1);
						}
						else{
							mapForWordCount.put(word, 1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		mapForWordCount.put("totalWordsInClass", totalWordsInClass);
		return mapForWordCount;
	}
	public static void main(String[] args) {
		Set<String> vocab = new Utilities().makeVocab("/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train");
		System.out.println(vocab.toString());
		System.out.println(vocab.size());

		//		HashMap<String, Integer> count = new Utilities().countDocsForEachClass();

		//		System.out.println(count.get("ham") + count.get("spam"));


	}

}
