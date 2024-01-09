package mage.game.command.planes;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author spjspj
 */
public class BantPlane extends Plane {

    private static final FilterPermanent filter = new FilterCreaturePermanent("green, white, or blue creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)
        ));
    }

    public BantPlane() {
        this.setPlaneType(Planes.PLANE_BANT);

        // All creatures have exalted
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND,
                new GainAbilityAllEffect(
                        new ExaltedAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
                )
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, put a divinity counter on target green, white, or blue creature.  That creature gains indestructible for as long as it has a divinity counter on it.
        Ability ability = new ChaosEnsuesTriggeredAbility(new AddCountersTargetEffect(CounterType.DIVINITY.createInstance()));
        ability.addEffect(new BantPlaneEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BantPlane(final BantPlane plane) {
        super(plane);
    }

    @Override
    public BantPlane copy() {
        return new BantPlane(this);
    }
}

class BantPlaneEffect extends ContinuousEffectImpl {

    BantPlaneEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "that creature gains indestructible for as long as it has a divinity counter on it";
    }

    private BantPlaneEffect(final BantPlaneEffect effect) {
        super(effect);
    }

    @Override
    public BantPlaneEffect copy() {
        return new BantPlaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.getCounters(game).containsKey(CounterType.DIVINITY)) {
            discard();
            return false;
        }
        permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
        return true;
    }
}
