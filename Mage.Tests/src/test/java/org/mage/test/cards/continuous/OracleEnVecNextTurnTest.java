package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 400.7
 * An object that moves from one zone to another becomes a new object with no memory of, or
 * relation to, its previous existence. This rule has the following exceptions.
 * <p>
 * 702.26b
 * If a permanent phases out, its status changes to “phased out.” Except for rules and effects
 * that specifically mention phased-out permanents, a phased-out permanent is treated as though
 * it does not exist. It can’t affect or be affected by anything else in the game. A permanent
 * that phases out is removed from combat. (See rule 506.4.)
 *
 * @author JayDi85
 */
public class OracleEnVecNextTurnTest extends CardTestPlayerBase {

    @Test
    public void test_SingleOpponentMustAttack() {
        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle en-Vec");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Angelic Wall", 1); // wall, can't attack
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1); // 2/2

        // 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent", playerB);
        setChoice(playerB, "Balduvian Bears");
        setChoice(playerB, "Angelic Wall");
        setChoice(playerB, TestPlayer.CHOICE_SKIP);
        //
        checkLife("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 1);
        checkPermanentCount("turn 1", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 2 - attack and destroy at the end
        checkLife("turn 2", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 1);
        checkPermanentCount("turn 2a", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 3 - nothing
        // after destroy at the end
        checkLife("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 0);
        checkPermanentCount("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        // 4 - nothing
        checkLife("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Balduvian Bears", 1);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Angelic Wall", 0);
        checkPermanentCount("turn 4", 4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ashcoat Bear", 1);

        setStopAt(4, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_ChosenCreatureBlinked_NewObjectNotDestroyed() {
        addCustomEffect_TargetBlink(playerB);

        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle en-Vec");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Angelic Wall", 1); // wall, can't attack
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Stone", 1); // wall, can't attack

        // turn 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent", playerB);
        setChoice(playerB, "Angelic Wall");
        setChoice(playerB, "Wall of Stone"); // all creatures chosen, no skip needed

        // turn 2 - the new object of a chosen creature is not a chosen creature
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "target blink", "Angelic Wall");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerB, "Angelic Wall", 1);
        assertGraveyardCount(playerB, "Wall of Stone", 1);
    }

    @Test
    public void test_ChosenCreaturePhasedOut_NotDestroyed() {
        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle en-Vec");
        //
        // Put a +1/+1 counter on target creature. It phases out.
        addCard(Zone.HAND, playerB, "Slip Out the Back"); // {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Angelic Wall", 1); // wall, can't attack
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Stone", 1); // wall, can't attack

        // turn 1 - activate
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent", playerB);
        setChoice(playerB, "Angelic Wall");
        setChoice(playerB, "Wall of Stone"); // all creatures chosen, no skip needed

        // turn 2 - a phased out permanent is treated as though it doesn't exist at the end step
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Slip Out the Back", "Angelic Wall");

        // turn 4 - phased in again at playerB's untap step
        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Angelic Wall", 1);
        assertGraveyardCount(playerB, "Wall of Stone", 1);
    }
}
