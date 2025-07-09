package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Optional;

/**
 * @author TheElk801
 */
public class StationAbility extends SimpleActivatedAbility {

    public StationAbility() {
        super(Zone.BATTLEFIELD, new StationAbilityEffect(), new TapTargetCost(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE));
    }

    private StationAbility(final StationAbility ability) {
        super(ability);
    }

    @Override
    public StationAbility copy() {
        return new StationAbility(this);
    }

    @Override
    public String getRule() {
        return "station <i>(Tap another creature you control: Put charge counters equal to its power on {this}. Station only as a sorcery.)</i>";
    }
}

class StationAbilityEffect extends OneShotEffect {

    StationAbilityEffect() {
        super(Outcome.Benefit);
    }

    private StationAbilityEffect(final StationAbilityEffect effect) {
        super(effect);
    }

    @Override
    public StationAbilityEffect copy() {
        return new StationAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int power = Optional
                .ofNullable((List<Permanent>) getValue("tappedPermanents"))
                .map(permanents -> permanents
                        .stream()
                        .map(MageObject::getPower)
                        .mapToInt(MageInt::getValue)
                        .sum())
                .orElse(0);
        return power > 0 && permanent.addCounters(CounterType.CHARGE.createInstance(power), source, game);
    }
}
