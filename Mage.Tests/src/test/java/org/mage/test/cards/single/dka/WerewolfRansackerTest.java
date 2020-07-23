package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class WerewolfRansackerTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // When Blade Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.
        // Golem creatures you control have first strike.
        addCard(Zone.HAND, playerA, "Blade Splicer");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        // At the beginning of each upkeep, if no spells were cast last turn, transform Afflicted Deserter.
        // Werewolf Ransacker
        // Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf Ransacker.
        addCard(Zone.HAND, playerB, "Afflicted Deserter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blade Splicer");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Afflicted Deserter");
        setStopAt(4, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Afflicted Deserter", 0);
        assertPermanentCount(playerB, "Werewolf Ransacker", 1);
        assertPermanentCount(playerA, "Blade Splicer", 1);
        assertPermanentCount(playerA, "Golem", 0);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 14);
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 1);
        assertPermanentCount(playerA, "Werewolf Ransacker", 0);
    }
}
