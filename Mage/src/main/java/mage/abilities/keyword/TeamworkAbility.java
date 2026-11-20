package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.common.TeamworkCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.hint.common.TeamworkCostWasPaidHint;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public class TeamworkAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    public static final String TEAMWORK_ACTIVATION_VALUE_KEY = "teamworkActivation";
    public static final String TEAMWORK_CREATURES_KEY = "teamworkCreatures";

    private final DynamicValue amount;
    private final String rule;
    private final OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost(int amount) {
        return makeCost(StaticValue.get(amount));
    }

    public static OptionalAdditionalCost makeCost(DynamicValue amount) {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                "Teamwork " + getAmountText(amount),
                "As an additional cost to cast this spell, you may tap any number of creatures you control " +
                        "with total power " + getAmountText(amount) + " or more.",
                new TeamworkCost(amount)
        );
        cost.setRepeatable(false);
        return cost;
    }

    public static String getAmountText(DynamicValue amount) {
        return amount instanceof StaticValue ? Integer.toString(((StaticValue) amount).getValue()) : amount.getMessage();
    }

    public TeamworkAbility(int amount) {
        this(StaticValue.get(amount));
    }

    public TeamworkAbility(DynamicValue amount) {
        super(Zone.STACK, null);
        this.amount = amount;
        this.additionalCost = makeCost(amount);
        this.rule = additionalCost.getName() + ' ' + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addHint(TeamworkCostWasPaidHint.instance);
    }

    private TeamworkAbility(final TeamworkAbility ability) {
        super(ability);
        this.amount = ability.amount.copy();
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public TeamworkAbility copy() {
        return new TeamworkAbility(this);
    }

    public void resetCost() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }

        this.resetCost();
        if (!additionalCost.canPay(ability, this, ability.getControllerId(), game)
                || !player.chooseUse(Outcome.Tap, "Teamwork " + amount.calculate(game, ability, null) + '?', ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : (Costs<Cost>) additionalCost) {
            ability.addCost(cost.copy());
        }
        ability.setCostsTag(TEAMWORK_ACTIVATION_VALUE_KEY, null);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    @Override
    public String getRule() {
        return rule;
    }
}
