package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ArchmageAscension extends CardImpl {

    public ArchmageAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on Archmage Ascension.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.QUEST.createInstance(1)),
                        TargetController.EACH_PLAYER, true
                ), ArchmageAscensionCondition.instance, "At the beginning of each end step, " +
                "if you drew two or more cards this turn, you may put a quest counter on {this}."
        ), new CardsAmountDrawnThisTurnWatcher());

        // As long as Archmage Ascension has six or more quest counters on it, if you would draw a card,
        // you may instead search your library for a card, put that card into your hand, then shuffle your library.
        this.addAbility(new SimpleStaticAbility(new ArchmageAscensionReplacementEffect()));
    }

    private ArchmageAscension(final ArchmageAscension card) {
        super(card);
    }

    @Override
    public ArchmageAscension copy() {
        return new ArchmageAscension(this);
    }
}

enum ArchmageAscensionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(source.getControllerId()) >= 2;
    }
}

class ArchmageAscensionReplacementEffect extends ReplacementEffectImpl {

    ArchmageAscensionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} has six or more quest counters on it, if you would draw a card, "
                + "you may instead search your library for a card, put that card into your hand, then shuffle";
    }

    private ArchmageAscensionReplacementEffect(final ArchmageAscensionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ArchmageAscensionReplacementEffect copy() {
        return new ArchmageAscensionReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent archmage = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        return event.getPlayerId().equals(source.getControllerId())
                && archmage != null
                && archmage.getCounters(game).getCount(CounterType.QUEST) >= 6
                && you != null
                && you.chooseUse(Outcome.Benefit, "Search your library instead of drawing a card?", source, game);
    }
}
