package mage.cards.decks.importer;

import com.google.gson.*;
import mage.cards.decks.DeckCardLists;

import java.io.File;
import java.io.FileReader;

/**
 * @author github: timhae
 */
public abstract class JsonDeckImporter extends DeckImporter {

    protected StringBuilder sbMessage = new StringBuilder();

    /**
     * @param fileName              file to import
     * @param errorMessages     you can setup output messages to showup to user
     * @param saveAutoFixedFile do not supported for that format
     * @return decks list
     */
    public DeckCardLists importDeck(String fileName, StringBuilder errorMessages, boolean saveAutoFixedFile) {
        File f = new File(fileName);
        DeckCardLists deckList = new DeckCardLists();
        if (!f.exists()) {
            logger.warn("Deckfile " + fileName + " not found.");
            return deckList;
        }

        sbMessage.setLength(0);
        try {
            try (FileReader reader = new FileReader(f)) {
                try {
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    readJson(json, deckList);

                    if (sbMessage.length() > 0) {
                        if (errorMessages != null) {
                            // normal output for user
                            errorMessages.append(sbMessage);
                        } else {
                            // fatal error
                            logger.fatal(sbMessage);
                        }
                    }
                } catch (JsonParseException ex) {
                    logger.fatal("Can't parse json-deck: " + fileName, ex);
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
    public DeckCardLists importDeck(String fileName, boolean saveAutoFixedFile) {
        return importDeck(fileName, null, saveAutoFixedFile);
    }

    protected abstract void readJson(JsonObject json, DeckCardLists decklist);
}
