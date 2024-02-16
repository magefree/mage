package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class UnscytheKillerOfKingsTest extends CardTestPlayerBase {

    /*
     * Unscythe, Killer of Kings
     * Legendary Artifact — Equipment, UBBR (4)
     * Equipped creature gets +3/+3 and has first strike.
     * Whenever a creature dealt damage by equipped creature this turn dies, you 
     * may exile that card. If you do, put a 2/2 black Zombie creature token onto 
     * the battlefield.
     * Equip {2}
     *
     */

    // test that when creature damaged by equipped creature dies a Zombie token is created
    @Test
    public void testDamagedCreatureDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Unscythe, Killer of Kings");
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Prodigal Pyromancer");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage to ", "Sejiri Merfolk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertExileCount("Sejiri Merfolk", 1);

    }

    // test that when creature damaged by equipped creature dies a Zombie token is created
    @Test
    public void testDamagedCreatureDiesAfterEquipped() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Unscythe, Killer of Kings");
        // {T}: Prodigal Pyromancer deals 1 damage to 
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage to ", "Craw Wurm");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Prodigal Pyromancer");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Craw Wurm", "Equip", StackClause.WHILE_NOT_ON_STACK);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Craw Wurm", 0);
        assertPermanentCount(playerA, "Zombie Token", 1);
        assertExileCount("Craw Wurm", 1);

    }

}
