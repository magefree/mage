package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class TortureChamber extends CardImpl {

    public TortureChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your upkeep, put a pain counter on Torture Chamber.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PAIN.createInstance()), TargetController.YOU, false
        ));

        // At the beginning of your end step, Torture Chamber deals damage to you equal to the number of pain counters on it.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new TortureChamberEffect1(), false));

        // {1}, {tap}, Remove all pain counters from Torture Chamber: Torture Chamber deals damage to target creature equal to the number of pain counters removed this way.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new TortureChamberEffect2(), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.PAIN));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TortureChamber(final TortureChamber card) {
        super(card);
    }

    @Override
    public TortureChamber copy() {
        return new TortureChamber(this);
    }
}

class TortureChamberEffect1 extends OneShotEffect {

    public TortureChamberEffect1() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to you equal to the number of pain counters on it";
    }

    private TortureChamberEffect1(final TortureChamberEffect1 effect) {
        super(effect);
    }

    @Override
    public TortureChamberEffect1 copy() {
        return new TortureChamberEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            int painCounters = permanent.getCounters(game).getCount(CounterType.PAIN);
            player.damage(painCounters, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}

class TortureChamberEffect2 extends OneShotEffect {

    public TortureChamberEffect2() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to target creature equal to the number of pain counters removed this way";
    }

    private TortureChamberEffect2(final TortureChamberEffect2 effect) {
        super(effect);
    }

    @Override
    public TortureChamberEffect2 copy() {
        return new TortureChamberEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(countersRemoved, source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }
}
