package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.costs.*;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.hint.ConditionTrueHint;
import mage.abilities.hint.Hint;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CollectEvidenceAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String promptString = "Collect evidence ";
    private static final String keywordText = "As an additional cost to cast this spell, you may collect evidence ";
    private static final String reminderText = "Exile cards with total mana value $$$ or greater from your graveyard";
    private final String rule;
    private final int amount;

    public static final String COLLECT_EVIDENCE_ACTIVATION_VALUE_KEY = "collectEvidenceActivation";

    protected OptionalAdditionalCost additionalCost;

    private static final Hint hint = new ConditionTrueHint(CollectedEvidenceCondition.instance, "evidence was collected");

    public static OptionalAdditionalCost makeCost(int amount) {
        OptionalAdditionalCost cost = new OptionalAdditionalCostImpl(
                keywordText + amount,
                reminderText.replace("$$$", "" + amount),
                new CollectEvidenceCost(amount)
        );
        cost.setRepeatable(false);
        return cost;
    }

    public CollectEvidenceAbility(int amount) {
        super(Zone.STACK, null);
        this.additionalCost = makeCost(amount);
        this.rule = additionalCost.getName() + ". " + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addHint(hint);
        this.amount = amount;
    }

    private CollectEvidenceAbility(final CollectEvidenceAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
        this.amount = ability.amount;
    }

    @Override
    public CollectEvidenceAbility copy() {
        return new CollectEvidenceAbility(this);
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
        if (!canPay || !player.chooseUse(Outcome.Exile, promptString + amount + '?', ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(COLLECT_EVIDENCE_ACTIVATION_VALUE_KEY, null);
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
