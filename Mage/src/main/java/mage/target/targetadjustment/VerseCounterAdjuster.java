package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public enum VerseCounterAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (sourcePermanent != null) {
            int xValue = sourcePermanent.getCounters(game).getCount(CounterType.VERSE);
            FilterPermanent permanentFilter = ((TargetPermanent) ability.getTargets().get(0)).getFilter();
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(0, xValue, permanentFilter, false));
        }
    }
}
