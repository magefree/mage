package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 * To have a creature connive, draw a card, then discard a card. If you discarded a nonland card, put a +1/+1 counter on that creature.
 */
public class ConniveTest extends CardTestPlayerBase {

    /**
     * Connive and discard a land.
     * Creature should not get a +1/+1 counter.
     */
    @Test
    public void conniveDiscardLand() {
        // P/T : 1/2
        // {3}: Hypnotic Grifter connives
        addCard(Zone.BATTLEFIELD, playerA, "Hypnotic Grifter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Plains", 1); // To discard

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Hypnotic Grifter", 1, 2); // Discarded a land card
        assertHandCount(playerA, 1); // 1 from drawing at start of turn
        assertGraveyardCount(playerA, "Plains", 1);
    }

    /**
     * Connive and discard a creature.
     * Creature should get a +1/+1 counter.
     */
    @Test
    public void conniveDiscardCreature() {
        // P/T : 1/2
        // {3}: Hypnotic Grifter connives
        addCard(Zone.BATTLEFIELD, playerA, "Hypnotic Grifter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Ledger Shredder", 1); // To discard

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, "Ledger Shredder");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Hypnotic Grifter", 2, 3); // Discarded a non-land
        assertHandCount(playerA, 1); // 1 from drawing at start of turn
        assertGraveyardCount(playerA, "Ledger Shredder", 1);
    }

    /**
     * Connive + Madness.
     * Creature should get a +1/+1 counter and the madness card gets played.
     */
    @Test
    public void conniveMadness() {
        // P/T : 1/2
        // {3}: Hypnotic Grifter connives
        addCard(Zone.BATTLEFIELD, playerA, "Hypnotic Grifter");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        // Target opponent loses 3 life and you gain 3 life.
        // Madness {B}
        addCard(Zone.HAND, playerA, "Alms of the Vein", 1); // To discard and use mad

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, "Alms of the Vein");
        setChoice(playerA, "Yes"); // Cast for Madness
        addTarget(playerA, playerB); // Target for Alms of the Vein

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Hypnotic Grifter", 2, 3); // Discarded a non-land
        assertHandCount(playerA, 1); // 1 from drawing at start of turn
        assertGraveyardCount(playerA, "Alms of the Vein", 1);

        assertLife(playerA, 23); // 3 life gained from Alms of the Vein
        assertLife(playerB, 17); // 3 life lost from Alms of the Vein
    }

    /**
     * Obscura Confluence allows you to connive a creature you don't control.
     * It's the only card that causes you to coonnive a creature you don't control.
     */
    @Test
    public void conniveNonControlledCreature() {
        // Choose three. You may choose the same mode more than once.
        //• Until end of turn, target creature loses all abilities and has base power and toughness 1/1.
        //• Target creature connives. (Draw a card, then discard a card. If you discarded a nonland card, put a +1/+1 counter on that creature.)
        //• Target player returns a creature card from their graveyard to their hand.
        addCard(Zone.HAND, playerA, "Obscura Confluence"); // {1}{W}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.HAND, playerB, "Plains", 3); // 3 land cards for conniving
        addCard(Zone.BATTLEFIELD, playerB, "Crazed Goblin"); // 1/1 creature that is made to connive

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Obscura Confluence");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        addTarget(playerA, "Crazed Goblin");
        addTarget(playerA, "Crazed Goblin");
        addTarget(playerA, "Crazed Goblin");
        setChoice(playerB, "Plains");
        setChoice(playerB, "Plains");
        setChoice(playerB, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPowerToughness(playerB, "Crazed Goblin", 1, 1); // Discarded only lands
        assertGraveyardCount(playerB, "Plains", 3);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9252
     * Connive fizzles if the creature that connived leaves the battlefield before connive resolves.
     *
     * Ruling:
     *      If a resolving spell or ability instructs a specific creature to connive but that creature has left the battlefield,
     *      the creature still connives.
     *      If you discard a nonland card this way, you won’t put a +1/+1 counter on anything.
     *      Abilities that trigger “when [that creature] connives” will trigger.
     *      (2022-04-29)
     */
    @Test
    public void conniveDoesNotFizzle() {
        // {4}{U}
        // 3/2
        // When Psychic Pickpocket enters the battlefield, it connives.
        // When it connives this way, return up to one target nonland permanent to its owner’s hand.
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Psychic Pickpocket");
        addCard(Zone.HAND, playerA, "Swamp");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // Target for Psychic Pickpocket

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Pickpocket");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true); // Wait for ETB
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Psychic Pickpocket", "When");
        setChoice(playerA, "Swamp"); // Discard for connive
        addTarget(playerA, "Sol Ring"); // Target for Psychic Pickpocket

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertHandCount(playerA, 1); // Drew a card at start of turn
        assertHandCount(playerB, "Sol Ring", 1); // Returned by Psychic Pickpocket's ability
        assertGraveyardCount(playerA, "Swamp", 1); // Connived
        assertGraveyardCount(playerA, "Psychic Pickpocket", 1); // Destroyed by Lightning Bolt
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }
}
