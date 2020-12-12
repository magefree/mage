
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class TapAttachedCost extends CostImpl {

    public TapAttachedCost() {
        this.text = "Tap enchanted creature";
    }

    public TapAttachedCost(TapAttachedCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        if (permanent != null) {
            paid = permanent.tap(source, game);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        if (permanent != null) {
            // return true;          // Technically the more correct implementation, but all cards using this cost also come with an "untapped" condition
            return !permanent.isTapped();
        }
        return false;
    }

    @Override
    public TapAttachedCost copy() {
        return new TapAttachedCost(this);
    }
}
