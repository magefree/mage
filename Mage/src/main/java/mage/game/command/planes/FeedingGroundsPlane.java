package mage.game.command.planes;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author spjspj
 */
public class FeedingGroundsPlane extends Plane {

    private static final FilterCard filter = new FilterCard("red spells");
    private static final FilterCard filter2 = new FilterCard("green spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public FeedingGroundsPlane() {
        this.setPlaneType(Planes.PLANE_FEEDING_GROUNDS);

        // Red spells cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new SpellsCostReductionAllEffect(filter, 1)));

        // Green spells cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new SpellsCostReductionAllEffect(filter2, 1)));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, put X +1/+1 counters on target creature, where X is that creature's mana value.
        Ability ability = new ChaosEnsuesTriggeredAbility(new FeedingGroundsPlaneEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FeedingGroundsPlane(final FeedingGroundsPlane plane) {
        super(plane);
    }

    @Override
    public FeedingGroundsPlane copy() {
        return new FeedingGroundsPlane(this);
    }
}

class FeedingGroundsPlaneEffect extends OneShotEffect {

    FeedingGroundsPlaneEffect() {
        super(Outcome.Benefit);
        staticText = "put X +1/+1 counters on target creature, where X is that creature's mana value";
    }

    private FeedingGroundsPlaneEffect(final FeedingGroundsPlaneEffect effect) {
        super(effect);
    }

    @Override
    public FeedingGroundsPlaneEffect copy() {
        return new FeedingGroundsPlaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && permanent.getManaValue() > 0
                && permanent.addCounters(CounterType.P1P1.createInstance(permanent.getManaValue()), source, game);
    }
}
