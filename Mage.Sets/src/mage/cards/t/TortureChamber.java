
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TortureChamber extends CardImpl {

    public TortureChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of your upkeep, put a pain counter on Torture Chamber.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new AddCountersSourceEffect(CounterType.PAIN.createInstance())));
        // At the beginning of your end step, Torture Chamber deals damage to you equal to the number of pain counters on it.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new TortureChamberEffect1(), false));
        // {1}, {tap}, Remove all pain counters from Torture Chamber: Torture Chamber deals damage to target creature equal to the number of pain counters removed this way.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TortureChamberEffect2(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TortureChamberCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public TortureChamber(final TortureChamber card) {
        super(card);
    }

    @Override
    public TortureChamber copy() {
        return new TortureChamber(this);
    }
}

class TortureChamberCost extends CostImpl {

    private int removedCounters;

    public TortureChamberCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all pain counters from {this}";
    }

    public TortureChamberCost(TortureChamberCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters(game).getCount(CounterType.PAIN);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.PAIN.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public TortureChamberCost copy() {
        return new TortureChamberCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
    }
}

class TortureChamberEffect1 extends OneShotEffect {

    public TortureChamberEffect1() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to you equal to the number of pain counters on it";
    }

    public TortureChamberEffect1(final TortureChamberEffect1 effect) {
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
            player.damage(painCounters, source.getSourceId(), game, false, true);
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

    public TortureChamberEffect2(final TortureChamberEffect2 effect) {
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
            if (cost instanceof TortureChamberCost) {
                countersRemoved = ((TortureChamberCost) cost).getRemovedCounters();
            }
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(countersRemoved, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
