package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.BlightCost;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class BlightAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    public static final String BLIGHT_ACTIVATION_VALUE_KEY = "blightActivation";

    protected OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost(int amount) {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                "As an additional cost to cast this spell, you may blight " + amount,
                "You may put " + CardUtil.numberToText(amount, "a") +
                        " -1/-1 counter" + (amount > 1 ? "s" : "") + "on a creature you control.",
                new BlightCost(amount)
        );
        cost.setRepeatable(false);
        return cost;
    }

    private final int amount;
    private final String rule;

    public BlightAbility(int amount) {
        super(Zone.STACK, null);
        this.amount = amount;
        this.additionalCost = makeCost(amount);
        this.rule = additionalCost.getName() + ". " + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
    }

    private BlightAbility(final BlightAbility ability) {
        super(ability);
        this.amount = ability.amount;
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public BlightAbility copy() {
        return new BlightAbility(this);
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
        boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
        if (!canPay || !player.chooseUse(Outcome.Exile, "Blight " + amount + '?', ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(BLIGHT_ACTIVATION_VALUE_KEY, null);
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
