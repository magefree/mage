package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DraftLogImporter extends PlainTextDeckImporter {

    private static Pattern SET_PATTERN = Pattern.compile("------ (\\p{Alnum}+) ------$");
    private static Pattern PICK_PATTERN = Pattern.compile("--> (.+)$");

    private String currentSet = null;

    @Override
    protected void readLine(String line, DeckCardLists deckList) {
        Matcher setMatcher = SET_PATTERN.matcher(line);
        if (setMatcher.matches()) {
            currentSet = setMatcher.group(1);
            return;
        }

        Matcher pickMatcher = PICK_PATTERN.matcher(line);
        if (pickMatcher.matches()) {
            String name = pickMatcher.group(1);
            List<CardInfo> cards = getCardLookup().lookupCardInfo(new CardCriteria().setCodes(currentSet).name(name));
            CardInfo card = null;
            if (!cards.isEmpty()) {
                card = cards.get(0);
            } else {
                card = getCardLookup().lookupCardInfo(name).orElse(null);
            }

            if (card != null) {
                deckList.getCards().add(new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()));
            } else {
                sbMessage.append("couldn't find: \"").append(name).append("\"\n");
            }
        }
    }

}
