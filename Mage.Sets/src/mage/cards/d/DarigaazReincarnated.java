package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarigaazReincarnated extends CardImpl {

    public DarigaazReincarnated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If Darigaaz Reincarnated would die, instead exile it with three egg counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DarigaazReincarnatedDiesEffect()));

        // At the beginning of your upkeep, if Darigaaz is exiled with an egg counter on it, remove an egg counter from it. Then if Darigaaz has no egg counters on it, return it to the battlefield.
        this.addAbility(new DarigaazReincarnatedInterveningIfTriggeredAbility());
    }

    private DarigaazReincarnated(final DarigaazReincarnated card) {
        super(card);
    }

    @Override
    public DarigaazReincarnated copy() {
        return new DarigaazReincarnated(this);
    }
}

class DarigaazReincarnatedDiesEffect extends ReplacementEffectImpl {

    public DarigaazReincarnatedDiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would die, instead exile it with three egg counters on it";
    }

    public DarigaazReincarnatedDiesEffect(final DarigaazReincarnatedDiesEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedDiesEffect copy() {
        return new DarigaazReincarnatedDiesEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }

        return CardUtil.moveCardWithCounter(game, source, controller, permanent, Zone.EXILED, CounterType.EGG.createInstance(3));
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            return zce.isDiesEvent();
        }
        return false;
    }

}

class DarigaazReincarnatedInterveningIfTriggeredAbility extends ConditionalInterveningIfTriggeredAbility {

    public DarigaazReincarnatedInterveningIfTriggeredAbility() {
        super(new BeginningOfUpkeepTriggeredAbility(Zone.EXILED, new DarigaazReincarnatedReturnEffect(), TargetController.YOU, false),
                DarigaazReincarnatedCondition.instance,
                "At the beginning of your upkeep, if {this} is exiled with an egg counter on it, "
                        + "remove an egg counter from it. Then if {this} has no egg counters on it, return it to the battlefield");
    }

    public DarigaazReincarnatedInterveningIfTriggeredAbility(final DarigaazReincarnatedInterveningIfTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedInterveningIfTriggeredAbility copy() {
        return new DarigaazReincarnatedInterveningIfTriggeredAbility(this);
    }
}

class DarigaazReincarnatedReturnEffect extends OneShotEffect {

    DarigaazReincarnatedReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    DarigaazReincarnatedReturnEffect(final DarigaazReincarnatedReturnEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedReturnEffect copy() {
        return new DarigaazReincarnatedReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject instanceof Card) {
            Card card = (Card) sourceObject;
            new RemoveCounterSourceEffect(CounterType.EGG.createInstance()).apply(game, source);
            if (card.getCounters(game).getCount(CounterType.EGG) == 0) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}

enum DarigaazReincarnatedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            return game.getState().getZone(card.getId()) == Zone.EXILED
                    && card.getCounters(game).getCount(CounterType.EGG) > 0;
        }
        return false;
    }
}
