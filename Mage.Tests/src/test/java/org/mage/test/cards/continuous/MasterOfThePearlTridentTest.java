package org.mage.test.cards.continuous;

import mage.abilities.keyword.IslandwalkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class MasterOfThePearlTridentTest extends CardTestPlayerBase {

    @Test
    public void testLordAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Master of the Pearl Trident", 1);
        assertLife(playerB, 18);
        assertPowerToughness(playerA, "Merfolk of the Pearl Trident", 2, 2);
        assertAbility(playerA, "Merfolk of the Pearl Trident", new IslandwalkAbility(), true);
    }

    @Test
    public void testLordAbilityGone() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerB, "Murder", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }

    @Test
    public void testTurnToFrog() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Master of the Pearl Trident");
        // Creature — Merfolk 2/2, UU
        // Other Merfolk creatures you control get +1/+1 and have islandwalk. (They can't be blocked as long as defending player controls an Island.)

        addCard(Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        // Creature — Elf Druid 1/1, G
        // {T}: Add {G} to your mana pool.
        addCard(Zone.HAND, playerB, "Turn to Frog");
        // Target creature loses all abilities and becomes a 1/1 blue Frog until end of turn.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Turn to Frog", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }

    @Test
    public void testTurnToFrogAndMurder() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.HAND, playerB, "Turn to Frog");
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, PhaseStep.BEGIN_COMBAT, playerB, "Turn to Frog", "Master of the Pearl Trident");
        castSpell(3, PhaseStep.DECLARE_ATTACKERS, playerB, "Murder", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Turn to Frog", 1);
        assertGraveyardCount(playerB, "Murder", 1);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }

    /*
     * Control of Master changes to player B in turn 2, after
     */
    @Test
    public void testLooseAndGainControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Master of the Pearl Trident");
        addCard(Zone.HAND, playerA, "Merfolk of the Pearl Trident");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Zealous Conscripts");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Zealous Conscripts");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk of the Pearl Trident");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Master of the Pearl Trident", 1);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 1);
        assertPowerToughness(playerA, "Merfolk of the Pearl Trident", 2, 2);

        assertPermanentCount(playerB, "Zealous Conscripts", 1);

    }


}
