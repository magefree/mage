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

                out.write(sb.toString().replace("\u00C6", "AE"));
                out.newLine();
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
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
    }
}
