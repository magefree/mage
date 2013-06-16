package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HavengulLichTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        addCard(Zone.GRAVEYARD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Prodigal Pyromancer");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prodigal Pyromancer");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}", playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Prodigal Pyromancer", 1);
        assertTapped("Havengul Lich", true);
        assertTapped("Prodigal Pyromancer", false);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        addCard(Zone.GRAVEYARD, playerA, "Black Cat");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Black Cat");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Cat");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Black Cat", 0);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        addCard(Zone.GRAVEYARD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Prodigal Pyromancer");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prodigal Pyromancer");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", playerB);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}", playerB);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Prodigal Pyromancer", 1);
        assertTapped("Prodigal Pyromancer", true);
        assertTapped("Havengul Lich", false);
        assertGraveyardCount(playerA, 0);
    }

}
