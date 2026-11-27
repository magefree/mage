package org.mage.test.cards.single.usg;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OpalTitanTest extends CardTestPlayerBase {

    private static final String titan = "Opal Titan";
    // When an opponent casts a creature spell, if Opal Titan is an enchantment, Opal Titan becomes
    // a 4/4 Giant creature with protection from each of that spell's colors.

    /**
     * The Titan that comes back from Flicker of Fate is a new object (400.7), so it stays an enchantment.
     */
    @Test
    public void test_FlickeredInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, titan);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Exile target creature or enchantment, then return it to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Flicker of Fate");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Flicker of Fate", titan, "When an opponent casts");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertType(titan, CardType.CREATURE, false);
        assertType(titan, CardType.ENCHANTMENT, true);
    }
}
