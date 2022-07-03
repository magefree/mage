package org.mage.test.cards.single.a40k;

import mage.abilities.Ability;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCodePayload;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author the-red-lily
 */
public class AbaddonTheDespoilerTest extends CardTestPlayerBase {

    // Abaddon the Despoiler {2}{U}{B}{R}
    // Legendary Creature — Astartes Warrior
    //
    // Trample
    // Mark of the Chaos Ascendant — During your turn, spells you cast from your hand with mana value X or less have cascade, where X is the total amount of life your opponents have lost this turn.
    //
    // 5/5

    private static final ArrayList<String> cardsByCMC = new ArrayList<>(Arrays.asList(
            "Accorder's Shield", // 0 CMC
            "Aegis Turtle", // 2 CMC, Creature
            "Absolute Grace", // 3 CMC, Enchantment
            "Abandoned Sarcophagus",
            "Abattoir Ghoul",
            "Abbey Gargoyles",
            "Book of Rass", // 6 CMC, Artifact
            "Accomplished Automaton",
            "Coalition Victory", // 8 CMC, Sorcery
            "Apex Altisaur" // 9 CMC
    ));

    // For IDE autocomplete
    private final String abaddonTheDespoiler = "Abaddon the Despoiler";
    private final String lightningBolt = "Lightning Bolt";
    private final String mountain = "Mountain";
    private final String island = "Island";
    private final String reliquaryTower = "Reliquary Tower";
    private final String aetherize = "Aetherize"; // 4 CMC instant (For testing cascade on opponent's turn)
    private final String gildedDrake = "Gilded Drake";

