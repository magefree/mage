package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SquadTest extends CardTestPlayerBase {

    /*
     * Rulling at 2022-11-15
     * 702.157. Squad
     * 702.157a Squad is a keyword that represents two linked abilities.
     * The first is a static ability that functions while the creature
     * spell with squad is on the stack. The second is a triggered ability
     * that functions when the creature with squad enters the battlefield.
     * “Squad [cost]” means “As an additional cost to cast this spell,
     * youmay pay [cost] any number of times” and “When this creature
     * enters the battlefield, if its squad cost was paid, create a token
     * that’s a copy of it for each time its squad cost was paid.” Paying
     * a spell’s squad cost follows the rules for paying additional costs
     * in rules 601.2b and 601.2f–h.
     * 702.157b If a spell has multiple instances of squad, each is paid separately.
     * If a permanent has multiple instances of squad, each triggers based
     * on the payments made for that squad ability as it was cast, not based
     * on payments for any other instance of squad.
     */

    /**
     * Arco-Flagellant
     * {2}{B} 3/1
     * Creature — Human
     *
     * Squad {2} (As an additional cost to cast this spell, you may pay {2} any number of times. When this creature enters the battlefield, create that many tokens that are copies of it.)
     * Arco-Flagellant can’t block.
     * Endurant — Pay 3 life: Arco-Flagellant gains indestructible until end of turn.
     */
    private static final String flagellant = "Arco-Flagellant";

    private static final String swamp = "Swamp";

    @Test
    public void test_Squad_NotUsed_Manual() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 5);
        addCard(Zone.HAND, playerA, flagellant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, flagellant, 1);
    }

    @Test
    @Ignore
    // TODO: enable test after squad/replicate ability will be supported by AI
    public void test_Squad_NotUsed_AI() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 5 - 1); // haven't all mana
        addCard(Zone.HAND, playerA, flagellant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        //setChoice(playerA, false); - AI must choose

        //setStrictChooseMode(true); - AI must choose
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, flagellant, 1);
    }

    @Test
    public void test_Squad_UseOnce() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 5);
        addCard(Zone.HAND, playerA, flagellant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, flagellant, 2);
    }

    @Test
    public void test_Squad_UseTwice() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 7);
        addCard(Zone.HAND, playerA, flagellant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, flagellant, 3);
    }

    @Test
    public void test_ZCC_ReturnedFromGraveyardMustNotRememberSquad() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 9); // 3 + 2 + 4
        addCard(Zone.HAND, playerA, flagellant, 1);
        // Return target creature card from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Zombify", 1);

        addCard(Zone.BATTLEFIELD, playerB, swamp, 1);
        // Target creature gets -2/-2 until end of turn.
        addCard(Zone.HAND, playerB, "Disfigure", 1);

        // casting with squad
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);  // use squad once.
        setChoice(playerA, false);

        // poor flagellant dies a horrible death
        addTarget(playerB, flagellant + "[no copy]");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Disfigure");

        // return the flagellant from graveyard
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombify", flagellant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Disfigure", 1);
        assertGraveyardCount(playerA, "Zombify", 1);
        assertPermanentCount(playerA, flagellant, 2); // The first token of squad + the zombified original
    }

    @Test
    public void test_ZCC_ReturnedToHandPermanentMustNotRememberSquad() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 8); // 3 + 2 + 3
        addCard(Zone.HAND, playerA, flagellant, 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Return target permanent to its owner's hand.
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        // first cast paying for squad
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);  // use squad once.
        setChoice(playerA, false);

        // return to hand
        addTarget(playerB, flagellant+"[no copy]");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Boomerang");

        // second cast not paying for squad
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, false);  // no squad

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertPermanentCount(playerA, flagellant, 2); // The first token of squad + the recasted original
    }

    @Test
    public void test_ZCC_BlinkMustNotRememberSquad() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 5); // 3 + 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, flagellant, 1);
        // Exile target creature you control, then return it to the battlefield under its owner’s control.
        // Rebound
        addCard(Zone.HAND, playerA, "Ephemerate", 1);

        // first cast paying for squad
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);  // use squad once.
        setChoice(playerA, false);

        // casting Ephemerate
        addTarget(playerA, flagellant+"[no copy]");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Ephemerate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flagellant, 2); // The first token of squad + the blinked back original
    }

    // The squad status is a copiable value of the spell, and should be carried over on copy.
    @Ignore
    @Test
    //TODO: Enable after fixing subability copying twice bug
    public void test_CopyingSpellMustKeepSquadStatus() {

        addCard(Zone.HAND, playerA, flagellant, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5); // 3 + 2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // Copy target creature spell you control, except it isn’t legendary if the spell is legendary.
        addCard(Zone.HAND, playerA, "Double Major", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5);
        // cast and pay once for squad, then copy it (squad status must be copied)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true); // pay squad once
        setChoice(playerA, false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", flagellant, flagellant, StackClause.WHILE_ON_STACK);
        checkStackSize("before copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // flagellant + double major
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // spell + copy

        //setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flagellant, 4); // One original + its squad buddy + the copy + the copy's squad buddy
    }

    // Copying the trigger remembers the squad status.
    @Test
    public void test_CopyingETBTriggerMustKeepSquadStatus() {

        addCard(Zone.HAND, playerA, flagellant, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7); // 3 + 2 + 2

        /*
         * Strionic Resonator
         * {2}
         * Artifact
         * {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Strionic Resonator", 1);

        // cast and pay once for squad, then copy it (squad status must be copied)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true); // pay squad once
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        // flagellant does resolve, its squad trigger goes in the stack.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.");

        //setStrictChooseMode(true); // Could not make it work for explicitly target the trigger with Lithoform Engine
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, flagellant, 3); // One original + its squad buddy + the squad buddy from the copied trigger
    }

    @Test
    public void test_PanharmoniconDoubleSquadETBStatus() {

        addCard(Zone.HAND, playerA, flagellant, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5); // 3 + 2

        // If an artifact or creature entering the battlefield causes a triggered ability
        // of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon", 1);

        // cast and pay once for squad, then copy it (squad status must be copied)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true); // pay squad once
        setChoice(playerA, false);

        //setStrictChooseMode(true); // There is a double trigger to put in the stack, not sure how to order them.
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flagellant, 3); // One original + its squad buddy + the squad buddy from the additional trigger
    }

    @Ignore
    @Test
    //TODO: Enable after fixing clones activating it if they have the same zcc. Also affects Kicker
    public void test_CloneMustNotCopySquad() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 8); // 3 + 2 + 3
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, flagellant + "@flaggy", 1);
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 5);

        // cast paying for squad
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);  // use squad once.
        setChoice(playerA, false);

        // cloning the flagellant
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);  // yes to the 'may copy'
        setChoice(playerA, "@flaggy");  // cloning the original flagellant.

        //setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flagellant, 3); // The original + its token + the clone
    }

    @Test
    public void test_CopyMustNotCopySquad() {
        addCard(Zone.BATTLEFIELD, playerA, swamp, 8); // 3 + 2
        addCard(Zone.HAND, playerA, flagellant + "@flaggy", 1);

        //{T}: Create a token that’s a copy of target nonlegendary creature you control,
        //     except it has haste. Sacrifice it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Kiki-Jiki, Mirror Breaker", 1);

        // casting with a squad buddy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flagellant);
        setChoice(playerA, true);  // use squad once.
        setChoice(playerA, false);

        // copying the original flagellant with kiki-jiki
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Create a token", "@flaggy");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, flagellant, 3); // The original + the first token of squad + the copy token
    }

    @Test
    public void test_Squad_FreeCast() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, flagellant, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        // Whenever Etali, Primal Storm attacks, exile the top card of each player's library,
        // then you may cast any number of nonland cards exiled this way without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Etali, Primal Storm", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arco-Flagellant", false);

        // attack and prepare free cast, pay squad
        attack(1, playerA, "Etali, Primal Storm", playerB);
        setChoice(playerA, true); // cast for free
        setChoice(playerA, true); // pay squad once.
        setChoice(playerA, true); // pay squad twice.
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flagellant, 3); // card + 2 buddies
    }
}
