package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.177a
 * Exhaust is a keyword that adds additional rules to the activated ability that follows it. "Exhaust — [Cost]:
 * [Effect]" means "[Cost]: [Effect]. Activate only once."
 * <p>
 * 605.1a
 * An activated ability is a mana ability if it meets all of the following criteria: it doesn't require a target
 * (see rule 115.6), it could add mana to a player's mana pool when it resolves, it's not a loyalty ability (see rule
 * 606, "Loyalty Abilities"), and its cost and effect don't move any card to or from a library. Do not take into
 * account replacement effects that may apply, other than self-replacement effects, when evaluating these criteria.
 *
 * @author JayDi85
 */
public class ExhaustManaAbilityTest extends CardTestPlayerBase {

    /**
     * Loot, the Pathfinder {2}{G}{U}{R}
     * Legendary Creature - Beast Noble 2/4
     * Double strike, vigilance, haste
     * Exhaust - {G}, {T}: Add three mana of any one color.
     * Exhaust - {U}, {T}: Draw three cards.
     * Exhaust - {R}, {T}: Loot deals 3 damage to any target.
     */
    private static final String loot = "Loot, the Pathfinder";

    @Test
    public void test_ManaExhaust_IsManaAbility() {
        addCard(Zone.BATTLEFIELD, playerA, loot);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        setChoice(playerA, "Green");
        checkStackSize("no stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_ManaExhaust_ActivateOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, loot);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Erhnam Djinn"); // {3}{G}

        // Forest + Loot = 1 + 3
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Erhnam Djinn", true);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        setChoice(playerA, "Green");

        // Forest x2 only
        checkPlayableAbility("after", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Erhnam Djinn", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_ManaExhaust_TriggersExhaustAbilityActivated() {
        addCard(Zone.BATTLEFIELD, playerA, loot);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Whenever you activate an exhaust ability, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Rangers' Refueler");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        setChoice(playerA, "Green");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_ElvishRefueler_ManaExhaustAgainOnNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, loot);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Erhnam Djinn"); // {3}{G}
        // During your turn, as long as you haven't activated an exhaust ability this turn, you may activate exhaust
        // abilities as though they haven't been activated.
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Refueler");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        setChoice(playerA, "Green");

        // no exhaust activated this turn - Forest + Loot again
        checkPlayableAbility("again", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Erhnam Djinn", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_ElvishRefueler_ManaExhaustActivatedThisTurn_NoOtherExhaust() {
        addCard(Zone.BATTLEFIELD, playerA, loot);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1 + 2);
        // During your turn, as long as you haven't activated an exhaust ability this turn, you may activate exhaust
        // abilities as though they haven't been activated.
        // Exhaust - {1}{G}: Put a +1/+1 counter on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Refueler");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {1}{G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("used before", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {1}{G}", true);
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {G}");
        setChoice(playerA, "Green");
        checkPlayableAbility("mana exhaust activated", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhaust &mdash; {1}{G}", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
