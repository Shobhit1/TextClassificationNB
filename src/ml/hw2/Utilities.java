package ml.hw2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utilities {
	String directoryPathHam = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/ham";
	String directoryPathSpam  = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/spam";

	HashMap<String, HashMap<String,Integer>> tokenHashMap = new HashMap<>();
	
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
		return mapForWordCount;
	}
	/*
	public List<String> vocabHam(){
		File dirHam = new File(directoryPathHam);

		File[] filesHam = dirHam.listFiles();
		List<String> vocabHam = new ArrayList<>();

		for(File f: filesHam){
			if(f.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(f);
					while(scan.hasNext()){
						vocabHam.addAll(Arrays.asList(scan.next().split("[^a-zA-Z]")));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		return vocabHam;
	}


	public List<String> vocabSpam(){
		File dirSpam = new File(directoryPathSpam);

		File[] filesSpam = dirSpam.listFiles();
		List<String> vocabSpam = new ArrayList<>();

		for(File f: filesSpam){
			if(f.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(f);
					while(scan.hasNext()){
						vocabSpam.addAll(Arrays.asList(scan.next().split("[^a-zA-Z]")));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		return vocabSpam;
	}

	public HashMap<String, Integer> countDocsForEachClass(){
		File dirHam = new File(directoryPathHam);
		File dirSpam = new File(directoryPathSpam);
		File[] filesHam = dirHam.listFiles();
		File[] filesSpam = dirSpam.listFiles();

		HashMap<String, Integer> mapForCount = new HashMap<String, Integer>();

		mapForCount.put("ham", filesHam.length);
		mapForCount.put("spam", filesSpam.length);

		return mapForCount;
	}
	 */
	public static void main(String[] args) {
		Set<String> vocab = new Utilities().makeVocab("/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train");
		System.out.println(vocab.toString());
		System.out.println(vocab.size());

		//		HashMap<String, Integer> count = new Utilities().countDocsForEachClass();

		//		System.out.println(count.get("ham") + count.get("spam"));


	}

}
