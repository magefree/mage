package org.mage.test.commander;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.filter.FilterMana;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 * @author LevelX2
 */
public class CommanderColorIdentityTest extends CardTestCommander3PlayersFFA {

    @Test
    public void TestColors() {
        // Basic Colors
        Assertions.assertEquals("{B}", getColorIdentityString("Sengir Vampire"));
        Assertions.assertEquals("{G}", getColorIdentityString("Elvish Visionary"));
        Assertions.assertEquals("{R}", getColorIdentityString("Demolish"));
        Assertions.assertEquals("{U}", getColorIdentityString("Negate"));
        Assertions.assertEquals("{W}", getColorIdentityString("Silvercoat Lion"));

        // Multicolor
        Assertions.assertEquals("{W}{G}", getColorIdentityString("Veteran Warleader"));
        Assertions.assertEquals("{B}{R}", getColorIdentityString("Forerunner of Slaughter"));
        Assertions.assertEquals("{U}{R}", getColorIdentityString("Brutal Expulsion"));
        Assertions.assertEquals("{B}{G}", getColorIdentityString("Catacomb Sifter"));
        Assertions.assertEquals("{U}{B}", getColorIdentityString("Fathom Feeder"));

        Assertions.assertEquals("{W}{R}", getColorIdentityString("Angelic Captain"));
        Assertions.assertEquals("{U}{G}", getColorIdentityString("Bring to Light"));
        Assertions.assertEquals("{W}{B}", getColorIdentityString("Drana's Emissary"));
        Assertions.assertEquals("{R}{G}", getColorIdentityString("Grove Rumbler"));
        Assertions.assertEquals("{W}{U}", getColorIdentityString("Roil Spout"));

        // Cards with colors in the rule text
        Assertions.assertEquals("{B}{R}", getColorIdentityString("Fires of Undeath"));

        // Cards without casting costs
        Assertions.assertEquals("{B}", getColorIdentityString("Living End"));
        Assertions.assertEquals("{G}", getColorIdentityString("Hypergenesis"));

        // Phyrexian Mana
        Assertions.assertEquals("{B}", getColorIdentityString("Dismember"));

        // Hybrid mana
        Assertions.assertEquals("{W}{G}", getColorIdentityString("Kitchen Finks"));

        // Lands with colored activation costs
        Assertions.assertEquals("{G}", getColorIdentityString("Treetop Village"));

        // Cards with extort don't use extort to determine color identity
        Assertions.assertEquals("{W}", getColorIdentityString("Knight of Obligation"));

        // Two face cards
        Assertions.assertEquals("{R}{G}", getColorIdentityString("Daybreak Ranger"));
        Assertions.assertEquals("{W}{R}", getColorIdentityString("Archangel Avacyn"));
        Assertions.assertEquals("{U}{R}", getColorIdentityString("Civilized Scholar"));

        // Split cards
        Assertions.assertEquals("{U}{R}", getColorIdentityString("Fire // Ice"));

        // MDF cards
        Assertions.assertEquals("{W}{U}{B}{R}{G}", getColorIdentityString("Esika, God of the Tree"));

        // Adventure cards
        Assertions.assertEquals("{G}", getColorIdentityString("Rosethorn Acolyte"));
    }

    private String getColorIdentityString(String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        if (cardInfo == null) {
            throw new IllegalArgumentException("Couldn't find the card " + cardName + " in the DB.");
        }
        Card card = cardInfo.createCard();
        FilterMana filterMana = card.getColorIdentity();
        return filterMana.toString();
    }
}