    /**
     * Massive comprehensive test:
     * <ul>
     *     <li>Opponent dealing us damage doesn't give cascade</li>
     *     <li>Opponent taking damage by lightning bolt gives our spells cascade</li>
     *     <li>Doesn't give opponent cascade on our turn</li>
     *     <li>Doesn't give opponents cascade on their turn</li>
     *     <li>Doesn't give us cascade on opponent's turn</li>
     *     <li>Opponent taking combat damage by combat gives our spells cascade</li>
     *     <li>Opponent taking more damage by lightning bolt stacks with combat damage</li>
     * </ul>
     */
    @Test
    public void markOfTheChaosAscendantTest() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, abaddonTheDespoiler);
        addCard(Zone.HAND, playerA, lightningBolt, 3 + 3 + 2);
        addCard(Zone.HAND, playerB, lightningBolt, 1 + 3);
        addCard(Zone.HAND, playerA, aetherize, 1);
        addCard(Zone.HAND, playerB, aetherize, 1);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 3 + 3);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 3);
        addCard(Zone.BATTLEFIELD, playerA, island, 4);
        addCard(Zone.BATTLEFIELD, playerB, island, 4);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower, 1); // Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerB, reliquaryTower, 1); // Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 4 + 4 * 9 * (9 + 1) / 2); // Cast spells CMC 1-9, 4 times
        addCard(Zone.BATTLEFIELD, playerB, "Archway Commons", 4 + 2 * 9 * (9 + 1) / 2); // Cast spells CMC 1-9, 4 times
        addCard(Zone.LIBRARY, playerA, mountain, 2);
        addCard(Zone.LIBRARY, playerB, mountain, 2);
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerA, c, 4 + 3)); //4 on turn 1, 3 on turn 3
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerB, c, 2));
        playerB.initLife(100); // Let us deal opponent a lot of damage

        // Opponent dealing us damage doesn't give cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        assertMarkOfTheChaosAscendant("No damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // Opponent taking damage by lightning bolt gives our spells cascade
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("3 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("6 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("9 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 9);

        // Doesn't give opponent cascade on our turn
        assertMarkOfTheChaosAscendant("Doesn't give opponent cascade on our turn",
                1, PhaseStep.PRECOMBAT_MAIN, playerB, false, aetherize);

        // Doesn't give opponents cascade on their turn
        assertMarkOfTheChaosAscendant("No damage, Opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerB, -1);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("9 damage, Opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerB, -1);

        // Doesn't give us cascade on opponent's turn
        assertMarkOfTheChaosAscendant("Doesn't give us cascade on opponent's turn",
                2, PhaseStep.PRECOMBAT_MAIN, playerA, false, aetherize);

        // Opponent taking combat damage by combat gives our spells cascade
        attack(3, playerA, abaddonTheDespoiler);
        assertMarkOfTheChaosAscendant("5 combat damage", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5);

        // Opponent taking more damage by lightning bolt stacks with combat damage
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("5 combat damage + 3 burn", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5 + 3);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("5 combat damage + 3 burn", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5 + 3 + 3);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 100 - 3 * 3 - 3 * 3 - 5 - 3 * 2);
    }

    /**
     * Test for exchange of ownership
     */
    @Test
    public void exchangeOwnershipTest() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, abaddonTheDespoiler);
        addCard(Zone.HAND, playerA, lightningBolt, 4);
        addCard(Zone.HAND, playerB, lightningBolt, 2);
        addCard(Zone.HAND, playerA, aetherize, 1);
        addCard(Zone.HAND, playerB, gildedDrake, 1);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 3);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 3);
        addCard(Zone.BATTLEFIELD, playerA, island, 4);
        addCard(Zone.BATTLEFIELD, playerB, island, 2);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower, 1); // Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerB, reliquaryTower, 1); // Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 9 * (9 + 1) / 2); // Cast spells CMC 1-9, 4 times
        addCard(Zone.BATTLEFIELD, playerB, "Archway Commons", 9 * (9 + 1) / 2); // Cast spells CMC 1-9, 4 times
        addCard(Zone.LIBRARY, playerA, mountain, 1);
        addCard(Zone.LIBRARY, playerB, mountain, 1);
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerA, c, 1));
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerB, c, 1));

        // Doesn't give opponents cascade on their turn
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, gildedDrake, true);
        addTarget(playerB, abaddonTheDespoiler); //When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target creature an opponent controls.
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, playerA);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN, playerB);
        showBattlefield("after Gilded Drake", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        showBattlefield("after Gilded Drake", 2, PhaseStep.PRECOMBAT_MAIN, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("6 damage, Opponent has Abaddon", 2, PhaseStep.PRECOMBAT_MAIN, playerB, 6);

        // Doesn't give us cascade on their turn when opponent stole it
        assertMarkOfTheChaosAscendant("Doesn't give us cascade on their turn when opponent stole it",
                2, PhaseStep.PRECOMBAT_MAIN, playerA, false, aetherize);

        // Doesn't give us cascade on our turn when opponent stole it
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("Doesn't give us cascade on our turn when opponent stole it",
                3, PhaseStep.POSTCOMBAT_MAIN, playerA, -1);

        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20 - 2 * 3);
        assertLife(playerB, 20 - 4 * 3);
    }

    // Unused, but I spent the time to write it :(
    private static class TestCardInHandHasAbility implements CardTestCodePayload {

        private final String cardName;
        private final Ability ability;
        private final boolean hasAbility;
        private final FilterCard filter;

        public TestCardInHandHasAbility(String cardName, Ability ability, boolean hasAbility) {
            this.cardName = cardName;
            this.ability = ability;
            this.hasAbility = hasAbility;
            this.filter = new FilterCard(cardName);
            this.filter.add(new NamePredicate(cardName));
        }

        @Override
        public void run(String info, Player player, Game game) {
            Set<Card> filteredCardsInHand = player.getHand().getCards(filter, game);
            Assert.assertFalse("Must have card named " + cardName, filteredCardsInHand.isEmpty());
            filteredCardsInHand.forEach(card ->
                    assertEquals(getMessage(), hasAbility, card.getAbilities().containsRule(ability)));
        }

        private String getMessage() {
            return cardName + " must " + (hasAbility ? "" : "not ") + "have ability " + ability.getRule();
        }
    }

    private static class TestCardOnStackHasAbility implements CardTestCodePayload {

        private final String cardName;
        private final Ability ability;
        private final boolean hasAbility;

        public TestCardOnStackHasAbility(String cardName, Ability ability, boolean hasAbility) {
            this.cardName = cardName;
            this.ability = ability;
            this.hasAbility = hasAbility;
        }

        @Override
        public void run(String info, Player player, Game game) {
            StackObject spell = game.getStack().peek();
            assertEquals(getMessage(), hasAbility, spell.getAbilities().containsRule(ability));
        }

        private String getMessage() {
            return cardName + " must " + (hasAbility ? "" : "not ") + "have ability " + ability.getRule();
        }
    }

    private void assertMarkOfTheChaosAscendant(String message, int turnNum, PhaseStep step, TestPlayer player, int opponentsLifeLost) {
        for (int i = 0; i < cardsByCMC.size(); i++) {
            assertMarkOfTheChaosAscendant(message + " X=" + opponentsLifeLost, turnNum, step, player,
                    i <= opponentsLifeLost, cardsByCMC.get(i));
        }
    }

    private void assertMarkOfTheChaosAscendant(String message, int turnNum, PhaseStep step, TestPlayer player, boolean shouldCascade, String cardName) {
        castSpell(turnNum, step, player, cardName);
        // If shouldCascade, check that stack has 2 items. Otherwise, check it has 1
        checkStackSize(message + " " + cardName + " " + (shouldCascade ? "and Cascade " : "") + "on stack",
                turnNum, step, player, shouldCascade ? 2 : 1);
        // If shouldCascade, check that cascade is on stack. Otherwise, check that cascade is not on stack
        runCode(message, turnNum, step, player,
                new TestCardOnStackHasAbility(cardName, new CascadeAbility(false), shouldCascade));
        // Drain Stack
        waitStackResolved(turnNum, step, player);
        // No need to check effect of Cascade, implementation of Cascade is not in scope of test
    }
}
