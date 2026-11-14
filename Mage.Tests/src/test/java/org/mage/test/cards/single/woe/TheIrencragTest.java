package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TheIrencragTest extends CardTestPlayerBase {

    private static final String irencrag = "The Irencrag";
    // Whenever a legendary creature you control enters, you may have The Irencrag become a legendary Equipment
    // artifact named Everflame, Heroes' Legacy. If you do, it gains equip {3} and "Equipped creature gets +3/+3"
    // and loses all other abilities.

    /**
     * The Irencrag that comes back from Scrollshift is a new object (400.7), so it doesn't become Everflame.
     */
    @Test
    public void test_FlickeredInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, irencrag);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Isamaru, Hound of Konda"); // {W}, legendary
        // Exile up to one target artifact, creature, or enchantment you control, then return it to the battlefield
        // under its owner's control. Draw a card.
        addCard(Zone.HAND, playerA, "Scrollshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Isamaru, Hound of Konda");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scrollshift", irencrag, "Whenever a legendary creature");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, irencrag, 1);
        assertPermanentCount(playerA, "Everflame, Heroes' Legacy", 0);
    }
}
