package cloudappMP1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
	Random generator;
	String userName;
	String inputFileName;
	String delimiters = " \t,;.?!-:@[](){}_*/";
	String[] stopWordsArray = { "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
			"yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
			"itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
			"these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
			"do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
			"of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
			"after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
			"further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
			"few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
			"too", "very", "s", "t", "can", "will", "just", "don", "should", "now" };

	void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");
		messageDigest.update(seed.toLowerCase().trim().getBytes());
		byte[] seedMD5 = messageDigest.digest();

		long longSeed = 0;
		for (int i = 0; i < seedMD5.length; i++) {
			longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
		}

		this.generator = new Random(longSeed);
	}

	Integer[] getIndexes() throws NoSuchAlgorithmException {
		Integer n = 10000;
		Integer number_of_lines = 50000;
		Integer[] ret = new Integer[n];
		this.initialRandomGenerator(this.userName);
		for (int i = 0; i < n; i++) {
			ret[i] = generator.nextInt(number_of_lines);
		}
		return ret;
	}

	public MP1(String userName, String inputFileName) {
		this.userName = userName;
		this.inputFileName = inputFileName;
	}

	public String[] process() throws Exception {
		String[] ret = new String[20];

		// Main algorithm:
		// Read the file line by line
		// Get indexes
		// Get the indexed lines in array
		// Split the delimiters by chars
		// For each line split using delimiters
		// Add to wordcount dictionary if not among stopWordsArray
		// Sort the wordcount dictionary
		// Return first 20 of wordcount dictionary

		List<String> titles = new ArrayList<String>();

		// http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file-in-java
		FileReader fr = new FileReader(this.inputFileName);
		BufferedReader textReader = new BufferedReader(fr);
		try {
			String line = textReader.readLine();
			while (line != null) {
				titles.add(line);
				line = textReader.readLine();
			}
		} finally {
			textReader.close();
		}

		Integer[] indexes = this.getIndexes();

		// http://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
		List<String> stopWords = new ArrayList<String>(Arrays.asList(this.stopWordsArray));

		List<String> onlyIndexedTitles = new ArrayList<String>();

		for (Integer i : indexes) {
			onlyIndexedTitles.add(titles.get(i));
		}

		// http://www.mkyong.com/java/how-to-sort-a-map-in-java/
		Map<String, Integer> wordCount = new HashMap<String, Integer>();

		for (String title : onlyIndexedTitles) {
			// http://docs.oracle.com/javase/7/docs/api/java/util/StringTokenizer.html
			StringTokenizer st = new StringTokenizer(title, this.delimiters);
			while (st.hasMoreTokens()) {
				String word = st.nextToken().toLowerCase().trim();
				if (!stopWords.contains(word)) {
					if (!wordCount.containsKey(word))
						wordCount.put(word, 1);
					else
						wordCount.put(word, wordCount.get(word) + 1);
				}
			}
		}

		Map<String, Integer> sortedWordCount = sortByComparator(wordCount);

		Integer i = 0;
		for (Map.Entry<String, Integer> entry : sortedWordCount.entrySet()) {
			if (i < 20) {
				ret[i] = entry.getKey();// + " " +entry.getValue();
				i++;
			} else {
				break;
			}
		}

		return ret;
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("MP1 <User ID>");
		} else {
			String userName = args[0];
			String inputFileName = "./input.txt";
			MP1 mp = new MP1(userName, inputFileName);
			String[] topItems = mp.process();
			for (String item : topItems) {
				System.out.println(item);
			}
		}
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {
		// Entirely taken from
		// http://www.mkyong.com/java/how-to-sort-a-map-in-java/

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				int result =(o2.getValue()).compareTo(o1.getValue());
				if (result != 0) 
						return result;
				else 
					return o1.getKey().compareTo(o2.getKey());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
