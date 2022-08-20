package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GripOfChaosTest extends CardTestPlayerBase {

    /**
     * From #6344 I just had a game where we had an interaction between Grip of
     * Chaos, Felidar Guardian, and Panharmonicon in which the cloned Felidar
     * trigger fizzled with valid targets on field because Grip retargeted that
     * trigger onto Felidar itself, which isn't a valid target. Grip of Chaos
     * specifically states it only chooses from valid targets when retargeting,
     * so this is a bug somewhere in that interaction, though whether it only
     * happens with cloned triggers or if there's a bad interaction between Grip
     * and Felidar itself isn't clear.
     */
    @Test
    public void noValidTargetsTest() {
        // Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.
        addCard(Zone.BATTLEFIELD, playerB, "Grip of Chaos", 1); // Enchantment

        // If an artifact or creature entering the battlefield causes a triggered ability
        // of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon", 1); // Artifact

        // When Felidar Guardian enters the battlefield, you may exile another target permanent you control,
        // then return that card to the battlefield under its owner's control.
        addCard(Zone.HAND, playerA, "Felidar Guardian"); // Creature {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Felidar Guardian");

        setChoice(playerA, "When "); // Select order of Felidar trigger

        setChoice(playerB, "Whenever "); // Select order of Grip of Chaos trigger

        setChoice(playerA, true); // use for the original trigger of Felidar Guardian
        setChoice(playerA, true); // use for the copied trigger of Felidar Guardian

        addTarget(playerA, "Forest");
        addTarget(playerA, "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Felidar Guardian", 1);

        int zcc = 0;
        zcc += getPermanent("Mountain").getZoneChangeCounter(currentGame);
        zcc += getPermanent("Forest").getZoneChangeCounter(currentGame);
        zcc += getPermanent("Swamp").getZoneChangeCounter(currentGame);
        zcc += getPermanent("Plains").getZoneChangeCounter(currentGame);
        zcc += getPermanent("Panharmonicon").getZoneChangeCounter(currentGame);
        // If both select the same permanent to exile, one spell fizzles so zcc == 7 otherwise 9
        if (zcc != 7) {
            Assert.assertEquals("Sum of zone change counter should be 9", 9, zcc);
            // creates error if the random targets do select the same target twice zcc is 7 then the second trigger has an invalid target
        }
    }

    /**
     * Maybe also good situation to create an test for 9/20/2016 Panharmonicon
     *
     * In some cases involving linked abilities, an ability requires information
     * about “the exiled card.” When this happens, the ability gets multiple
     * answers. If these answers are being used to determine the value of a
     * variable, the sum is used. For example, if Elite Arcanist’s
     * enters-the-battlefield ability triggers twice, two cards are exiled. The
     * value of X in the activation cost of Elite Arcanist’s other ability is
     * the sum of the two cards’ converted mana costs. As the ability resolves,
     * you create copies of both cards and can cast none, one, or both of the
     * copies in any order.
     */
}
