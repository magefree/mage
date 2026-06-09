package mage.abilities.costs.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.TeamworkAbility;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.awt.Color;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public class TeamworkCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private final DynamicValue amount;

    public TeamworkCost(int amount) {
        this(StaticValue.get(amount));
    }

    public TeamworkCost(DynamicValue amount) {
        this.amount = amount;
        this.text = "tap any number of creatures you control with total power " +
                TeamworkAbility.getAmountText(amount) + " or more";
    }

    private TeamworkCost(final TeamworkCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        int amount = this.amount.calculate(game, ability, null);
        if (amount <= 0) {
            ability.setCostsTag(TeamworkAbility.TEAMWORK_CREATURES_KEY, new HashSet<MageObjectReference>());
            paid = true;
            return true;
        }
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true) {
            @Override
            public String getMessage(Game game) {
                int selectedPower = this.targets.keySet().stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .mapToInt(permanent -> permanent.getPower().getValue())
                        .sum();
                return super.getMessage(game) + ' ' + HintUtils.prepareText(
                        "(selected power " + selectedPower + " of " + amount + ")",
                        selectedPower >= amount ? Color.GREEN : Color.RED
                );
            }
        };

        if (!target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            return false;
        }

        int sumPower = 0;
        HashSet<MageObjectReference> teamworkCreatures = new HashSet<>();
        Set<UUID> paidCreatureIds = new HashSet<>();
        for (UUID targetId : target.getTargets()) {
            GameEvent event = new GameEvent(GameEvent.EventType.PAY_TEAMWORK_COST, targetId, source, controllerId);
            if (game.replaceEvent(event)) {
                continue;
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.tap(source, game)) {
                sumPower += permanent.getPower().getValue();
                teamworkCreatures.add(new MageObjectReference(permanent, game));
                paidCreatureIds.add(targetId);
            }
        }

        paid = sumPower >= amount;
        if (paid) {
            ability.setCostsTag(TeamworkAbility.TEAMWORK_CREATURES_KEY, teamworkCreatures);
            for (UUID targetId : paidCreatureIds) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PAID_TEAMWORK_COST, targetId, source, controllerId));
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int amount = this.amount.calculate(game, ability, null);
        if (amount <= 0) {
            return true;
        }
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            sumPower += Math.max(permanent.getPower().getValue(), 0);
            if (sumPower >= amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TeamworkCost copy() {
        return new TeamworkCost(this);
    }
}
