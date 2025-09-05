package mage.cards.decks.importer;

import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;

import java.util.*;

/**
 * Tests only: mock of card repository for deck import tests
 * TODO: move that tests from Mage to Test project
 */
public class FakeCardLookup extends CardLookup {

    private final Map<String, CardInfo> lookup = new HashMap<>();
    private final boolean alwaysMatches;

    public FakeCardLookup() {
        this(true);
    }

    public FakeCardLookup(boolean alwaysMatches) {
        this.alwaysMatches = alwaysMatches;
    }

    public FakeCardLookup addCard(String cardName) {
        lookup.put(cardName, new CardInfo() {{
            name = cardName;
        }});
        return this;
    }

    public CardInfo lookupCardInfo(String cardName) {
        CardInfo card = lookup.get(cardName);
        if (card != null) {
            return card;
        }

        if (alwaysMatches) {
            return new CardInfo() {{
                name = cardName;
            }};
        }

        return null;
    }

    @Override
    public List<CardInfo> lookupCardInfo(CardCriteria criteria) {
        CardInfo found = lookupCardInfo(criteria.getName());
        if (found != null) {
            return new ArrayList<>(Collections.singletonList(found));
        } else {
            return Collections.emptyList();
        }
    }

}
