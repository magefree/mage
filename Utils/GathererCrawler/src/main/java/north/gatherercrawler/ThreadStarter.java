package north.gatherercrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import north.gatherercrawler.util.CardsList;

/**
 *
 * @author North
 */
public class ThreadStarter extends Thread {

    private static Integer threadsDone = 0;
    private final Integer threads = 10;
    private List<Card> sortedCards;

    public static synchronized void threadDone() {
        threadsDone++;
    }

    private void updateSortedCards() {
        if (sortedCards == null) {
            sortedCards = new ArrayList<Card>();
            Iterator<Card> iterator = CardsList.iterator();
            while (iterator.hasNext()) {
                sortedCards.add(iterator.next());
            }

            Collections.sort(sortedCards, new Comparator<Card>() {

                public int compare(Card o1, Card o2) {
                    int expansionCompare = o1.getExpansion().compareTo(o2.getExpansion());
                    return expansionCompare != 0 ? expansionCompare : o1.getCardNumber().compareTo(o2.getCardNumber());
                }
            });
        }
    }

    private void writeCardsToFile() {
        try {
            FileWriter fstream = new FileWriter("cards-data.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            Iterator<Card> iterator = sortedCards.iterator();
            while (iterator.hasNext()) {
                out.write(iterator.next().toString());
                out.newLine();
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void writeCardsToUtilFile() {
        try {
            FileWriter fstream = new FileWriter("mtg-cards-data.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            Iterator<Card> iterator = sortedCards.iterator();
            while (iterator.hasNext()) {
                Card card = iterator.next();
                String cardLine = generateUtilLine(card);
                out.write(replaceSpecialChards(cardLine));
                out.newLine();

                if (card.getOtherSide() != null) {
                    cardLine = generateUtilLine(card.getOtherSide());
                    out.write(replaceSpecialChards(cardLine));
                    out.newLine();
                }
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void writeCardsToTrackerFile() {
        Set<String> knownSets = new TreeSet<String>();
        knownSets.add("Fifth Edition");
        knownSets.add("Eighth Edition");
        knownSets.add("Ninth Edition");
        knownSets.add("Tenth Edition");
        knownSets.add("Magic 2010");
        knownSets.add("Magic 2011");
        knownSets.add("Magic 2012");
        knownSets.add("Planechase");
        knownSets.add("Duel Decks: Elspeth vs. Tezzeret");
        knownSets.add("Tempest");
        knownSets.add("Urza's Saga");
        knownSets.add("Invasion");
        knownSets.add("Planeshift");
        knownSets.add("Apocalypse");
        knownSets.add("Onslaught");
        knownSets.add("Mirrodin");
        knownSets.add("Darksteel");
        knownSets.add("Fifth Dawn");
        knownSets.add("Champions of Kamigawa");
        knownSets.add("Betrayers of Kamigawa");
        knownSets.add("Saviors of Kamigawa");
        knownSets.add("Ravnica: City of Guilds");
        knownSets.add("Guildpact");
        knownSets.add("Dissension");
        knownSets.add("Time Spiral");
        knownSets.add("Time Spiral \"Timeshifted\"");
        knownSets.add("Planar Chaos");
        knownSets.add("Future Sight");
        knownSets.add("Lorwyn");
        knownSets.add("Morningtide");
        knownSets.add("Shadowmoor");
        knownSets.add("Eventide");
        knownSets.add("Shards of Alara");
        knownSets.add("Conflux");
        knownSets.add("Alara Reborn");
        knownSets.add("Zendikar");
        knownSets.add("Worldwake");
        knownSets.add("Rise of the Eldrazi");
        knownSets.add("Scars of Mirrodin");
        knownSets.add("Mirrodin Besieged");
        knownSets.add("New Phyrexia");
        knownSets.add("Innistrad");
        knownSets.add("Dark Ascension");
        knownSets.add("Avacyn Restored");
        try {
            FileWriter fstream = new FileWriter("mtg-cards-tracker-data.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            Iterator<Card> iterator = sortedCards.iterator();
            while (iterator.hasNext()) {
                Card card = iterator.next();
                if (knownSets.contains(card.getExpansion())) {
                    String cardLine = card.toString();
                    out.write(replaceSpecialChards(cardLine));
                    out.newLine();

                    if (card.getOtherSide() != null) {
                        cardLine = card.getOtherSide().toString();
                        out.write(replaceSpecialChards(cardLine));
                        out.newLine();
                    }
                }
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String replaceSpecialChards(String original) {
        return original.replace("\u2014", "-").replace("\u00E9", "e")
                .replace("\u00C6", "AE").replace("\u00E6", "ae")
                .replace("\u00C1", "A").replace("\u00E1", "a")
                .replace("\u00C2", "A").replace("\u00E2", "a")
                .replace("\u00D6", "O").replace("\u00F6", "o")
                .replace("\u00DB", "U").replace("\u00FB", "u")
                .replace("\u00DC", "U").replace("\u00FC", "u");
    }

    private String generateUtilLine(Card card) {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getName()).append("|");
        sb.append(card.getExpansion()).append("|");
        sb.append(card.getCardNumber() != null ? card.getCardNumber() : "").append("|");
        String rarity = card.getRarity() != null ? card.getRarity() : "";
        if (rarity.equalsIgnoreCase("Mythic Rare")) {
            rarity = "M";
        }
        if (rarity.equalsIgnoreCase("Rare")) {
            rarity = "R";
        }
        if (rarity.equalsIgnoreCase("Uncommon")) {
            rarity = "U";
        }
        if (rarity.equalsIgnoreCase("Common")) {
            rarity = "C";
        }
        if (rarity.equalsIgnoreCase("Basic Land")) {
            rarity = "L";
        }
        sb.append(rarity).append("|");
        List<String> manaCost = card.getManaCost();
        for (String cost : manaCost) {
            if (!cost.isEmpty()) {
                sb.append("{").append(cost).append("}");
            }
        }
        sb.append("|");
        sb.append(card.getTypes()).append("|");
        String pts = card.getPowerToughness();
        if (pts != null && pts.length() > 1) {
            String[] pt = pts.split("/");
            sb.append(pt[0].trim()).append("|");
            sb.append(pt[1].trim()).append("|");
        } else {
            sb.append("||");
        }
        List<String> cardText = card.getCardText();
        for (int i = 0; i < cardText.size(); i++) {
            sb.append(cardText.get(i));
            if (i < cardText.size() - 1) {
                sb.append("$");
            }
        }
        sb.append("|");
        return sb.toString();
    }

    @Override
    public void run() {
        for (int i = 0; i < threads; i++) {
            Thread t = new CardParser();
            t.start();
        }

        while (threads != threadsDone) {
            try {
                synchronized (this) {
                    this.wait(5000);
                }
            } catch (InterruptedException ex) {
            }
        }

        updateSortedCards();
        writeCardsToFile();
        writeCardsToUtilFile();
        writeCardsToTrackerFile();
    }
}
