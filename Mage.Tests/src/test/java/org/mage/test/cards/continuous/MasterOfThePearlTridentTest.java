package org.mage.test.cards.continuous;

import mage.Constants;
import mage.abilities.keyword.IslandwalkAbility;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class MasterOfThePearlTridentTest extends CardTestPlayerBase {

    @Test
    public void testLordAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();


        assertPermanentCount(playerA, "Master of the Pearl Trident", 1);
        assertLife(playerB, 18);
        assertPowerToughness(playerA, "Merfolk of the Pearl Trident", 2, 2);
        assertAbility(playerA, "Merfolk of the Pearl Trident", new IslandwalkAbility(), true);
    }

    @Test
    public void testLordAbilityGone() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island");
        addCard(Constants.Zone.HAND, playerB, "Murder");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, Constants.PhaseStep.DECLARE_ATTACKERS, playerB, "Murder", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }

    @Test
    public void testTurnToFrog() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.HAND, playerB, "Turn to Frog");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, Constants.PhaseStep.DECLARE_ATTACKERS, playerB, "Turn to Frog", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }

    @Test
    public void testTurnToFrogAndMurder() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Master of the Pearl Trident");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Merfolk of the Pearl Trident");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.HAND, playerB, "Turn to Frog");
        addCard(Constants.Zone.HAND, playerB, "Murder");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Master of the Pearl Trident");
        castSpell(3, Constants.PhaseStep.BEGIN_COMBAT, playerB, "Turn to Frog", "Master of the Pearl Trident");
        castSpell(3, Constants.PhaseStep.DECLARE_ATTACKERS, playerB, "Murder", "Master of the Pearl Trident");

        attack(3, playerA, "Merfolk of the Pearl Trident");
        block(3, playerB, "Llanowar Elves", "Merfolk of the Pearl Trident");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Turn to Frog", 1);
        assertGraveyardCount(playerB, "Murder", 1);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Merfolk of the Pearl Trident", 0);
        assertPermanentCount(playerB, "Llanowar Elves", 0);
    }
}
