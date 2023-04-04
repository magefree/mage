package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author huangn
 */
public final class MeliraTheLivingCure extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public MeliraTheLivingCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If you would get one or more poison counters, instead you get one poison counter and you can't get additional poison counters this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraTheLivingCureEffect()), new MeliraTheLivingCureWatcher());

        // Exile Melira, the Living Cure: Choose another target creature or artifact. When it's put into a graveyard this turn, return that card to the battlefield under its owner's control.
        DelayedTriggeredAbility delayedAbility = new WhenTargetDiesDelayedTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return that card to the battlefield under its owner's control"),
                SetTargetPointer.CARD
        );
        delayedAbility.setTriggerPhrase("Choose another target creature or artifact. When it's put into a graveyard this turn, ");
        Ability ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(delayedAbility), new ExileSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MeliraTheLivingCure(final MeliraTheLivingCure card) {
        super(card);
    }

    @Override
    public MeliraTheLivingCure copy() {
        return new MeliraTheLivingCure(this);
    }
}

class MeliraTheLivingCureEffect extends ReplacementEffectImpl {
    MeliraTheLivingCureEffect(){
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage, false);
        staticText = "If you would get one or more poison counters, instead you get one poison counter and you can't get additional poison counters this turn.";
    }

    MeliraTheLivingCureEffect(final MeliraTheLivingCureEffect effect){ super(effect); }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (source.getControllerId() != event.getTargetId()) {
            return false;
        }

        MeliraTheLivingCureWatcher watcher = game.getState().getWatcher(MeliraTheLivingCureWatcher.class);
        if (watcher.checkPoisonCounterAdded(event.getTargetId())){
            return true;
        } else {
            event.setAmountForCounters(1, true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getData().equals(CounterType.POISON.getName()) && event.getTargetId().equals(source.getControllerId());
    }

    @Override
    public MeliraTheLivingCureEffect copy() {
        return new MeliraTheLivingCureEffect(this);
    }
}

class MeliraTheLivingCureWatcher extends Watcher {
    private final Map<UUID, Boolean> poisonCounterAddedMap = new HashMap<>();

    public MeliraTheLivingCureWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTERS_ADDED && event.getData().equals(CounterType.POISON.getName())){
            if (poisonCounterAddedMap.containsKey(event.getTargetId())) {
                poisonCounterAddedMap.replace(event.getTargetId(), true);
            } else {
                poisonCounterAddedMap.put(event.getTargetId(), true);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        poisonCounterAddedMap.clear();
    }

    public Boolean checkPoisonCounterAdded(UUID target){
        return poisonCounterAddedMap.computeIfAbsent(target, x -> false);
    }

}