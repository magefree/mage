package org.mage.plugins.rating.results;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mage.plugins.rating.util.MapSorter;

public class ResultHandler {

	private static ResultHandler fInstance = new ResultHandler();
	private static Map<String, Integer> ratings = new LinkedHashMap<String, Integer>();
	private static String newLine = System.getProperty("line.separator");
	private static Pattern scorePattern = Pattern.compile("([^|]*)[|]+ > [|]+([^|]*)");
	private static Logger log = Logger.getLogger(ResultHandler.class);

	static {
		File file = new File("results");
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static ResultHandler getInstance() {
		return fInstance;
	}

	public void save(List<Rating> results) {
		File f = new File("results" + File.separator + UUID.randomUUID() + ".txt");
		try {
			if (f.createNewFile()) {
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream b = new BufferedOutputStream(fos);
				for (Rating r : results) {
					String line = r.winnerCardName + "| > |" + r.loserCardName + newLine;
					b.write(line.getBytes());
				}
				b.close();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rate() throws Exception {
		ratings.clear();
		File file = new File("results");
		File ratingFile = new File("ratings.txt");
		if (ratingFile.exists()) {
			if (!ratingFile.delete()) {
				throw new RuntimeException("Couldn't delete previous ratings.txt file");
			}
		}
		if (ratingFile.createNewFile()) {
			for (File f : file.listFiles()) {
				if (!f.getName().equals("rating.txt")) {
					parseFile(f);
				}
			}
			ratings = MapSorter.sortByValue(ratings);
			FileOutputStream fos = new FileOutputStream(ratingFile);
			BufferedOutputStream b = new BufferedOutputStream(fos);
			for (Entry<String, Integer> entry : ratings.entrySet()) {
				String line = entry.getValue() + " : " + entry.getKey() + newLine;
				b.write(line.getBytes());
			}
			b.close();
			fos.close();
		}
	}

	private void parseFile(File f) throws Exception {
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			Matcher m = scorePattern.matcher(line);
			if (m.matches()) {
				String winner = m.group(1);String loser = m.group(2);
				Integer winnerRating = ratings.get(winner);
				if (winnerRating == null)
					winnerRating = 1000;
				Integer loserRating = ratings.get(loser);
				if (loserRating == null)
					loserRating = 1000;
				Integer newWinnerRating = countEloRating(winnerRating, loserRating, true);
				Integer newLoserRating = countEloRating(loserRating, winnerRating, false);
				log.info("Winner(" + winner + "): " + winnerRating + " >> " + newWinnerRating);
				log.info("Loser(" + loser + "): " + loserRating + " >> " + newLoserRating);
				ratings.put(winner, newWinnerRating);
				ratings.put(loser, newLoserRating);
			} else {
				log.warn("Doesn't match rate pattern: " + line);
			}
		}
		s.close();
	}

	/**
	 * Count rating using Elo Rating System.
	 * 
	 * @param ra
	 * @param rb
	 * @return
	 */
	private Integer countEloRating(Integer ra, Integer rb, boolean firstWon) {
		double d = (rb - ra) / 400.0;
		double expected = 1.0d / (1 + Math.pow(10, d));
		double actual = firstWon ? 1 : 0;
		return Integer.valueOf((int) Math.round(ra + 32 * (actual - expected)));
	}
}