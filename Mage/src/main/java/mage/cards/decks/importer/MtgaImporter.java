package mage.cards.decks.importer;

import com.google.common.collect.ImmutableMap;
import mage.cards.decks.CardNameUtil;
import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mage.cards.decks.CardNameUtil.CARD_NAME_PATTERN;

public class MtgaImporter extends PlainTextDeckImporter {

    private static final Map<String, String> SET_REMAPPING = ImmutableMap.of("DAR", "DOM");
    private static final Pattern MTGA_PATTERN = Pattern.compile(
            "(\\p{Digit}+)" +
                    "\\p{javaWhitespace}+" +
                    "(" + CARD_NAME_PATTERN.pattern() + ")" +
                    "(?:\\p{javaWhitespace}+\\()?" +
                    "(\\p{Alnum}+)?" +
                    "(?:\\)\\p{javaWhitespace}+)?" +
                    "(\\p{Graph}+)?");

    private final CardLookup lookup = getCardLookup();
    private boolean sideboard = false;

    @Override
    protected void readLine(String line, DeckCardLists deckList, FixedInfo fixedInfo) {
        
        line = line.trim();
        
        if (line.equals("Deck")) {
            return;
        }

        if (line.equals("Sideboard") || line.equals("")) {
            sideboard = true;
            return;
        }

        Matcher pattern = MTGA_PATTERN.matcher(CardNameUtil.normalizeCardName(line));
        
        if (!pattern.matches()) {
            sbMessage.append("Error reading '").append(line).append("'\n");
            return;
        }
        
        Optional<CardInfo> found;
        int count = Integer.parseInt(pattern.group(1));
        String name = pattern.group(2);    
        if (pattern.group(3) != null && pattern.group(4) != null) {
            String set = SET_REMAPPING.getOrDefault(pattern.group(3), pattern.group(3));
            String cardNumber = pattern.group(4);
            found = lookup.lookupCardInfo(name, set, cardNumber);
        } else {
            found = lookup.lookupCardInfo(name);
        }
        
        if (!found.isPresent()) {
            sbMessage.append("Cound not find card for '").append(line).append("'\n");
        } else {
            final List<DeckCardInfo> zone = sideboard ? deckList.getSideboard() : deckList.getCards();
            found.ifPresent(card -> zone.addAll(Collections.nCopies(count,
                    new DeckCardInfo(card.getName(), card.getCardNumber(), card.getSetCode()))));
        }
    }

}
