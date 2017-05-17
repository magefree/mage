package org.mage.test.cards.mana;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class NagaVitalistTest extends CardTestPlayerBase {

    /*
    Naga Vitalist 1G
    Creature - Naga Druid 1/2
    T: Add to your mana pool one mana of any type that a land you control could produce.
     */
    private final String nagaVitalist = "Naga Vitalist";

    /*
     Reported bug (issue #3315)
    Naga Vitalist could not produce any color mana with a Gift of Paradise enchanted on a forest. All lands on board were forests.
     */
    @Test
    public void nagaVitalist_InteractionGiftOfParadise() {

        /*
        Gift of Paradise 2G
        Enchantment - Aura
        Enchant - Land
        When Gift of Paradise enters the battlefield, you gain 3 life.
        Enchanted land has "T: Add two mana of any one color to your mana pool."
         */
        String giftParadise = "Gift of Paradise";

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, nagaVitalist);
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling"); // mana pools do not empty at the end of phases or turns
        addCard(Zone.HAND, playerA, giftParadise);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giftParadise, "Forest");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add to your mana pool one mana of any type that a land you control could produce");
        setChoice(playerA, "Red");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23); // gift of paradise ETB
        assertTapped(nagaVitalist, true);
        Assert.assertEquals("one red mana has to be in the mana pool", 1, playerA.getManaPool().get(ManaType.RED));
    }
}
