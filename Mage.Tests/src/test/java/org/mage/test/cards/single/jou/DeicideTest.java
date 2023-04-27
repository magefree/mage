package org.mage.test.cards.single.jou;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DeicideTest extends CardTestPlayerBase {

    // #7661
    @Test
    public void targetNonCreatureeGod() {
        // Exile target enchantment. If the exiled card is a God card, search its controller's graveyard, hand,
        // and library for any number of cards with the same name as that card and exile them, then that player shuffles their library.
        addCard(Zone.HAND, playerA, "Deicide");

        addCard(Zone.BATTLEFIELD, playerB, "Heliod, Sun-Crowned");
        // add for Devotion {W}{W} x 3
        addCard(Zone.BATTLEFIELD, playerB, "Crusade", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deicide", "Heliod, Sun-Crowned");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExiledCardSubtype("Heliod, Sun-Crowned", SubType.GOD);


    }
}
