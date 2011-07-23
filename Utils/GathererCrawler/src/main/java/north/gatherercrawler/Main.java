package north.gatherercrawler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author robert.biter
 */
public class Main {

    private static void readCardsFromFile() {
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream("cards-data.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if (strLine.length() > 0) {
                    Card card = new Card(strLine);
                    CardsList.add(card);
                    ParsedList.add(card.getMultiverseId());
                }
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> sets = new ArrayList<String>();
//        sets.add("Alara Reborn");
//        sets.add("Alliances");
//        sets.add("Antiquities");
//        sets.add("Apocalypse");
//        sets.add("Arabian Nights");
//        sets.add("Archenemy");
//        sets.add("Battle Royale Box Set");
//        sets.add("Beatdown Box Set");
//        sets.add("Betrayers of Kamigawa");
//        sets.add("Champions of Kamigawa");
//        sets.add("Chronicles");
//        sets.add("Classic Sixth Edition");
//        sets.add("Coldsnap");
//        sets.add("Conflux");
//        sets.add("Darksteel");
//        sets.add("Dissension");
//        sets.add("Duel Decks: Divine vs. Demonic");
//        sets.add("Duel Decks: Elspeth vs. Tezzeret");
//        sets.add("Duel Decks: Elves vs. Goblins");
//        sets.add("Duel Decks: Garruk vs. Liliana");
//        sets.add("Duel Decks: Jace vs. Chandra");
//        sets.add("Duel Decks: Knights vs. Dragons");
//        sets.add("Duel Decks: Phyrexia vs. the Coalition");
//        sets.add("Eighth Edition");
//        sets.add("Eventide");
//        sets.add("Exodus");
//        sets.add("Fallen Empires");
//        sets.add("Fifth Dawn");
//        sets.add("Fifth Edition");
//        sets.add("Fourth Edition");
//        sets.add("From the Vault: Dragons");
//        sets.add("From the Vault: Exiled");
//        sets.add("From the Vault: Relics");
//        sets.add("Future Sight");
//        sets.add("Guildpact");
//        sets.add("Homelands");
//        sets.add("Ice Age");
//        sets.add("Invasion");
//        sets.add("Judgment");
//        sets.add("Legends");
//        sets.add("Legions");
//        sets.add("Limited Edition Alpha");
//        sets.add("Limited Edition Beta");
//        sets.add("Lorwyn");
//        sets.add("Magic 2010");
//        sets.add("Magic 2011");
//        sets.add("Magic 2012");
//        sets.add("Masters Edition");
//        sets.add("Masters Edition II");
//        sets.add("Masters Edition III");
//        sets.add("Masters Edition IV");
//        sets.add("Mercadian Masques");
//        sets.add("Mirage");
//        sets.add("Mirrodin");
//        sets.add("Mirrodin Besieged");
//        sets.add("Morningtide");
//        sets.add("Nemesis");
//        sets.add("New Phyrexia");
//        sets.add("Ninth Edition");
//        sets.add("Odyssey");
//        sets.add("Onslaught");
//        sets.add("Planar Chaos");
//        sets.add("Planechase");
//        sets.add("Planeshift");
//        sets.add("Portal");
//        sets.add("Portal Second Age");
//        sets.add("Portal Three Kingdoms");
//        sets.add("Premium Deck Series: Fire and Lightning");
//        sets.add("Premium Deck Series: Slivers");
//        sets.add("Promo set for Gatherer");
//        sets.add("Prophecy");
//        sets.add("Ravnica: City of Guilds");
//        sets.add("Revised Edition");
//        sets.add("Rise of the Eldrazi");
//        sets.add("Saviors of Kamigawa");
//        sets.add("Scars of Mirrodin");
//        sets.add("Scourge");
//        sets.add("Seventh Edition");
//        sets.add("Shadowmoor");
//        sets.add("Shards of Alara");
//        sets.add("Starter 1999");
//        sets.add("Starter 2000");
//        sets.add("Stronghold");
//        sets.add("Tempest");
//        sets.add("Tenth Edition");
//        sets.add("The Dark");
//        sets.add("Time Spiral");
//        sets.add("Time Spiral \"Timeshifted\"");
//        sets.add("Torment");
//        sets.add("Unlimited Edition");
//        sets.add("Urza's Destiny");
//        sets.add("Urza's Legacy");
//        sets.add("Urza's Saga");
//        sets.add("Vanguard");
//        sets.add("Visions");
//        sets.add("Weatherlight");
//        sets.add("Worldwake");
//        sets.add("Zendikar");
//        sets.add("Magic: The Gathering-Commander");


//        sets.add("Unglued");
//        sets.add("Unhinged");

        readCardsFromFile();

        StringBuilder sb = new StringBuilder();
        int added = 0;
        for (String set : sets) {
            sb.append("|[\"").append(set.replace(" ", "+")).append("\"]");
            added++;

            if (added % 20 == 0 || added == sets.size()) {
                int retries = 30;
                boolean done = false;
                while (retries > 0 && !done) {
                    String url = "http://gatherer.wizards.com/Pages/Search/Default.aspx?action=advanced&output=checklist&set=" + sb.toString();
                    Connection connection = Jsoup.connect(url);
                    connection.timeout(300000);
                    Document doc = connection.get();
                    System.out.println(url);

                    Elements select = doc.select(".checklist .name a");
                    if (!select.isEmpty()) {
                        for (Element element : select) {
                            Integer multiverseId = Integer.parseInt(element.attr("href").replace("../Card/Details.aspx?multiverseid=", ""));
                            if (!ParsedList.contains(multiverseId)) {
                                ParseQueue.add(multiverseId);
                            }
                        }
                    }
                    done = true;
                }

                if (!done) {
                    System.out.println("Error accured");
                }
                sb = new StringBuilder();
                Thread.sleep(1000);
            }
        }


        Thread t = new ThreadStarter();
        t.start();
    }
}
