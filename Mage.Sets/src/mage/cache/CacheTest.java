package mage.cache;

import mage.Constants;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.sets.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author noxx
 */
public class CacheTest {

    /**
     * In case this test fails, it does mean that you need to update cache version in Sets.java
     */
    @Test
    public void testCacheConsistency() {
        Set<String> names = Sets.getCardNames();
        Set<String> nonLandNames = Sets.getNonLandCardNames();
        Set<String> creatureTypes = Sets.getCreatureTypes();

        for (ExpansionSet set : Sets.getInstance().values()) {
            for (Card card : set.getCards()) {
                if (card.getCardType().contains(Constants.CardType.CREATURE)) {
                    for (String type : card.getSubtype()) {
                        if (!creatureTypes.contains(type)) {
                            Assert.assertTrue("Couldn't find a creature type in the cache: " + type, false);
                        }
                    }
                }
                if (!names.contains(card.getName())) {
                    Assert.assertTrue("Couldn't find a card name in the cache: " + card.getName(), false);
                }
                if (!card.getCardType().contains(Constants.CardType.LAND)) {
                    if (!nonLandNames.contains(card.getName())) {
                        Assert.assertTrue("Couldn't find a non-land card name in the cache: " + card.getName(), false);
                    }
                }
            }
        }
    }
}
