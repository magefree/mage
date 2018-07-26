package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class MassRecall extends CardImpl {

    public MassRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");
        

        // As an additional cost to cast Mass Recall, return X permanents you control to their owner's hands.
        this.getSpellAbility().addCost(new ReturnToHandXTargetCost());

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new ManacostVariableValue()));
    }

    public MassRecall(final MassRecall card) {
        super(card);
    }

    @Override
    public MassRecall copy() {
        return new MassRecall(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            Target target = new TargetControlledPermanent(xValue, xValue,
                    new FilterControlledPermanent(xValue + " target controlled permanent(s)"), false);
            ability.getTargets().clear();
            ability.getTargets().add(target);
        }
    }
}

class ReturnToHandXTargetCost extends CostImpl {

    public ReturnToHandXTargetCost() {
        this.text = "return X permanents you control to their owner's hands";
    }

    public ReturnToHandXTargetCost(ReturnToHandXTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if(controller == null) {
            return false;
        }

        for (Target target : ability.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                return false;
            }
            controller.moveCards(permanent, Zone.HAND, ability, game);
        }

        return true;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return ability.getTargets().canChoose(sourceId, controllerId, game);
    }

    @Override
    public ReturnToHandXTargetCost copy() {
        return new ReturnToHandXTargetCost(this);
    }
}
