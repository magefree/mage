package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.BeholdCost;
import mage.constants.BeholdType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class BeholdAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String promptString = "Behold ";
    private static final String keywordText = "As an additional cost to cast this spell, you may behold ";
    private static final String reminderText = "Choose $$$ you control or reveal $$$ card from your hand.";
    private final String rule;

    public static final String BEHOLD_ACTIVATION_VALUE_KEY = "beholdActivation";

    protected OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost(BeholdType beholdType) {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                keywordText + beholdType.getDescription(),
                reminderText.replace("$$$", beholdType.getDescription()),
                new BeholdCost(beholdType, 1)
        );
        cost.setRepeatable(false);
        return cost;
    }

    private final BeholdType beholdType;

    public BeholdAbility(SubType subType) {
        super(Zone.STACK, null);
        this.beholdType = BeholdType.getBeholdType(subType);
        this.additionalCost = makeCost(beholdType);
        this.rule = additionalCost.getName() + ". " + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
    }

    private BeholdAbility(final BeholdAbility ability) {
        super(ability);
        this.beholdType = ability.beholdType;
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public BeholdAbility copy() {
        return new BeholdAbility(this);
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
        if (!canPay || !player.chooseUse(Outcome.Exile, promptString + beholdType.getDescription() + '?', ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(BEHOLD_ACTIVATION_VALUE_KEY, null);
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
