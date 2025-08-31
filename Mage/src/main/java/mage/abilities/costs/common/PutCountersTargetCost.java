package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public class PutCountersTargetCost extends CostImpl {

    private final Counter counter;

    public PutCountersTargetCost(Counter counter){
        this(counter, new TargetControlledCreaturePermanent());
    }

    public PutCountersTargetCost(Counter counter, TargetControlledPermanent target) {
        this.counter = counter.copy();
        target.withNotTarget(true);
        this.addTarget(target);
        this.text = "put " + counter.getDescription() + " on " + target.getDescription();
    }

    public PutCountersTargetCost(PutCountersTargetCost cost) {
        super(cost);
        this.counter = cost.counter.copy();
    }

    public PutCountersTargetCost copy() {
        return new PutCountersTargetCost(this);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null || !this.getTargets().choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        for (UUID targetId : this.getTargets().get(0).getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            paid |= permanent.addCounters(counter, controllerId, ability, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return canChooseOrAlreadyChosen(ability, source, controllerId, game);
    }
}
