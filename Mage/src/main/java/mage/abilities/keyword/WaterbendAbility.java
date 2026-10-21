package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.WaterbendCost;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class WaterbendAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String promptString = "Waterbend {";
    private static final String keywordText = "As an additional cost to cast this spell, you may waterbend {";
    private static final String reminderText = "While paying a waterbend cost, you can tap your artifacts and creatures to help. Each one pays for {1}.";
    private final String rule;
    private final int amount;

    public static final String WATERBEND_ACTIVATION_VALUE_KEY = "waterbendActivation";

    protected OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost(int amount) {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                keywordText + amount + '}', reminderText, new WaterbendCost(amount)
        );
        cost.setRepeatable(false);
        return cost;
    }

    public WaterbendAbility(int amount) {
        this(amount, null);
    }

    public WaterbendAbility(int amount, String extraInfoText) {
        super(Zone.STACK, null);
        this.additionalCost = makeCost(amount);
        this.rule = additionalCost.getName() + ". " + (extraInfoText == null ? "" : extraInfoText + ". ") + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.amount = amount;
    }

    private WaterbendAbility(final WaterbendAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
        this.amount = ability.amount;
    }

    @Override
    public WaterbendAbility copy() {
        return new WaterbendAbility(this);
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
        if (!canPay || !player.chooseUse(Outcome.Exile, promptString + amount + "}?", ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.addCost(cost.copy());
        }
        ability.setCostsTag(WATERBEND_ACTIVATION_VALUE_KEY, null);
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
