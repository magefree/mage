package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author BetaSteward
 */
public class MycosynthGolemTest extends CardTestCommander4Players {

    @Test
    public void test_AffinityForNormalSpells() {
        /*
         bug:
         Mycosynth Golem Artifact Creature — Golem 4/5, 11 (11) Affinity for
         artifacts (This spell costs {1} less to cast for each artifact you
         control.) Artifact creature spells you cast have affinity for artifacts.
         (They cost {1} less to cast for each artifact you control.)
         */

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Affinity for artifacts
        // Artifact creature spells you cast have affinity for artifacts.
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Golem");
        addCard(Zone.HAND, playerA, "Alpha Myr"); // Creature - Myr  2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Alpha Myr", 1);
        assertHandCount(playerA, "Alpha Myr", 0);

        Permanent mountain = getPermanent("Mountain", playerA);
        Permanent forest = getPermanent("Forest", playerA);
        int tappedLands = 0;
        if (mountain.isTapped()) {
            tappedLands++;
        }
        if (forest.isTapped()) {
            tappedLands++;
        }
        Assert.assertEquals("only one land may be tapped because the cost reduction", 1, tappedLands);
    }
}
