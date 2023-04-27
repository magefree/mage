package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Pillar of Flame: Pillar of Flame deals 2 damage to any target.
 * If a creature dealt damage this way would die this turn, exile it instead.
 *
 * @author LevelX2
 */
public class PillarOfFlameTest extends CardTestPlayerBase {

    /**
     * Tests when cast Pillar of Flame targeting opponent there is no influence
     * on dying creature of opponent
     */
    @Test
    public void testNotTriggeringExileItInstead() {
        // Soulbond
        // As long as Lightning Mauler is paired with another creature, both creatures have haste.
        addCard(Zone.BATTLEFIELD, playerA, "Lightning Mauler"); // 2/1
        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerA, "Rakdos Cackler"); // 1/1

        // Pillar of Flame deals 2 damage to any target.
        // If a creature dealt damage this way would die this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Pillar of Flame");
        // Soulbond
        // As long as Stonewright is paired with another creature, each of those creatures has "{R}: This creature gets +1/+0 until end of turn."
        addCard(Zone.HAND, playerA, "Stonewright");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Dutiful Thrull");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillar of Flame", playerB);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Stonewright");

        attack(3, playerA, "Rakdos Cackler");
        attack(3, playerA, "Lightning Mauler");
        block(3, playerB, "Dutiful Thrull", "Lightning Mauler");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);

        assertPermanentCount(playerA, "Rakdos Cackler", 1);
        assertPermanentCount(playerA, "Lightning Mauler", 0);
        assertPermanentCount(playerA, "Stonewright", 1);
        assertGraveyardCount(playerA, 2);

        assertGraveyardCount(playerB, 1);
    }

    /**
     * Tests when cast Pillar of Flame targeting creature it goes to exile if
     * dying later
     */
    @Test
    public void testTriggeringExileItInstead() {
        addCard(Zone.BATTLEFIELD, playerA, "Lightning Mauler");

        addCard(Zone.HAND, playerA, "Pillar of Flame");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Warmind Infantry");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillar of Flame", "Warmind Infantry");

        attack(3, playerA, "Lightning Mauler");
        block(3, playerB, "Warmind Infantry", "Lightning Mauler");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Lightning Mauler", 0);
        assertGraveyardCount(playerA, 2);

        assertPermanentCount(playerB, "Warmind Infantry", 0);
        assertGraveyardCount(playerB, 0);
    }

}
