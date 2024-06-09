package org.mage.test.cards.single.frf;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MobRuleTest extends CardTestPlayerBase {

    private static final String mobRule = "Mob Rule";
    /* Mob Rule {4}{R}{R} Sorcery
     * Choose one —
     * • Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn.
     * • Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn.
     */

    private static final String lord = "Merfolk Sovereign"; // 2/2
    /* Merfolk Sovereign {1}{U}{U} Creature — Merfolk Noble
     * Other Merfolk creatures you control get +1/+1.
     * {T}: Target Merfolk creature can’t be blocked this turn.
     */

    private static final String smog = "Smog Elemental"; // 3/3
    /* Smog Elemental {4}{B}{B} Creature — Elemental
     * Flying
     * Creatures with flying your opponents control get -1/-1.
     */

    private static final String bond = "Bond of Discipline";
    // {4}{W} Tap all creatures your opponents control. Creatures you control gain lifelink until end of turn.

    private static final String merfolk1 = "Merfolk of the Pearl Trident"; // 1/1 merfolk
    private static final String merfolk2 = "Coral Merfolk"; // 2/1 merfolk
    private static final String merfolk3 = "Coral Commando"; // 3/2 merfolk
    private static final String serra = "Serra Angel"; // 4/4 flying vigilance
    private static final String vizzerdrix = "Vizzerdrix"; // 6/6

    @Test
    public void testMobRuleSmall() {
        addCard(Zone.HAND, playerA, mobRule);
        addCard(Zone.HAND, playerA, bond);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 11);
        addCard(Zone.BATTLEFIELD, playerA, smog);
        addCard(Zone.BATTLEFIELD, playerB, lord); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk2); // 3/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, serra); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, vizzerdrix); // 6/6

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bond);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mobRule);
        setModeChoice(playerA, "2"); // power 3 or less

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPowerToughness(playerA, smog, 3, 3);
        assertPowerToughness(playerA, lord, 2, 2);
        assertPowerToughness(playerA, merfolk1, 2, 2);
        assertPowerToughness(playerA, merfolk2, 3, 2);
        assertPowerToughness(playerB, merfolk3, 3, 2);
        assertTapped(merfolk3, true);
        assertPowerToughness(playerA, serra, 4, 4);
        assertTapped(serra, false);
        assertPowerToughness(playerB, vizzerdrix, 6, 6);
    }


    @Test
    public void testMobRuleBig() {
        addCard(Zone.HAND, playerA, mobRule);
        addCard(Zone.HAND, playerA, bond);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 11);
        addCard(Zone.BATTLEFIELD, playerA, smog);
        addCard(Zone.BATTLEFIELD, playerB, lord); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk2); // 3/2
        addCard(Zone.BATTLEFIELD, playerB, merfolk3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, serra); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, vizzerdrix); // 6/6

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bond);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mobRule);
        setModeChoice(playerA, "1"); // power 4 or greater

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 2);
        assertPowerToughness(playerA, smog, 3, 3);
        assertPowerToughness(playerB, lord, 2, 2);
        assertPowerToughness(playerB, merfolk1, 2, 2);
        assertPowerToughness(playerB, merfolk2, 3, 2);
        assertPowerToughness(playerA, merfolk3, 3, 2);
        assertTapped(merfolk3, false);
        assertPowerToughness(playerB, serra, 3, 3);
        assertPowerToughness(playerA, vizzerdrix, 6, 6);
    }

}
