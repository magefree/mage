package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorgalAFineHound extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("your first Human creature spell each turn");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public TorgalAFineHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your first Human creature spell each turn, that creature enters with an additional +1/+1 counter on it for each Dog and/or Wolf you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(new TorgalAFineHoundEffect(), filter, false)
                .withTriggerCondition(TorgalAFineHoundCondition.instance)
                .addHint(TorgalAFineHoundEffect.getHint()), new TorgalAFineHoundWatcher());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private TorgalAFineHound(final TorgalAFineHound card) {
        super(card);
    }

    @Override
    public TorgalAFineHound copy() {
        return new TorgalAFineHound(this);
    }
}

enum TorgalAFineHoundCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return TorgalAFineHoundWatcher.check(game, source);
    }

    @Override
    public String toString() {
        return "";
    }
}

class TorgalAFineHoundEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                SubType.DOG.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Dogs and Wolves you control", xValue);

    public static Hint getHint() {
        return hint;
    }

    TorgalAFineHoundEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        staticText = "that creature enters with an additional +1/+1 counter on it for each Dog and/or Wolf you control";
    }

    private TorgalAFineHoundEffect(final TorgalAFineHoundEffect effect) {
        super(effect);
    }

    @Override
    public TorgalAFineHoundEffect copy() {
        return new TorgalAFineHoundEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && event.getTargetId().equals(spell.getCard().getId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        int count = xValue.calculate(game, source, this);
        if (creature == null || count < 1) {
            return false;
        }
        creature.addCounters(
                CounterType.P1P1.createInstance(count), source.getControllerId(),
                source, game, event.getAppliedEffects()
        );
        discard();
        return false;
    }
}

class TorgalAFineHoundWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    TorgalAFineHoundWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Optional.ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getSpell)
                .filter(spell -> spell.isCreature(game) && spell.hasSubtype(SubType.HUMAN, game))
                .map(Spell::getControllerId)
                .ifPresent(playerId -> map.compute(playerId, CardUtil::setOrIncrementValue));
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean check(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(TorgalAFineHoundWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0) < 2;
    }
}
