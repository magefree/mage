package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class UntapTargetCost extends CostImpl {

    private final TargetPermanent target;

    public UntapTargetCost(TargetPermanent target) {
        this.target = target;
        this.text = makeText(target);

        // It will never target as part of a cost
        this.target.setNotTarget(true);
    }

    public UntapTargetCost(final UntapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!target.choose(Outcome.Untap, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        List<UUID> untapped = new ArrayList<>();
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }

            if (permanent.untap(game)) {
                untapped.add(targetId);
            }

        }

        game.getState().setValue("UntapTargetCost" + ability.getSourceId().toString(), untapped); // remember the untapped permanent

        paid |= untapped.size() >= target.getMinNumberOfTargets();
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    @Override
    public UntapTargetCost copy() {
        return new UntapTargetCost(this);
    }

    private static String makeText(TargetPermanent target) {
        StringBuilder sb = new StringBuilder("untap ");
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
            sb.append(' ');
            sb.append(target.getTargetName().replace(" you control", "s you control"));
        } else {
            sb.append(CardUtil.addArticle(target.getTargetName()));
        }
        return sb.toString();
    }
}
