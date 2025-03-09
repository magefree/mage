package mage.cards.e;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.*;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

/**
 *
 * @author Jmlundeen
 */
public final class ElvishRefueler extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent();
    private static final Hint hint = new ConditionHint(ActivatedExhaustCondition.instance,
            "You haven't activated an exhaust ability this turn",
            null,
            "You have activated an exhaust ability this turn",
            null,
            true);

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ElvishRefueler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // During your turn, as long as you haven't activated an exhaust ability this turn, you may activate exhaust abilities as though they haven't been activated.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new ElvishRefuelerEffect(filter), ActivatedExhaustCondition.instance)
                .setText("During your turn, as long as you haven't activated an exhaust ability this turn, " +
                        "you may activate exhaust abilities as though they haven't been activated")
        ).addHint(hint), new ElvishRefuelerWatcher());

        // Exhaust -- {1}{G}: Put a +1/+1 counter on this creature.
        this.addAbility(new ExhaustAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{G}")));

    }

    private ElvishRefueler(final ElvishRefueler card) {
        super(card);
    }

    @Override
    public ElvishRefueler copy() {
        return new ElvishRefueler(this);
    }

}

enum ActivatedExhaustCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ElvishRefuelerWatcher watcher = game.getState().getWatcher(ElvishRefuelerWatcher.class);
        return watcher != null && !watcher.checkActivatedExhaust(source.getControllerId());
    }
}

class ElvishRefuelerWatcher extends Watcher {

    private final Map<UUID, Boolean> activatedExhaustAbilities = new HashMap<>();

    public ElvishRefuelerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null && stackObject.getStackAbility() instanceof ExhaustAbility) {
            activatedExhaustAbilities.put(event.getPlayerId(), true);
        }
    }

    @Override
    public void reset() {
        super.reset();
        activatedExhaustAbilities.clear();
    }

    public boolean checkActivatedExhaust(UUID playerId) {
        return activatedExhaustAbilities.getOrDefault(playerId, false);
    }
}

class ElvishRefuelerEffect extends AsThoughEffectImpl {
    private final FilterPermanent filter;

    ElvishRefuelerEffect(FilterPermanent filter) {
        super(AsThoughEffectType.ALLOW_EXHAUST_PER_TURN, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        staticText = "During your turn, as long as you haven't activated an exhaust ability this turn, " +
                "you may activate exhaust abilities as though they haven't been activated";
    }

    ElvishRefuelerEffect(final ElvishRefuelerEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ElvishRefuelerEffect copy() {
        return new ElvishRefuelerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        ElvishRefuelerWatcher watcher = game.getState().getWatcher(ElvishRefuelerWatcher.class);
        if (!game.isActivePlayer(affectedControllerId) || watcher == null || watcher.checkActivatedExhaust(affectedControllerId)) {
            return false;
        }
        Permanent permanent = game.getPermanent(objectId);
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
