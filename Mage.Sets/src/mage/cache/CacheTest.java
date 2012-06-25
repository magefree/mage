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

    @Test
    public void testCacheConsistency() {
        //Set<String> names = Sets.getCardNames();
        //Set<String> nonLandNames = Sets.getNonLandCardNames();
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
            }
        }
    }
}
