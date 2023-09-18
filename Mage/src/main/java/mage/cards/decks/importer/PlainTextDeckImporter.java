package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PlainTextDeckImporter extends DeckImporter {

    protected StringBuilder sbMessage = new StringBuilder(); //TODO we should stop using this not garbage collectable StringBuilder. It just bloats
    protected int lineCount;


    /**
     * Import deck from text file
     *
     * @param fileName          file to import
     * @param errorMessages     you can setup output messages to showup to user (set null for fatal exception on messages.count > 0)
     * @param saveAutoFixedFile save fixed deck file (if any fixes applied)
     * @return decks list
     */
    public DeckCardLists importDeck(String fileName, StringBuilder errorMessages, boolean saveAutoFixedFile) {
        File f = new File(fileName);
        List<String> originalFile = new ArrayList<>();
        List<String> fixedFile = new ArrayList<>();
        DeckCardLists deckList = new DeckCardLists();
        if (!f.exists()) {
            logger.warn("Deckfile " + fileName + " not found.");
            return deckList;
        }
        lineCount = 0;

        sbMessage.setLength(0);
        try {
            try (Scanner scanner = new Scanner(f)) {
                boolean canFix = true;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    FixedInfo fixedInfo = new FixedInfo(line);
                    lineCount++;
                    readLine(line, deckList, fixedInfo);

                    originalFile.add(line);
                    fixedFile.add(fixedInfo.getFixedLine());
                    canFix = canFix && fixedInfo.getCanFix();
                }

                // auto-fix
                if (saveAutoFixedFile && canFix && !originalFile.equals(fixedFile)) {
                    logger.warn("WARNING, deck file contains errors, try to apply auto-fix and save: " + f.getAbsolutePath());
                    saveFixedDeckFile(fixedFile, f);
                }

                if (deckList.getCards().isEmpty() && deckList.getSideboard().isEmpty()) {
                    sbMessage.append("ERROR, unknown deck format, can't find any cards").append("\n");
                }

                if (sbMessage.length() > 0) {
                    if (errorMessages != null) {
                        // normal output for user
                        errorMessages.append(sbMessage);
                    } else {
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

    private void saveFixedDeckFile(List<String> fixedfile, File file) {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            PrintWriter out = new PrintWriter(stream);
            for (String line : fixedfile) {
                out.println(line);
            }
            out.close();
        } catch (Exception e) {
            logger.error("Can't save fixed deck file: " + file.getAbsolutePath() + ", reason: " + e.getMessage());
        }
    }


    @Override
    public DeckCardLists importDeck(String fileName, boolean saveAutoFixedFile) {
        return importDeck(fileName, null, saveAutoFixedFile);
    }

    /**
     * Read one text line from file and convert it to card
     *
     * @param line      original text line
     * @param deckList  deck list for new card
     * @param fixedInfo fixed info that contains fixed line (if converter can auto-fix deck file, e.g. replace one card to another)
     */
    protected abstract void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo);
}
