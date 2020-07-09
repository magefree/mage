package org.mage.test.cards.text;

import mage.cards.Card;
import mage.cards.repository.CardRepository;
import org.junit.Assert;
import org.junit.Test;

public class WrennAndSixTest {

    @Test
    public void testFirstLoyaltyAbilityRulesText() {
        Card wrennAndSix = CardRepository.instance.findCard("Wrenn and Six").getCard();
        String firstLoyaltyAbilityRulesText = wrennAndSix.getRules().get(0);

        Assert.assertEquals(firstLoyaltyAbilityRulesText, "+1: Return up to one target land card from your graveyard to your hand.");
    }
}
