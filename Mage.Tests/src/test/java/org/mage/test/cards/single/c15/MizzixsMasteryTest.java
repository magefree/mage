package org.mage.test.cards.single.c15;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class MizzixsMasteryTest extends CardTestPlayerBase {
    private static final String mastery = "Mizzix's Mastery";

    @Test
    // Author: alexander-novo
    // Making sure overload works correctly.
    public void overloadTest() {
        String fireball = "Delayed Blast Fireball";

        // Prep for exiling fireball from graveyard, copying, and then casting copy
        addCard(Zone.GRAVEYARD, playerA, fireball, 2);
        addCard(Zone.HAND, playerA, mastery);

        // Enough mana to overload mastery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        // Cast Mizzix's Mastery targetting delayed blast fireball. This should exile it, copy it into exile, and then cast the copy from exile, which should end up dealing 5 damage to player B
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mastery + " with overload");
        addTarget(playerA, fireball, 2);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 * 5);
        assertExileCount(playerA, fireball, 2);
    }

}
