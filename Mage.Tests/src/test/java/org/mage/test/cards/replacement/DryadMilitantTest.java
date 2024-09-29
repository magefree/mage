package org.mage.test.cards.replacement;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.watchers.common.CardsExiledThisTurnWatcher;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Dryad Militant oracle rules:
 * <p>
 * If an instant or sorcery spell destroys Dryad Militant directly (like Murder does), that instant or sorcery
 * card will be put into its owner’s graveyard. However, if an instant or sorcery card deals lethal damage to
 * Dryad Militant, Dryad Militant will remain on the battlefield until the next time state-based actions are
 * checked, which is after the instant or sorcery finishes resolving.
 * The instant or sorcery will be exiled.
 * (2012-10-01) - supported, has tests
 * <p>
 * If an instant or sorcery card is discarded while Dryad Militant is on the battlefield, abilities that
 * function when a card is discarded (such as madness) still work, even though that card never reaches a
 * graveyard. In addition, spells or abilities that check the characteristics of a discarded card (such as
 * Chandra Ablaze’s first ability) can find that card in exile.
 * (2012-10-01) - supported, has tests
 *
 * @author LevelX2, JayDi85
 */
public class DryadMilitantTest extends CardTestPlayerBase {

    /**
     * Tests that an instant or sorcery card is moved to exile
     */
    @Test
    public void test_OnResolve_MustWork() {
        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertExileCount("Lightning Bolt", 1);
    }

    /**
     * Tests if Dryad Militant dies by damage spell, the
     * spell gets exiled (see oracle rules)
     */
    @Test
    public void test_DiesByDamage_MustWork() {
        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dryad Militant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertExileCount("Lightning Bolt", 1);
    }

    /**
     * Tests if Dryad Militant dies by destroy spell, the
     * spell don't get exiled (see oracle rules)
     */
    @Test
    public void test_DiesByDestroy_MustNotWork() {
        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");
        //
        // Destroy target creature. It can't be regenerated.
        addCard(Zone.HAND, playerA, "Terminate"); // {B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminate", "Dryad Militant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20);
        assertHandCount(playerA, "Terminate", 0);
        assertGraveyardCount(playerA, "Terminate", 1);
    }

    private void prepareGraveyardAndExileWatchers() {
        currentGame.getState().addWatcher(new CardsPutIntoGraveyardWatcher());
        currentGame.getState().addWatcher(new CardsExiledThisTurnWatcher());
    }

    private void assertCardsList(String info, Collection<Card> currentList, Collection<String> needList) {
        String current = currentList.stream().map(Card::getName).sorted().collect(Collectors.joining("; "));
        String need = needList.stream().sorted().collect(Collectors.joining("; "));
        Assert.assertEquals(info, need, current);
    }

    private void assertReachesGraveyard(Collection<String> needList) {
        CardsPutIntoGraveyardWatcher graveyardWatcher = currentGame.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        assertCardsList(
                "graveyard must reaches " + needList.size() + " cards this turn",
                graveyardWatcher.getCardsPutIntoGraveyardFromAnywhere(currentGame),
                needList
        );
    }

    private void assertReachesExile(Collection<String> needList) {
        CardsExiledThisTurnWatcher exileWatcher = currentGame.getState().getWatcher(CardsExiledThisTurnWatcher.class);
        assertCardsList(
                "exile must reaches " + needList.size() + " cards this turn",
                exileWatcher.getCardsExiledThisTurn(currentGame),
                needList
        );
    }

    @Test
    public void test_MadnessCreature_OnDiscard() {
        prepareGraveyardAndExileWatchers();

        // Madness {B}{B} (If you discard this card, discard it into exile. When you do, cast it for its madness cost
        // or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Gorgon Recluse"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // discard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, true); // use madness

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // raven on cast go to grave
        // gorgon on madness go to exile and cast instead grave
        assertReachesGraveyard(Arrays.asList("Raven's Crime"));
        assertReachesExile(Arrays.asList("Gorgon Recluse")); // was cast from exile
    }

