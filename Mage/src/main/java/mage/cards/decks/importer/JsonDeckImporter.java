package mage.cards.decks.importer;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mage.cards.decks.DeckCardLists;

/**
 * @author github: timhae
 */
public abstract class JsonDeckImporter extends DeckImporter {

    protected StringBuilder sbMessage = new StringBuilder();

    /**
     *
     * @param file file to import
     * @param errorMessages you can setup output messages to showup to user
     * @return decks list
     */
    public DeckCardLists importDeck(String file, StringBuilder errorMessages) {
        File f = new File(file);
        DeckCardLists deckList = new DeckCardLists();
        if (!f.exists()) {
            logger.warn("Deckfile " + file + " not found.");
            return deckList;
        }

        sbMessage.setLength(0);
        try {
            try (FileReader reader = new FileReader(f)) {
                try { // Json parsing
                    JSONParser parser = new JSONParser();
                    JSONObject rootObj = (JSONObject) parser.parse(reader);
                    deckList.setName((String) rootObj.get("name"));
                    readJson(rootObj, deckList);

                    if (sbMessage.length() > 0) {
                        if (errorMessages != null) {
                            // normal output for user
                            errorMessages.append(sbMessage);
                        } else {
                            // fatal error
                            logger.fatal(sbMessage);
                        }
                    }
                } catch (ParseException ex) {
                    logger.fatal(null, ex);
                }
            } catch (Exception ex) {
                logger.fatal(null, ex);
            }
        } catch (Exception ex) {
            logger.fatal(null, ex);
        }
        return deckList;
    }

    @Override
    public DeckCardLists importDeck(String file) {
        return importDeck(file, null);
    }

    protected abstract void readJson(JSONObject line, DeckCardLists decklist);
}
