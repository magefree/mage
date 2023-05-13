package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferisAgelessInsight extends CardImpl {

    public TeferisAgelessInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new TeferisAgelessInsightEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    private TeferisAgelessInsight(final TeferisAgelessInsight card) {
        super(card);
    }

    @Override
    public TeferisAgelessInsight copy() {
        return new TeferisAgelessInsight(this);
    }
}

class TeferisAgelessInsightEffect extends ReplacementEffectImpl {

    TeferisAgelessInsightEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead";
    }

    private TeferisAgelessInsightEffect(final TeferisAgelessInsightEffect effect) {
        super(effect);
    }

    @Override
    public TeferisAgelessInsightEffect copy() {
        return new TeferisAgelessInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        if (!game.isActivePlayer(event.getPlayerId())
                || game.getPhase().getStep().getType() != PhaseStep.DRAW) {
            return true;
        }
        CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0;
    }
}
