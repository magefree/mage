package org.mage.test.cards.single.m21;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CrashThroughTest extends CardTestPlayerBase {

    @Test
    public void crashThrough() {
        // Creatures you control gain trample until end of turn.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Crash Through");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Scryb Sprites");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crash Through");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAbility(playerA, "Grizzly Bears", TrampleAbility.getInstance(), true);
        assertAbility(playerB, "Scryb Sprites", TrampleAbility.getInstance(), false);
        assertHandCount(playerA, 1);

    }
}
