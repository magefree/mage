package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Grath
 */
public final class PriorityBoarding extends CardImpl {

    public PriorityBoarding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you roll a die, you may reveal the top card of your library. Do this only once each turn. Whenever you reveal a card with mana value less than the result this way, you may exile it. If you do, you may play it this turn.
        this.addAbility(new PriorityBoardingTriggeredAbility());
    }

    private PriorityBoarding(final PriorityBoarding card) {
        super(card);
    }

    @Override
    public PriorityBoarding copy() {
        return new PriorityBoarding(this);
    }
}

class PriorityBoardingEffect extends OneShotEffect {

    PriorityBoardingEffect() {
        super(Outcome.Benefit);
    }

    private PriorityBoardingEffect(final PriorityBoardingEffect effect) {
        super(effect);
    }

    @Override
    public PriorityBoardingEffect copy() {
        return new PriorityBoardingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (getValue("rolled") == null) {
            return true;
        }
        int amount = (Integer) getValue("rolled");
        if (amount > card.getManaValue()) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                            .withTextOptions("it", true), true
            );
            game.fireReflexiveTriggeredAbility(ability, source);
            return true;
        }
        return true;
    }
}

class PriorityBoardingTriggeredAbility extends TriggeredAbilityImpl {

    PriorityBoardingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PriorityBoardingEffect(), true);
        this.setDoOnlyOnceEachTurn(true);
    }

    private PriorityBoardingTriggeredAbility(final PriorityBoardingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        if (this.isControlledBy(event.getTargetId()) && drEvent.getRollDieType() == RollDieType.NUMERICAL) {
            int result = drEvent.getResult();
            this.getEffects().setValue("rolled", result);
            return true;
        }
        return false;
    }

    @Override
    public PriorityBoardingTriggeredAbility copy() {
        return new PriorityBoardingTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you roll a die, you may reveal the top card of your library. Do this only once each turn. " +
                "Whenever you reveal a card with mana value less than the result this way, you may exile it. If you " +
                "do, you may play it this turn.";
    }
}
