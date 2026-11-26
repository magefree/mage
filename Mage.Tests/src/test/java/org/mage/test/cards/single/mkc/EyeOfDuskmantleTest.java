package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class EyeOfDuskmantleTest extends CardTestPlayerBase {

    @Test
    public void test_PlayLandAndCastSpell_Surveilled() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider", 2); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G}
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Swamp");

        // "Cards in your graveyard you've surveilled this turn" includes cards you put into your graveyard while
        // surveilling.
        // (2024-02-02)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Swamp"); // surveil to graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Grizzly Bears"); // surveil to graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void test_NotSurveilled() {
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant");

        checkPlayableAbility("not surveilled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hill Giant", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_SurveilLeftOnTop_ThenDiscarded() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp"); // Discard a card: ...
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        // It does not include cards you saw while surveilling, left on top of your library, and then put into your
        // graveyard later that turn by other means.
        // (2024-02-02)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // surveil - leave on top, then draw it
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Grizzly Bears"); // discard

        checkPlayableAbility("seen by surveil, discarded", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_SurveilledOnPreviousTurn() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Grizzly Bears"); // surveil to graveyard
        checkPlayableAbility("this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);

        checkPlayableAbility("next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void test_Surveilled_NewObjectInGraveyard() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Raise Dead"); // {B}, return creature card to hand
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp"); // Discard a card: ...
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        // surveilled card leaves the graveyard and returns as a new object
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Grizzly Bears"); // surveil to graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise Dead", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Grizzly Bears"); // discard

        checkPlayableAbility("new object", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MandatoryAdditionalCost() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.LIBRARY, playerA, "Bone Splinters"); // {B}, as an additional cost sacrifice a creature
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        // You may only cast spells this way by paying the appropriate amount of life. You may not pay their normal
        // costs and may not pay any other alternative costs. You may still pay for additional costs, such as kicker
        // costs. If the spell has mandatory additional costs, you must pay those.
        // (2024-02-02)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Bone Splinters"); // surveil to graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bone Splinters", "Grizzly Bears");
        setChoice(playerA, "Memnite"); // sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void test_X_IsZero() {
        skipInitShuffling();
        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn. If you
        // cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        //
        addCard(Zone.HAND, playerA, "Consider"); // {U}, surveil 1, then draw
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.LIBRARY, playerA, "Fireball"); // {X}{R}, X damage

        // If you cast a spell with {X} in its mana cost this way, the only legal choice for X is 0.
        // (2024-02-02)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Fireball"); // surveil to graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fireball", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
        assertLife(playerB, 20);
    }
}
