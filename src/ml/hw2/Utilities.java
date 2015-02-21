package ml.hw2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utilities {


	String directoryPathHam = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/ham";
	String directoryPathSpam  = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/spam";

	static String stopWordsPath = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/stopWords.txt";

	public HashMap<String, HashMap<String,Integer>> tokenHashMap = new HashMap<>();
	public int countSpam = 0;
	public int countHam = 0;


	public Set<String> makeVocab(String directoryPath, boolean stopWordCheck){

		Set<String> vocab = new HashSet<>();
		HashMap<String, Integer> mapForWordCount = null;
		for(File classFile : new File(directoryPath).listFiles()){
			if(classFile.getName().charAt(0) != '.'){

				mapForWordCount = wordCount(classFile, stopWordCheck);
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

	public HashMap<String, Integer> wordCount(File file, boolean stopWordCheck){

		int totalWordsInClass = 0;
		HashMap<String, Integer> mapForWordCount = new HashMap<String, Integer>();

		//adding stop words concept - to be removed later to make it in a different function
		ArrayList<String> stopWords = stopWords(stopWordsPath);

		File[] files = file.listFiles();
		if(files != null){
			for(File f : files){
				if(f.isFile()){
					Scanner scan = null;
					try {
						scan = new Scanner(f);
						scan.useDelimiter("[^a-zA-Z]+");
						while(scan.hasNext()){
							String word = scan.next();
							if(stopWordCheck){
								if(!stopWords.contains(word)){			//stop words
									totalWordsInClass++;

									if(mapForWordCount.containsKey(word)){
										mapForWordCount.put(word,mapForWordCount.get(word) + 1);
									}
									else{
										mapForWordCount.put(word, 1);
									}
								}
							}
							//						}
							else{
								totalWordsInClass++;

								if(mapForWordCount.containsKey(word)){
									mapForWordCount.put(word,mapForWordCount.get(word) + 1);
								}
								else{
									mapForWordCount.put(word, 1);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					scan.close();
					if(file.getName().equalsIgnoreCase("ham")){
						countHam++;
					}
					else{
						countSpam++;
					}
				}
			}
			//
		}

		else{
			if(file.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(file);
					scan.useDelimiter("[^a-zA-Z]+");

					while(scan.hasNext()){
						String word = scan.next();
						if(stopWordCheck){
							if(!stopWords.contains(word)){			//stop words
								totalWordsInClass++;

								if(mapForWordCount.containsKey(word)){
									mapForWordCount.put(word,mapForWordCount.get(word) + 1);
								}
								else{
									mapForWordCount.put(word, 1);
								}
							}
						}
						//						}
						else{
							totalWordsInClass++;

							if(mapForWordCount.containsKey(word)){
								mapForWordCount.put(word,mapForWordCount.get(word) + 1);
							}
							else{
								mapForWordCount.put(word, 1);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//				if(file.getName().equalsIgnoreCase("ham")){
				//					countHam++;
				//				}
				//				else{
				//					countSpam++;
				//				}
			}
		}
		mapForWordCount.put("totalWordsInClass", totalWordsInClass);
		return mapForWordCount;
	}

	/**
	 * Extracting all the words in a file and returning an arrayList of the words
	 * @param file
	 * @return
	 */


	public ArrayList<String> wordsInFile(File file){
		ArrayList<String> wordsInFile = new ArrayList<String>();
		ArrayList<String> stopWords = stopWords(stopWordsPath);			//stop words
		Scanner scan = null;
		try {
			scan = new Scanner(file);
			scan.useDelimiter("[^a-zA-Z]+");
			while(scan.hasNext()){
				String word = scan.next();
				if(!stopWords.contains(word)){						//stop words
					wordsInFile.add(word);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		scan.close();

		return wordsInFile;
	}


	/**
	 * to calculate an array list containing all the stop words
	 * @param path
	 * @return
	 */
	public ArrayList<String> stopWords(String path){
		ArrayList<String> stopWords = new ArrayList<String>();

		Scanner scan = null;
		try {
			File file = new File(path);
			scan = new Scanner(file);
			scan.useDelimiter("\n");
			while(scan.hasNext()){
				String stopWord = scan.next();
				stopWords.add(stopWord);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		scan.close();
		return stopWords;
	}

	public HashMap<String, HashMap<String, Integer>> getTokenHashMap() {
		return tokenHashMap;
	}


	public int getCountSpam() {
		return countSpam;
	}


	public int getCountHam() {
		return countHam;
	}

	public static void main(String[] args) {
		
//		Set<String> vocab = new Utilities().makeVocab("/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train");
//		System.out.println(vocab.toString());
//		System.out.println(vocab.size());

		ArrayList<String> stopWords = new Utilities().stopWords(stopWordsPath);
		System.out.println(stopWords.toString());
		System.out.println(stopWords.size());

	}

}
