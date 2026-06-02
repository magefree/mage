package mage.cards.decks.importer;

import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DckDeckImportTest {

    @Test
    public void testImport() {
        StringBuilder errors = new StringBuilder();
        DckDeckImporter importer = new DckDeckImporter() {
            @Override
            public CardLookup getCardLookup() {
                return new CardLookup() {
                    @Override
                    public List<CardInfo> lookupCardInfo(CardCriteria criteria) {
                        return Collections.singletonList(new CardInfo() {{
                            name = new HashMap<String, String>() {{
                                this.put("2*", "Ugin, the Ineffable");
                                this.put("72+", "Cephalid Looter");
                            }}.get(criteria.getCardNumber());
                            setCode = criteria.getSetCodes().get(0);
                            cardNumber = criteria.getCardNumber();
                        }});
                    }
                };
            }
        };

        DeckCardLists deck = importer.importDeck(
                Paths.get("src", "test", "data", "importer", "testdeck.dck").toString(),
                errors,
                false
        );

        Assert.assertEquals("", errors.toString());
        TestDeckChecker.checker()
            .addMain("Ugin, the Ineffable", 1)
            .addMain("Cephalid Looter", 1)
        .verify(deck, 2, 0);
    }

}
