package org.mage.test.cards.planeswalker;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JaceTest extends CardTestPlayerBase {

    @Test
    public void TelepathUnboundSecondAbility() {
        // +1: Up to one target creature gets -2/-0 until your next turn.
        // -3: You may cast target instant or sorcery card from your graveyard this turn. If that card would be put into your graveyard this turn, exile it instead.
        // -9: You get an emblem with "Whenever you cast a spell, target opponent puts the top five cards of their library into their graveyard".
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Telepath Unbound"); // starts with 7 Loyality counters

        // As an additional cost to cast Magmatic Insight, discard a land card.
        // Draw two cards.
        addCard(Zone.GRAVEYARD, playerA, "Magmatic Insight");// {R}
        addCard(Zone.HAND, playerA, "Plains");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3: You may cast target instant", "Magmatic Insight");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Magmatic Insight");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Jace, Telepath Unbound", 1);
        assertCounterCount("Jace, Telepath Unbound", CounterType.LOYALTY, 2);  // 5 - 3 = 2

        assertExileCount("Magmatic Insight", 1);

        assertHandCount(playerA, 2);

    }

    /**
     * I know it's been a bit a rules question recently but I believe flip
     * planeswalkers shouldn't be exiled by Containment priest when flipping as
     * happens when using xmage (at least with Jace).
     */
    @Test
    public void testContainmentPriestWithFlipPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 4);

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard,
        // exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Vryn's Prodigy", 1); // {U}{1} - 0/2
        addCard(Zone.HAND, playerA, "Pillarfield Ox", 1);

        // Flash
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Containment Priest", 1); // {2}{U}{U}

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card"); // The Ox is auto-chosen

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertExileCount("Jace, Vryn's Prodigy", 0);
        assertPermanentCount(playerA, "Jace, Telepath Unbound", 1);
    }

    /**
     * Rollback doesn't unflip a newly flipped Jace #1973 Reporting on behalf of
     * someone else (but he plays a lot on xmage) so I don't have any further
     * information, but a rollback didn't reset a jace, vryn's prodigy. Haven't
     * tried to recreate it.
     */
    @Test
    public void rollbackDoesntUnflipJaceTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.GRAVEYARD, playerA, "Mountain", 4);

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard,
        // exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Vryn's Prodigy", 1); // {U}{1} - 0/2
        addCard(Zone.HAND, playerA, "Pillarfield Ox", 1);

        // Flash
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Containment Priest", 1); // {2}{U}{U}

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card"); // The Ox is auto-chosen

        rollbackTurns(3, PhaseStep.BEGIN_COMBAT, playerA, 0); // Start of turn 3

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Pillarfield Ox", 0); // Goes back to hand
        assertHandCount(playerA, "Pillarfield Ox", 1);

        assertExileCount("Jace, Vryn's Prodigy", 0);

        assertPermanentCount(playerA, "Jace, Telepath Unbound", 0);
        assertPermanentCount(playerA, "Jace, Vryn's Prodigy", 1);

        Assert.assertFalse("Jace, Vryn's Prodigy may not be flipped", getPermanent("Jace, Vryn's Prodigy").isFlipped());
    }

    @Test
    public void vrynCannotCastAncestralVisions() {

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard,
        // exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        String jVryn = "Jace, Vryn's Prodigy"; // {U}{1} 0/2

        //−3: You may cast target instant or sorcery card from your graveyard this turn.
        //    If that card would be put into your graveyard this turn, exile it instead.
        String jTelepath = "Jace, Telepath Unbound"; // 5 loyalty

        // Sorcery, Suspend 4 {U}. Target player draws three cards.
        String ancestralVision = "Ancestral Vision";

        addCard(Zone.BATTLEFIELD, playerA, "Jace, Vryn's Prodigy", 1); // {U}{1} - 0/2
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.GRAVEYARD, playerA, "Island", 4);
        addCard(Zone.GRAVEYARD, playerA, ancestralVision);
        addCard(Zone.HAND, playerA, "Swamp", 1);

        setStrictChooseMode(true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:");
        setChoice(playerA, "Swamp");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-3:");
        addTarget(playerA, ancestralVision);

        checkPlayableAbility("Can't cast Ancestral Vision", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast" + ancestralVision, false);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, jTelepath, 1);
        assertGraveyardCount(playerA, "Swamp", 1);
        assertGraveyardCount(playerA, ancestralVision, 1);
        assertHandCount(playerA, 2); // 1 draw step + jace draw card
        assertCounterCount(playerA, jTelepath, CounterType.LOYALTY, 2);
    }

    /**
     * I know it's been a bit a rules question recently but I believe flip
     * planeswalkers shouldn't be exiled by Containment priest when flipping as
     * happens when using xmage (at least with Jace).
     */
    @Test
    public void testJaceUnravelerOfSecretsEmblem() {
        // +1: Scry 1, then draw a card.
        // -2: Return target creature to its owner's hand.
        // -8: You get an emblem with "Whenever an opponent casts their first spell each turn, counter that spell."
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Unraveler of Secrets", 1); // starts with 5 Loyality counters
        addCounters(1, PhaseStep.UPKEEP, playerA, "Jace, Unraveler of Secrets", CounterType.LOYALTY, 5);

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Perimeter Captain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Perimeter Captain", true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Perimeter Captain");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertEmblemCount(playerA, 1);

        assertPermanentCount(playerB, "Perimeter Captain", 1);
        assertGraveyardCount(playerB, "Perimeter Captain", 1);
    }
}
