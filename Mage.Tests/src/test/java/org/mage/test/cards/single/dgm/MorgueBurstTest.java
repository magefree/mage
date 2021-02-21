package org.mage.test.cards.single.dgm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

/*
Morgue Burst    {4}{B}{R}
Sorcery 
Return target creature card from your graveyard to your hand. Morgue Burst deals damage to any target equal to the power of the card returned this way.
*/



public class MorgueBurstTest extends CardTestPlayerBase {

    // Character defining static abilities (defining P/T of Nightmare) work in all zones
    @Test
    public void testNightmare() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Morgue Burst");
        addCard(Zone.GRAVEYARD, playerA, "Nightmare");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morgue Burst", "Nightmare^targetPlayer=PlayerB");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertGraveyardCount(playerA, "Morgue Burst", 1);
        assertHandCount(playerA, 1);
    }

}