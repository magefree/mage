package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author MTGfan
 */
public final class CyclopeanTomb extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.SWAMP)));
    }

    public CyclopeanTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}: Put a mire counter on target non-Swamp land. That land is a Swamp for as long as it has a mire counter on it. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.MIRE.createInstance()), new GenericManaCost(2), new IsStepCondition(PhaseStep.UPKEEP), "{2}, {T}: Put a mire counter on target non-Swamp land. That land is a Swamp for as long as it has a mire counter on it. Activate this ability only during your upkeep.");
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent(filter));
        ability.addEffect(new BecomeSwampEffect(Duration.Custom, false, true, SubType.SWAMP));
        this.addAbility(ability, new CyclopeanTombCounterWatcher());

        // When Cyclopean Tomb is put into a graveyard from the battlefield, at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with Cyclopean Tomb but that a mire counter has not been removed from with Cyclopean Tomb.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new CyclopeanTombCreateTriggeredEffect()));
    }

    public CyclopeanTomb(final CyclopeanTomb card) {
        super(card);
    }

    @Override
    public CyclopeanTomb copy() {
        return new CyclopeanTomb(this);
    }
}

class BecomeSwampEffect extends BecomesBasicLandTargetEffect {

    public BecomeSwampEffect(Duration duration, boolean chooseLandType, boolean loseOther, SubType... landNames) {
        super(duration, chooseLandType, loseOther, landNames);
        staticText = "That land is a Swamp for as long as it has a mire counter on it";
    }

    public BecomeSwampEffect(final BecomeSwampEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land == null) {
            // if permanent left battlefield the effect can be removed because it was only valid for that object
            this.discard();
        } else if (land.getCounters(game).getCount(CounterType.MIRE) > 0) {
            // only if Mire counter is on the object it becomes a Swamp.
            super.apply(layer, sublayer, source, game);
        }
        return true;
    }

    @Override
    public BecomeSwampEffect copy() {
        return new BecomeSwampEffect(this);
    }
}

class CyclopeanTombCreateTriggeredEffect extends OneShotEffect {

    public CyclopeanTombCreateTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with {this} but that a mire counter has not been removed from with {this}";
    }

    public CyclopeanTombCreateTriggeredEffect(final CyclopeanTombCreateTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public CyclopeanTombCreateTriggeredEffect copy() {
        return new CyclopeanTombCreateTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent tomb = game.getPermanentOrLKIBattlefield(source.getSourceId()); // we need to set the correct source object
            DelayedTriggeredAbility ability = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new CyclopeanTombEffect(), Duration.EndOfGame, false);
            ability.setControllerId(source.getControllerId());
            ability.setSourceId(source.getSourceId());
            game.addDelayedTriggeredAbility(ability);
            return true;
        }
        return false;
    }
}

class CyclopeanTombEffect extends OneShotEffect {

    public CyclopeanTombEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with {this} but that a mire counter has not been removed from with {this}";
    }

    public CyclopeanTombEffect(final CyclopeanTombEffect effect) {
        super(effect);
    }

    @Override
    public CyclopeanTombEffect copy() {
        return new CyclopeanTombEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObjectReference mor = new MageObjectReference(source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game);
        CyclopeanTombCounterWatcher watcher = (CyclopeanTombCounterWatcher) game.getState().getWatchers().get(CyclopeanTombCounterWatcher.class.getSimpleName());
        if (controller != null && watcher != null) {

            Set<MageObjectReference> landRef = watcher.landMiredByCyclopeanTombInstance(mor, game);
            if (landRef == null || landRef.isEmpty()) { // no lands got mire counter from that instance
                return true;
            }
            FilterLandPermanent filter = new FilterLandPermanent("a land with a mire counter added from the Cyclopean Tomb instance (" + landRef.size() + " left)");
            Set<PermanentIdPredicate> idPref = new HashSet<>();
            for (MageObjectReference ref : landRef) {
                Permanent land = ref.getPermanent(game);
                if (land != null) {
                    idPref.add(new PermanentIdPredicate(land.getId()));
                }
            }
            filter.add(Predicates.or(idPref));
            TargetLandPermanent target = new TargetLandPermanent(1, 1, filter, true);
            /*Player must choose a land each upkeep. Using the message are above the player hand where frequent interactions
             * take place is the most logical way to prompt for this scenario. A new constructor added to provide a not optional
             * option for any cards like this where the player must choose a target in such the way this card requires.
             */
            if (controller.chooseTarget(Outcome.Neutral, target, source, game)) {
                Permanent chosenLand = game.getPermanent(target.getFirstTarget());
                if (chosenLand != null) {
                    Effect effect = new RemoveAllCountersTargetEffect(CounterType.MIRE);
                    effect.setTargetPointer(new FixedTarget(chosenLand, game));
                    effect.apply(game, source);
                    landRef.remove(new MageObjectReference(chosenLand, game));
                }
            }

            return true;
        }
        return false;
    }
}

class CyclopeanTombCounterWatcher extends Watcher {

    public HashMap<MageObjectReference, Set<MageObjectReference>> counterData = new HashMap<>();

    public CyclopeanTombCounterWatcher() {
        super(CyclopeanTombCounterWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CyclopeanTombCounterWatcher(final CyclopeanTombCounterWatcher watcher) {
        super(watcher);
        for (MageObjectReference mageObjectReference : watcher.counterData.keySet()) {
            Set<MageObjectReference> miredLands = new HashSet<>();
            miredLands.addAll(watcher.counterData.get(mageObjectReference));
            counterData.put(mageObjectReference, miredLands);
        }
    }

    @Override
    public CyclopeanTombCounterWatcher copy() {
        return new CyclopeanTombCounterWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTERS_ADDED && event.getData().equals(CounterType.MIRE.getName()) && event.getAmount() > 0) {
            Permanent tomb = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (tomb != null) {
                MageObjectReference cylopeanTombInstance = new MageObjectReference(tomb, game);
                Set<MageObjectReference> miredLands;
                if (counterData.containsKey(cylopeanTombInstance)) {
                    miredLands = counterData.get(cylopeanTombInstance);
                } else {
                    miredLands = new HashSet<>();
                    counterData.put(cylopeanTombInstance, miredLands);
                }
                miredLands.add(new MageObjectReference(event.getTargetId(), game));
            }

        }
    }

    @Override
    public void reset() {
        super.reset();
    }

    public Set<MageObjectReference> landMiredByCyclopeanTombInstance(MageObjectReference mor, Game game) {
        if (counterData.containsKey(mor)) {
            return counterData.get(mor);
        }
        return null;
    }
}