    @Test
    public void test_MadnessSorcery_OnDiscard() {
        prepareGraveyardAndExileWatchers();

        // Alchemist’s Greeting deals 4 damage to target creature.
        // Madness {1}{R} (If you discard this card, discard it into exile. When you do, cast it for its madness
        // cost or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Alchemist's Greeting"); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        //
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // discard
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, true); // use madness
        addTarget(playerA, "Grizzly Bears"); // target of madness card

        showGraveyard("after grave", 1, PhaseStep.END_TURN, playerA);
        showExile("after exile", 1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Alchemist's Greeting", 1); // go to grave after madness cast resolve

        // raven on cast go to grave
        // alchemist on discard with madness go to exile and grave
        // bears on damage go to grave
        assertReachesGraveyard(Arrays.asList("Raven's Crime", "Alchemist's Greeting", "Grizzly Bears"));
        assertReachesExile(Arrays.asList("Alchemist's Greeting"));
    }

    /**
     * Make sure no exile zone reaches due replacement effects (see oracle rules)
     */
    @Test
    public void test_MadnessSorcery_OnDiscardWithDryadMilitant_BySpell() {
        prepareGraveyardAndExileWatchers();

        // checking rule:
        // If an instant or sorcery card is discarded while Dryad Militant is on the battlefield, abilities that
        // function when a card is discarded (such as madness) still work, even though that card never reaches a
        // graveyard.

        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");
        //
        // Alchemist’s Greeting deals 4 damage to target creature.
        // Madness {1}{R} (If you discard this card, discard it into exile. When you do, cast it for its madness
        // cost or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Alchemist's Greeting"); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        //
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // discard
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, "Alchemist's Greeting"); // choose replacement effects (madness first)
        setChoice(playerA, true); // use madness
        addTarget(playerA, "Grizzly Bears"); // target of madness card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Alchemist's Greeting", 1); // discard -> exile and cast madness -> exile instead grave

        // raven on cast go to exile instead grave due dryad effect
        // alchemist on discard with madness go to exile, after cast go to exile again instead grave due dryad effect
        // bears on damage go to grave (ignored by dryad effect due it's not a instant/sorcery)
        assertReachesGraveyard(Arrays.asList("Grizzly Bears"));
        assertReachesExile(Arrays.asList("Raven's Crime", "Alchemist's Greeting", "Alchemist's Greeting"));
    }

    @Test
    public void test_MadnessSorcery_OnDiscardWithDryadMilitant_ByChandraAblaze() {
        prepareGraveyardAndExileWatchers();

        // checking rule:
        // In addition, spells or abilities that check the characteristics of a discarded card (such as
        // Chandra Ablaze’s first ability) can find that card in exile.

        // If an instant or sorcery card would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Militant");
        //
        // Alchemist’s Greeting deals 4 damage to target creature.
        // Madness {1}{R} (If you discard this card, discard it into exile. When you do, cast it for its madness
        // cost or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Alchemist's Greeting"); // {4}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        //
        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Ablaze");

        // discard
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");
        addTarget(playerA, playerB); // x4 damage by chandra
        setChoice(playerA, "Alchemist's Greeting"); // discard by chandra
        setChoice(playerA, "Alchemist's Greeting"); // choose replacement effects (madness first)
        setChoice(playerA, true); // use madness
        addTarget(playerA, "Grizzly Bears"); // target of madness card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Alchemist's Greeting", 1); // discard -> exile and cast madness -> exile instead grave
        assertLife(playerB, 20 - 4); // by chandra

        // alchemist on discard with madness go to exile, after cast go to exile again instead grave due dryad effect
        // bears on damage go to grave (ignored by dryad effect due it's not a instant/sorcery)
        assertReachesGraveyard(Arrays.asList("Grizzly Bears"));
        assertReachesExile(Arrays.asList("Alchemist's Greeting", "Alchemist's Greeting"));
    }
}