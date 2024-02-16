package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DraftLogImporter extends PlainTextDeckImporter {

    private static final Pattern SET_PATTERN = Pattern.compile("------ (\\p{Alnum}+) ------$");
    private static final Pattern PICK_PATTERN = Pattern.compile("--> (.+)$");

    private String currentSet = null;

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {

        Matcher setMatcher = SET_PATTERN.matcher(line);
        if (setMatcher.matches()) {
            currentSet = setMatcher.group(1);
            return;
        }

        Matcher pickMatcher = PICK_PATTERN.matcher(line);
        if (pickMatcher.matches()) {
            String name = pickMatcher.group(1);
            CardInfo card = getCardLookup().lookupCardInfo(name, currentSet).orElse(null);
            if (card != null) {
                deckList.getCards().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
            } else {
                sbMessage.append("couldn't find: \"").append(name).append("\"\n");
            }
        }
    }

}
