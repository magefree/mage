package org.mage.test.cards.single.afr;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.util.GameLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class Plus2MaceTest extends CardTestPlayerBase {

    @Test
    public void test_NameProcessingByRegExp() {
        // + character can't processing by regexp, so it must be quoted replaced in code by Pattern.quote(mageObject.getName())
        String cardName = "+2 Mace";
        CardInfo cardinfo = CardRepository.instance.findCard(cardName);
        Assertions.assertNotNull(cardinfo, cardName + " must exists");
        Card card = cardinfo.createCard();
        String cardText = GameLog.replaceNameByColoredName(card, card.getSpellAbility().toString(), null);
        Assertions.assertTrue(cardText.contains(cardName), "card text must contain card name");
    }
}
