package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.BeholdDragonCost;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.hint.common.EvidenceHint;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class BeholdDragonAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String promptString = "Behold a Dragon";
    private static final String keywordText = "As an additional cost to cast this spell, you may behold a Dragon";
    private static final String reminderText = "Choose a Dragon you control or reveal a Dragon card from your hand.";
    private final String rule;

    public static final String BEHOLD_DRAGON_ACTIVATION_VALUE_KEY = "beholdDragonActivation";

    protected OptionalAdditionalCost additionalCost;

    public static OptionalAdditionalCost makeCost() {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(keywordText , reminderText, new BeholdDragonCost());
        cost.setRepeatable(false);
        return cost;
    }
    public BeholdDragonAbility( ) {
        super(Zone.STACK, null);
        this.additionalCost = makeCost();
        this.rule = additionalCost.getName() + ". "  + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
    }

    private BeholdDragonAbility(final BeholdDragonAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public BeholdDragonAbility copy() {
        return new BeholdDragonAbility(this);
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
        if (!canPay || !player.chooseUse(Outcome.Exile, promptString +  '?', ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(BEHOLD_DRAGON_ACTIVATION_VALUE_KEY, null);
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
