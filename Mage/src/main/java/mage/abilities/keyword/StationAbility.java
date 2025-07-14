package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author TheElk801
 */
public class StationAbility extends SimpleActivatedAbility {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public StationAbility() {
        super(Zone.BATTLEFIELD, new StationAbilityEffect(), new TapTargetCost(filter));
        this.timing = TimingRule.SORCERY;
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
        return "Station <i>(Tap another creature you control: Put charge counters equal to its power on {this}. Station only as a sorcery.)</i>";
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
        List<Permanent> creatures = (List<Permanent>) getValue("tappedPermanents");
        if (creatures == null) {
            return false;
        }
        int power = 0;
        for (Permanent creature : creatures) {
            GameEvent event = GameEvent.getEvent(
                    GameEvent.EventType.STATION_PERMANENT, creature.getId(),
                    source, source.getControllerId(), creature.getPower().getValue()
            );
            if (game.replaceEvent(event)) {
                continue;
            }
            power += event.getAmount();
        }
        return power > 0 && permanent.addCounters(CounterType.CHARGE.createInstance(power), source, game);
    }
}
