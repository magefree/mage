

package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DeckImporter {

    private static final Logger logger = Logger.getLogger(DeckImporter.class);

    protected StringBuilder sbMessage = new StringBuilder(); //TODO we should stop using this not garbage collectable StringBuilder. It just bloats
    protected int lineCount;


    /**
     *
     * @param file file to import
     * @param errorMessages you can setup output messages to showup to user (set null for fatal exception on messages.count > 0)
     * @return decks list
     */
    public DeckCardLists importDeck(String file, StringBuilder errorMessages) {
        File f = new File(file);
        DeckCardLists deckList = new DeckCardLists();
        if (!f.exists()) {
            logger.warn("Deckfile " + file + " not found.");
            return deckList;
        }
        lineCount = 0;

        sbMessage.setLength(0);
        try {
            try (Scanner scanner = new Scanner(f)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    lineCount++;
                    readLine(line, deckList);
                }

                if (sbMessage.length() > 0) {
                    if(errorMessages != null) {
                        // normal output for user
                        errorMessages.append(sbMessage);
                    }else{
                        // fatal error
                        logger.fatal(sbMessage);
                    }
                }
            } catch (Exception ex) {
                logger.fatal(null, ex);
            }
        } catch (Exception ex) {
            logger.fatal(null, ex);
        }
        return deckList;
    }

    public DeckCardLists importDeck(String file) {
        return importDeck(file, null);
    }

    public String getErrors(){
        return sbMessage.toString();
    }

    protected abstract void readLine(String line, DeckCardLists deckList);
}
