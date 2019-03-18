
package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class BrutalExpulsionTest extends CardTestPlayerBase {

    @Test
    public void test_useSecondModeOnCreature() {
        // Choose one or both
        // - Return target spell or creature to its owner's hand.
        // - Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Brutal Expulsion"); // {2}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1); // 1 life
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2 life
        addCard(Zone.BATTLEFIELD, playerB, "Razorclaw Bear", 1); // 3 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brutal Expulsion", "mode=2Augmenting Automaton");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, null); // ignore last one mode
        //addTarget(playerA, "mode=2Augmenting Automaton"); // doesn't work with mode

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Brutal Expulsion", 0);
        assertPermanentCount(playerB, "Augmenting Automaton", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Razorclaw Bear", 1);
    }

    @Test
    public void test_useSecondModeOnPlaneswalker() {
        // Choose one or both
        // - Return target spell or creature to its owner's hand.
        // - Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Brutal Expulsion"); // {2}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1); // 1 life
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2 life
        addCard(Zone.BATTLEFIELD, playerB, "Razorclaw Bear", 1); // 3 life
        addCard(Zone.BATTLEFIELD, playerB, "Kiora, the Crashing Wave"); // 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brutal Expulsion", "mode=2Kiora, the Crashing Wave");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, null); // ignore last one mode

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Brutal Expulsion", 0);
        assertPermanentCount(playerB, "Augmenting Automaton", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Razorclaw Bear", 1);
        assertPermanentCount(playerB, "Kiora, the Crashing Wave", 0);
    }

    @Test
    public void test_useSpellOnPlaneswalker() {
        addCard(Zone.HAND, playerA, "Shock"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton", 1); // 1 life
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2 life
        addCard(Zone.BATTLEFIELD, playerB, "Razorclaw Bear", 1); // 3 life
        addCard(Zone.BATTLEFIELD, playerB, "Kiora, the Crashing Wave"); // 2 life

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Kiora, the Crashing Wave");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Shock", 0);
        assertPermanentCount(playerB, "Augmenting Automaton", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Razorclaw Bear", 1);
        assertPermanentCount(playerB, "Kiora, the Crashing Wave", 0);
    }

    /**
     * Brutal Expulsion targeting Gideon, Ally of Zendikar. Gideon has 3
     * loyalty. Brutal Expulsion resolves, leaves 1 loyalty. I attack Gideon for
     * 1 with a Scion token, Gideon dies. Instead of going to graveyard,
     * Expulsion sends Gideon to exile. However, in game Gideon went to
     * graveyard.
     */
    @Test
    public void testPlaneswalkerExile() {
        // Choose one or both
        // - Return target spell or creature to its owner's hand.
        // - Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Brutal Expulsion"); // {2}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Shock deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Shock"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Planeswalker with 4 loyalty.
        addCard(Zone.BATTLEFIELD, playerB, "Gideon, Ally of Zendikar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brutal Expulsion", "mode=2Gideon, Ally of Zendikar");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, null); // ignore last one mode
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Gideon, Ally of Zendikar");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Shock", 0);
        assertPermanentCount(playerB, "Gideon, Ally of Zendikar", 0);
        assertExileCount("Gideon, Ally of Zendikar", 1);
    }

}
