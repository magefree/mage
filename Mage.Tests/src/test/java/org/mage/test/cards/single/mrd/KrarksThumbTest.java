package org.mage.test.cards.single.mrd;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CoinFlippedEvent;
import mage.game.events.GameEvent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KrarksThumbTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KrarksThumb KrarksThumb}
     * Legendary Artifact
     * If you would flip a coin, instead flip two coins and ignore one.
     */
    private static final String thumb = "Krark's Thumb";

    /**
     * {@link mage.cards.g.GoblinTraprunner Goblin Traprunner} {3}{R}
     * Creature — Goblin
     * Whenever Goblin Traprunner attacks, flip three coins. For each flip you win, create a 1/1 red Goblin creature token that’s tapped and attacking.
     */
    private static final String runner = "Goblin Traprunner";

    /**
     * "Whenever you flip a coin, you gain 1 life for each coin flip."
     */
    private class KrarksThumbTestTriggeredAbility extends TriggeredAbilityImpl {

        KrarksThumbTestTriggeredAbility() {
            super(Zone.BATTLEFIELD, null, false);
            setTriggerPhrase("Whenever you flip a coin, ");
        }

        private KrarksThumbTestTriggeredAbility(final KrarksThumbTestTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public KrarksThumbTestTriggeredAbility copy() {
            return new KrarksThumbTestTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.COIN_FLIPPED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (!event.getPlayerId().equals(getControllerId())) {
                return false;
            }
            CoinFlippedEvent flippedEvent = (CoinFlippedEvent) event;
            int amount = flippedEvent.getFlipCount();
            getEffects().clear();
            getEffects().add(new GainLifeEffect(amount));
            return true;
        }
    }

    // Just to make sure of the test on flip is working, without Thumb interference
    @Test
    public void test_NoThumb() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, runner);
        addCustomCardWithAbility("Test Trigger", playerA, new KrarksThumbTestTriggeredAbility());

        setFlipCoinResult(playerA, true);
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, true);
        attack(1, playerA, runner, playerB);
        setChoice(playerA, "Whenever you flip a coin, you gain 1 life", 2); // stack triggers

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 2);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_OneThumb() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, runner);
        addCard(Zone.BATTLEFIELD, playerA, thumb);
        addCustomCardWithAbility("Test Trigger", playerA, new KrarksThumbTestTriggeredAbility());

        // First flip, + 1 reroll -- result success
        setFlipCoinResult(playerA, true);
        setFlipCoinResult(playerA, true);

        // Second flip, + 1 reroll -- result success
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, true);

        // Third flip, + 1 reroll -- result failure
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, false);

        attack(1, playerA, runner, playerB);
        setChoice(playerA, "Whenever you flip a coin, you gain 2 life", 2); // stack triggers

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20 + 3 * 2);
        assertPermanentCount(playerA, "Goblin Token", 2);
    }

    @Test
    public void test_TwoThumb() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, runner);
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Box"); // prevent legend rule as Thumb are legendary
        addCard(Zone.BATTLEFIELD, playerA, thumb, 2);
        addCustomCardWithAbility("Test Trigger", playerA, new KrarksThumbTestTriggeredAbility());

        // First flip, + 2 rerolls -- result success
        setFlipCoinResult(playerA, true);
        setChoice(playerA, "Krark's Thumb"); // order replacement effects
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, false);

        // Second flip, + 2 rerolls -- result fail
        setFlipCoinResult(playerA, false);
        setChoice(playerA, "Krark's Thumb"); // order replacement effects
        setFlipCoinResult(playerA, false);
        setFlipCoinResult(playerA, false);

        // Third flip, + 2 rerolls -- result success
        setFlipCoinResult(playerA, true);
        setChoice(playerA, "Krark's Thumb"); // order replacement effects
        setFlipCoinResult(playerA, true);
        setFlipCoinResult(playerA, false);

        attack(1, playerA, runner, playerB);
        setChoice(playerA, "Whenever you flip a coin, you gain 3 life", 2); // stack triggers

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20 + 3 * 3);
        assertPermanentCount(playerA, "Goblin Token", 2);
    }
}
