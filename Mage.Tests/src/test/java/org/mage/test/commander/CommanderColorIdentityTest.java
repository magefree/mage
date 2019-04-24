
package org.mage.test.commander;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 *
 * @author LevelX2
 */
public class CommanderColorIdentityTest extends CardTestCommander3PlayersFFA {

    @Test
    public void TestColors() {
        // Basic Colors
        Assert.assertEquals("{B}", getColorIdentityString("Sengir Vampire"));
        Assert.assertEquals("{G}", getColorIdentityString("Elvish Visionary"));
        Assert.assertEquals("{R}", getColorIdentityString("Demolish"));
        Assert.assertEquals("{U}", getColorIdentityString("Negate"));
        Assert.assertEquals("{W}", getColorIdentityString("Silvercoat Lion"));

        // Multicolor
        Assert.assertEquals("{G}{W}", getColorIdentityString("Veteran Warleader"));
        Assert.assertEquals("{B}{R}", getColorIdentityString("Forerunner of Slaughter"));
        Assert.assertEquals("{R}{U}", getColorIdentityString("Brutal Expulsion"));
        Assert.assertEquals("{B}{G}", getColorIdentityString("Catacomb Sifter"));
        Assert.assertEquals("{B}{U}", getColorIdentityString("Fathom Feeder"));

        Assert.assertEquals("{R}{W}", getColorIdentityString("Angelic Captain"));
        Assert.assertEquals("{G}{U}", getColorIdentityString("Bring to Light"));
        Assert.assertEquals("{B}{W}", getColorIdentityString("Drana's Emissary"));
        Assert.assertEquals("{G}{R}", getColorIdentityString("Grove Rumbler"));
        Assert.assertEquals("{U}{W}", getColorIdentityString("Roil Spout"));

        // Cards with colors in the rule text
        Assert.assertEquals("{B}{R}", getColorIdentityString("Fires of Undeath"));

        // Cards without casting costs
        Assert.assertEquals("{B}", getColorIdentityString("Living End"));
        Assert.assertEquals("{G}", getColorIdentityString("Hypergenesis"));

        // Phyrexian Mana
        Assert.assertEquals("{B}", getColorIdentityString("Dismember"));

        // Hybrid mana
        Assert.assertEquals("{G}{W}", getColorIdentityString("Kitchen Finks"));

        // Lands with colored activation costs
        Assert.assertEquals("{G}", getColorIdentityString("Treetop Village"));

        // Cards with extort don't use extort to determine color identity
        Assert.assertEquals("{W}", getColorIdentityString("Knight of Obligation"));

        // Two face cards
        Assert.assertEquals("{G}{R}", getColorIdentityString("Daybreak Ranger"));
        Assert.assertEquals("{R}{W}", getColorIdentityString("Archangel Avacyn"));
        Assert.assertEquals("{R}{U}", getColorIdentityString("Civilized Scholar"));

    }

    private String getColorIdentityString(String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        if (cardInfo == null) {
            throw new IllegalArgumentException("Couldn't find the card " + cardName + " in the DB.");
        }
        Card card = cardInfo.getCard();
        FilterMana filterMana = card.getColorIdentity();
        return filterMana.toString();
    }
}
