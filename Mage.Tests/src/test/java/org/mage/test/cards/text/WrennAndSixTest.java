package org.mage.test.cards.text;

import mage.cards.Card;
import mage.cards.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WrennAndSixTest {

    @Test
    public void testFirstLoyaltyAbilityRulesText() {
        Card wrennAndSix = CardRepository.instance.findCard("Wrenn and Six").createCard();
        String firstLoyaltyAbilityRulesText = wrennAndSix.getRules().get(0);

        Assertions.assertEquals(firstLoyaltyAbilityRulesText, "+1: Return up to one target land card from your graveyard to your hand.");
    }
}
