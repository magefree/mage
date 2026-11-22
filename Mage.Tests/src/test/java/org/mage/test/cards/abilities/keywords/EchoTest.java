package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class EchoTest extends CardTestPlayerBase {

    /*
     *    I flickered an Avalanche Riders with its Echo trigger on the stack with Restoration Angel.
     *    When the trigger resolved, my Riders was sacrificed, even though it should have been
     *    considered a new permanent.
     */

    @Test
    public void testEchoTriggerChecksIdentity() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Avalanche Riders  Creature - Human Nomad 2/2 {3}{R}
        // Haste
        // Echo (At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)
        // When Avalanche Riders enters the battlefield, destroy target land.
        addCard(Zone.HAND, playerA, "Avalanche Riders");

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Restoration Angel  {3}{W}
        // Flash
        // Flying
        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control,
        // then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Restoration Angel");


        // cast Avalanche Riders and destroy forest
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Avalanche Riders");
        addTarget(playerA, "Forest");

        // Avalanche Riders go to echo, cast Restoration Angel to restore rider (do not apply echo with 4 mana)
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        castSpell(3, PhaseStep.UPKEEP, playerA, "Restoration Angel");
        addTarget(playerA, "Avalanche Riders");
        setChoice(playerA, true); // raider do restore

        // Avalanche Riders triggered again
        addTarget(playerA, "Forest");

        // but no echo for blinked rider

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Avalanche Riders", 1);
        assertPermanentCount(playerA, "Restoration Angel", 1);

        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 2);
        assertTappedCount("Plains", true, 4);
        assertTappedCount("Mountain", true, 0);
    }

    //Deranged Hermit has been cloned with Phantasmal Image.
    //The Phantasmal Image version of the Deranged Hermit had to pay the echo cost multiple times.
    @Test
    public void testEchoTriggerClone() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 15);
        // Deranged Hermit {3}{G}{G}
        // Echo
        addCard(Zone.HAND, playerA, "Deranged Hermit");
        addCard(Zone.HAND, playerA, "Phantasmal Image");
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deranged Hermit");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", "Deranged Hermit");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        setChoice(playerA, true);
        setChoice(playerA, "Deranged Hermit");

        setChoice(playerA, "Echo {3}{G}{G}", 2); // x3 triggers from Deranged Hermit

        setChoice(playerA, true); //Pay echo costs
        setChoice(playerA, true);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Deranged Hermit", 3);
        assertTappedCount("Tropical Island", true, 15);

    }

    /**
     * 702.30a
     * Echo is a triggered ability. "Echo [cost]" means "At the beginning of your upkeep, if this permanent came under
     * your control since the beginning of your last upkeep, sacrifice it unless you pay [cost]."
     * <p>
     * Control change between your upkeeps (example: opponent's Act of Treason) must trigger it again
     */
    @Test
    public void testEchoTriggerAfterControlReturned() {
        // Echo {G}
        addCard(Zone.HAND, playerA, "Pouncing Jaguar"); // {G}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerB, "Act of Treason"); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pouncing Jaguar");

        // turn 3: first echo trigger
        setChoice(playerA, true); // pay echo

        // turn 4: opponent gains control until end of turn
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Pouncing Jaguar");
        checkPermanentCount("taken", 4, PhaseStep.END_TURN, playerB, "Pouncing Jaguar", 1);

        // turn 5: it came under your control again, so new echo trigger
        setChoice(playerA, false); // don't pay echo

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Pouncing Jaguar", 0);
        assertGraveyardCount(playerA, "Pouncing Jaguar", 1);
    }

    @Test
    public void testEchoTriggerOnlyOnceWithoutControlChange() {
        // Echo {G}
        addCard(Zone.HAND, playerA, "Pouncing Jaguar"); // {G}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pouncing Jaguar");

        // turn 3: first echo trigger
        setChoice(playerA, true); // pay echo

        // turn 5: no echo trigger

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Pouncing Jaguar", 1);
    }

    @Test
    public void testEchoNoTriggerAfterControlChangeOfAnotherPermanent() {
        // Echo {G}
        addCard(Zone.HAND, playerA, "Pouncing Jaguar"); // {G}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        //
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        addCard(Zone.HAND, playerB, "Act of Treason"); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pouncing Jaguar");

        // turn 3: first echo trigger
        setChoice(playerA, true); // pay echo

        // turn 4: opponent gains control of another creature until end of turn
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Grizzly Bears");
        checkPermanentCount("taken", 4, PhaseStep.END_TURN, playerB, "Grizzly Bears", 1);

        // turn 5: no echo trigger, Pouncing Jaguar didn't change control

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Pouncing Jaguar", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

}