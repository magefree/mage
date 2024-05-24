package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OnduRisingTest extends CardTestPlayerBase {

    /** Ondu Rising {1}{W} Sorcery
     * Whenever a creature attacks this turn, it gains lifelink until end of turn.
     * Awaken 4—{4}{W} (If you cast this spell for {4}{W}, also put four +1/+1 counters on target land you control and it becomes a 0/0 Elemental creature with haste. It’s still a land.)
     */
    private static final String onduRising = "Ondu Rising";
    private static final String lion = "Silvercoat Lion"; // 2/2
    private static final String doomBlade = "Doom Blade";
    private static final String addW = "{T}: Add {W}";

    @Test
    public void testLifelinkGained() {
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, lion, 1);
        addCard(Zone.HAND, playerB, onduRising, 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ondu Rising with awaken");
        addTarget(playerB, "Mountain");

        attack(2, playerB, "Silvercoat Lion", playerA);
        attack(2, playerB, "Mountain", playerA);
        setChoice(playerB, "Whenever "); // order triggers

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, onduRising, 1);
        assertPowerToughness(playerB, "Mountain", 4, 4);
        assertLife(playerA, 14);
        assertLife(playerB, 26);
    }

    @Test
    public void testNoFizzle() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, lion, 1);
        addCard(Zone.HAND, playerB, onduRising, 1);
        addCard(Zone.HAND, playerA, doomBlade, 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, addW);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ondu Rising with awaken");
        addTarget(playerB, "Mountain");

        attack(2, playerB, "Silvercoat Lion", playerA);
        attack(2, playerB, "Mountain", playerA);
        setChoice(playerB, "Whenever "); // order triggers
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, doomBlade, "Mountain");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, onduRising, 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerA, doomBlade, 1);
        assertLife(playerA, 18);
        assertLife(playerB, 22);
    }

}
