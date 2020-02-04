package mage.cards.decks.importer;

import org.apache.log4j.Logger;
import java.io.File;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;


import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

public abstract class DeckImporter {

  protected static final Logger logger = Logger.getLogger(DeckImporter.class);

  private static final String[] SIDEBOARD_MARKS = new String[]{"//sideboard", "sb: "};

  public static DeckImporter getDeckImporter(String file) {
    if (file == null) {
      return null;
    } if (file.toLowerCase(Locale.ENGLISH).endsWith("dec")) {
      return new DecDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("mwdeck")) {
      return new MWSDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("txt")) {
      return new TxtDeckImporter(haveSideboardSection(file));
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("dck")) {
      return new DckDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("dek")) {
      return new DekDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("cod")) {
      return new CodDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("o8d")) {
      return new O8dDeckImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("draft")) {
      return new DraftLogImporter();
    } else if (file.toLowerCase(Locale.ENGLISH).endsWith("mtga")) {
      return new MtgaImporter();
    } else {
      return null;
    }
  }

  public static DeckCardLists importDeckFromFile(String file) {
    return importDeckFromFile(file, new StringBuilder());
  }

  public static DeckCardLists importDeckFromFile(String file, StringBuilder errorMessages) {
    DeckImporter deckImporter = getDeckImporter(file);
    if (deckImporter != null) {
      return deckImporter.importDeck(file, errorMessages);
    } else {
      return new DeckCardLists();
    }
  }

  public abstract DeckCardLists importDeck(String file, StringBuilder errorMessages);

  public DeckCardLists importDeck(String file) {
    return importDeck(file, new StringBuilder());
  }

  public CardLookup getCardLookup() {
    return CardLookup.instance;
  }

  private static boolean haveSideboardSection(String file) {
    // search for sideboard section:
    // or //sideboard
    // or SB: 1 card name -- special deckstats.net

    File f = new File(file);
    try (Scanner scanner = new Scanner(f)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim().toLowerCase(Locale.ENGLISH);

        for (String mark : SIDEBOARD_MARKS) {
          if (line.startsWith(mark)) {
            return true;
          }
        }
      }
    } catch (Exception e) {
      // ignore error, deckimporter will process it
    }

    // not found
    return false;
  }

}
