package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class CantHaveCountersAddedAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    private final CounterType counterType;

    public CantHaveCountersAddedAllEffect(FilterPermanent filter, CounterType counterType) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        this.filter = filter;
        this.counterType = counterType;
        staticText = filter.getMessage() + " can't have " +
                (counterType != null ? counterType.getName() + ' ' : "") +
                "counters put on them";
    }

    private CantHaveCountersAddedAllEffect(final CantHaveCountersAddedAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.counterType = effect.counterType;
    }

    @Override
    public CantHaveCountersAddedAllEffect copy() {
        return new CantHaveCountersAddedAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getPermanentsEntering().values()) {
            if (!filter.match(permanent, source.getControllerId(), source, game)) {
                continue;
            }
            if (counterType == null) {
                permanent.setCountersCanBeAdded(false);
            } else {
                permanent.setCounterTypeCantBeAdded(counterType);
            }
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (counterType == null) {
                permanent.setCountersCanBeAdded(false);
            } else {
                permanent.setCounterTypeCantBeAdded(counterType);
            }
        }
        return true;
    }
}
