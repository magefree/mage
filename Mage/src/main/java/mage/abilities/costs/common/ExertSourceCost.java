
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class ExertSourceCost extends CostImpl {

    public ExertSourceCost() {
        this.text = "Exert {this}";
    }

    public ExertSourceCost(ExertSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_EXERTED, permanent.getId(), source, source.getControllerId()));
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("", permanent.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, ability);
            paid = true;
        }
        return paid;
    }

    @Override
    public ExertSourceCost copy() {
        return new ExertSourceCost(this);
    }
}
