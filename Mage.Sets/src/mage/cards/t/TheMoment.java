package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author padfoothelix 
 */
public final class TheMoment extends CardImpl {

    public TheMoment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, put a time counter on The Moment.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.TIME.createInstance())
        ));

        // {2}, {T}: Untap target creature you control. It phases out until The Moment leaves the battlefield.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TheMomentPhaseOutEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {3}, {T}: Destroy each nonland permanent with mana value less than or equal to the number of time counters on The Moment. Then sacrifice The Moment. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new TheMomentDestroyEffect(), new GenericManaCost(3));
        ability.addEffect(new SacrificeSourceEffect().concatBy("Then"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheMoment(final TheMoment card) {
        super(card);
    }

    @Override
    public TheMoment copy() {
        return new TheMoment(this);
    }
}

// Copied from Oubliette
class TheMomentPhaseOutEffect extends OneShotEffect {

    TheMomentPhaseOutEffect() {
        super(Outcome.Detriment);
        staticText = "It phases out until {this} leaves the battlefield";
    }

    private TheMomentPhaseOutEffect(final TheMomentPhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public TheMomentPhaseOutEffect copy() {
        return new TheMomentPhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || permanent == null) {
            return false;
        }

        MageObjectReference mor = new MageObjectReference(permanent, game);
        permanent.phaseOut(game);
        game.addEffect(new TheMomentPhasePreventEffect(mor), source);
        game.addDelayedTriggeredAbility(new TheMomentDelayedTriggeredAbility(mor), source);

        return true;
    }
}

class TheMomentPhasePreventEffect extends ContinuousRuleModifyingEffectImpl {

    private final MageObjectReference mor;

    TheMomentPhasePreventEffect(MageObjectReference mor) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.mor = mor;
    }

    private TheMomentPhasePreventEffect(final TheMomentPhasePreventEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public TheMomentPhasePreventEffect copy() {
        return new TheMomentPhasePreventEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourcePermanentIfItStillExists(game) != null
                && this.mor.refersTo(event.getTargetId(), game);
    }
}

class TheMomentDelayedTriggeredAbility extends DelayedTriggeredAbility {

    TheMomentDelayedTriggeredAbility(MageObjectReference mor) {
        super(new TheMomentPhaseInEffect(mor), Duration.Custom, true, false);
        this.usesStack = false;
    }

    private TheMomentDelayedTriggeredAbility(final TheMomentDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMomentDelayedTriggeredAbility copy() {
        return new TheMomentDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        return ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
    }
}

class TheMomentPhaseInEffect extends OneShotEffect {

    private final MageObjectReference mor;

    TheMomentPhaseInEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private TheMomentPhaseInEffect(final TheMomentPhaseInEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public TheMomentPhaseInEffect copy() {
        return new TheMomentPhaseInEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = mor.getPermanent(game);
        if (permanent == null) {
            return false;
        }
        return permanent.phaseIn(game);
    }
}

class TheMomentDestroyEffect extends OneShotEffect {

    TheMomentDestroyEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy each nonland permanent with mana value " +
                "less or equal to the number of time counters on {this}";
    }

    private TheMomentDestroyEffect(final TheMomentDestroyEffect effect) {
        super(effect);
    }

    @Override
    public TheMomentDestroyEffect copy() {
        return new TheMomentDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        int xValue = permanent.getCounters(game).getCount(CounterType.TIME);
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, xValue));
        return new DestroyAllEffect(filter).apply(game, source);
    }
}
