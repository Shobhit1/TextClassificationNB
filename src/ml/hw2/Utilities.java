package ml.hw2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utilities {
	String directoryPathHam = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/ham";
	String directoryPathSpam  = "/Users/shobhitagarwal/Dropbox/UTD/Sem-2/Machine Learning/Project/Project 2/train/spam";


	public List<String> makeVocab(){

		File dirHam = new File(directoryPathHam);
		File dirSpam = new File(directoryPathSpam);
		File[] filesHam = dirHam.listFiles();
		File[] filesSpam = dirSpam.listFiles();
		List<String> vocab = new ArrayList<>();
		//		List<String> vocabSpam = new ArrayList<>();

		for(File f : filesHam){
			if(f.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(f);
					while(scan.hasNext()){
						vocab.addAll(Arrays.asList(scan.next().split("[^a-zA-Z]")));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}


			}
		}

		for(File fileSpam : filesSpam){
			if(fileSpam.isFile()){
				Scanner scan = null;
				try {
					scan = new Scanner(fileSpam);
					while(scan.hasNext()){
						vocab.addAll(Arrays.asList(scan.next().split("[^a-zA-Z]")));
					}
				} catch (Exception e) {
					// TODO: handle exception
				} 
			}
		}

		return vocab;

		//		System.out.println(vocab.toString());
		//		System.out.println(vocab.size());


	}

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

	public static void main(String[] args) {
		List<String> vocab = new Utilities().makeVocab();

		System.out.println(vocab.size());

		HashMap<String, Integer> count = new Utilities().countDocsForEachClass();

		System.out.println(count.get("ham") + count.get("spam"));


	}

}
