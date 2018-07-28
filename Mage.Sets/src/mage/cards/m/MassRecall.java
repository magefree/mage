package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class MassRecall extends CardImpl {

    public MassRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");
        

        // As an additional cost to cast Mass Recall, return X permanents you control to their owner's hands.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new MassRecallRuleEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

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
            if(xValue > 0) {
                ability.addCost(new ReturnToHandFromBattlefieldTargetCost(
                        new TargetControlledPermanent(xValue, xValue,
                                new FilterControlledPermanent(xValue + " target controlled permanent(s)"), false)));
            }
        }
    }
}

class MassRecallRuleEffect extends OneShotEffect {

    public MassRecallRuleEffect() {
        super(Outcome.Benefit);
        this.staticText = "As an additional cost to cast {this}, return X permanents you control to their owner's hands";
    }

    public MassRecallRuleEffect(final MassRecallRuleEffect effect) {
        super(effect);
    }

    @Override
    public MassRecallRuleEffect copy() {
        return new MassRecallRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

// From DiscardTargetCost and ReturnToHandFromBattlefieldSourceCost
class ReturnToHandFromBattlefieldTargetCost extends CostImpl {

    List<Permanent> permanents = new ArrayList<>();

    public ReturnToHandFromBattlefieldTargetCost(TargetPermanent target) {
        this.addTarget(target);
        this.text = "return " + target.getTargetName() + " to its owner's hand";
    }

    public ReturnToHandFromBattlefieldTargetCost(ReturnToHandFromBattlefieldTargetCost cost) {
        super(cost);
        this.permanents.addAll(cost.permanents);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.permanents.clear();
        this.targets.clearChosen();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        int amount = this.getTargets().get(0).getNumberOfTargets();
        if (targets.choose(Outcome.ReturnToHand, controllerId, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                controller.moveCards(permanent, Zone.HAND, ability, game);
                this.permanents.add(permanent);
            }
        }
        paid = (permanents.size() >= amount);
        return paid;
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        this.permanents.clear();
        this.targets.clearChosen();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(sourceId, controllerId, game);
    }

    @Override
    public ReturnToHandFromBattlefieldTargetCost copy() {
        return new ReturnToHandFromBattlefieldTargetCost(this);
    }
}