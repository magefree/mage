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

import static org.junit.Assert.assertTrue;

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
            "Accorder's Shield", //0 CMC
            "Aegis Turtle", //1 CMC, Creature
            "Absolute Grace", //2 CMC, Enchantment
            "Abandoned Sarcophagus",
            "Abattoir Ghoul",
            "Abbey Gargoyles",
            "Book of Rass", //6 CMC, Artifact
            "Accomplished Automaton",
            "Coalition Victory", //8 CMC, Sorcery
            "Apex Altisaur" //9 CMC
    ));

    //For IDE autocomplete
    private final String abaddonTheDespoiler = "Abaddon the Despoiler";
    private final String lightningBolt = "Lightning Bolt";
    private final String mountain = "Mountain";
    private final String reliquaryTower = "Reliquary Tower";
    private final String aetherize = "Aetherize"; //4 CMC instant

    @Test
    public void markOfTheChaosAscendantTest() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, abaddonTheDespoiler);
        addCard(Zone.HAND, playerA, lightningBolt, 3 + 3 + 2);
        addCard(Zone.HAND, playerB, lightningBolt, 3);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 3 + 3);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 3);
        addCard(Zone.BATTLEFIELD, playerA, reliquaryTower, 1); //Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerB, reliquaryTower, 1); //Unlimited hand size
        addCard(Zone.BATTLEFIELD, playerA, "Archway Commons", 4 + 4 * 9 * (9 + 1) / 2); //Cast spells CMC 1-9, 4 times
        addCard(Zone.BATTLEFIELD, playerB, "Archway Commons", 4 + 2 * 9 * (9 + 1) / 2); //Cast spells CMC 1-9, 4 times
        addCard(Zone.LIBRARY, playerA, mountain, 2);
        addCard(Zone.LIBRARY, playerB, mountain, 2);
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerA, c, 4 + 3));
        cardsByCMC.forEach(c -> addCard(Zone.HAND, playerB, c, 2));
        playerB.initLife(100); //Let us deal opponent a lot of damage

        //Opponent taking damage by lightning bolt gives cards in hand cascade
        assertMarkOfTheChaosAscendant("No damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("3 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("6 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("9 damage", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 9);

        //Doesn't give opponents cascade on their turn
        assertMarkOfTheChaosAscendant("No damage, Opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerB, -1);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, lightningBolt, playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("9 damage, Opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerB, -1);

        //Opponent taking combat damage by combat gives cards in hand cascade
        attack(3, playerA, abaddonTheDespoiler);
        assertMarkOfTheChaosAscendant("5 combat damage", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5);

        //Opponent taking more damage by lightning bolt stacks with combat damage
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("5 combat damage + 3 burn", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5 + 3);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, lightningBolt, playerB, true);
        assertMarkOfTheChaosAscendant("5 combat damage + 3 burn", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 5 + 3 + 3);

        setStopAt(3, PhaseStep.END_TURN);
        execute();  // possible bug: you can catch choose dialog for duplicated upkeep triggers
        assertAllCommandsUsed();

        assertLife(playerB, 100 - 3 * 3 - 3 * 3 - 5 - 3 * 2);
    }

    private class TestCardInHandHasAbility implements CardTestCodePayload {

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
                    assertTrue(getMessage(), hasAbility == card.getAbilities().containsRule(ability)));
        }

        private String getMessage() {
            return cardName + " must " + (hasAbility ? "" : "not ") + "have ability " + ability.getRule();
        }
    }

    private class TestCardOnStackHasAbility implements CardTestCodePayload {

        private final String cardName;
        private final Ability ability;
        private final boolean hasAbility;
        private final FilterCard filter;

        public TestCardOnStackHasAbility(String cardName, Ability ability, boolean hasAbility) {
            this.cardName = cardName;
            this.ability = ability;
            this.hasAbility = hasAbility;
            this.filter = new FilterCard(cardName);
            this.filter.add(new NamePredicate(cardName));
        }

        @Override
        public void run(String info, Player player, Game game) {
            StackObject spell = game.getStack().peek();
            assertTrue(getMessage(), hasAbility == spell.getAbilities().containsRule(ability));
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
        //If shouldCascade, check that stack has 2 items. Otherwise, check it has 1
        checkStackSize(message + " " + cardName + " " + (shouldCascade ? "and Cascade " : "") + "on stack",
                turnNum, step, player, shouldCascade ? 2 : 1);
        //If shouldCascade, check that cascade is on stack. Otherwise, check that cascade is not on stack
        runCode(message, turnNum, step, player,
                new TestCardOnStackHasAbility(cardName, new CascadeAbility(false), shouldCascade));
        //Drain Stack
        waitStackResolved(turnNum, step, player);
        //No need to check effect of Cascade, implementation of Cascade is not in scope of test
    }
}
