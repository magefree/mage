package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EquipAbility extends ActivatedAbilityImpl {

    private String costReduceText = null;
    private final boolean showAbilityHint;

    public EquipAbility(int cost) {
        this(cost, true);
    }

    public EquipAbility(int cost, boolean showAbilityHint) {
        this(Outcome.AddAbility, new GenericManaCost(cost), showAbilityHint);
    }

    public EquipAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, true);
    }

    public EquipAbility(Outcome outcome, Cost cost, boolean showAbilityHint) {
        this(outcome, cost, new TargetControlledCreaturePermanent(), showAbilityHint);
    }

    public EquipAbility(Outcome outcome, Cost cost, Target target) {
        this(outcome, cost, target, true);
    }

    public EquipAbility(Outcome outcome, Cost cost, Target target, boolean showAbilityHint) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Equip"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
        this.showAbilityHint = showAbilityHint;
    }

    public EquipAbility(final EquipAbility ability) {
        super(ability);
        this.costReduceText = ability.costReduceText;
        this.showAbilityHint = ability.showAbilityHint;
    }

    public void setCostReduceText(String text) {
        this.costReduceText = text;
    }

    @Override
    public EquipAbility copy() {
        return new EquipAbility(this);
    }

    @Override
    public String getRule() {
        String targetText = getTargets().get(0) != null ? getTargets().get(0).getFilter().getMessage() : "creature";
        String reminderText = " <i>(" + manaCosts.getText() + ": Attach to target " + targetText + ". Equip only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the creature leaves.)</i>";

        StringBuilder sb = new StringBuilder("Equip");
        if (!targetText.equals("creature you control")) {
            sb.append(' ').append(targetText);
        }
        String costText = costs.getText();
        if (costText != null && !costText.isEmpty()) {
            sb.append("&mdash;").append(costText).append('.');
        } else {
            sb.append(' ');
        }
        sb.append(manaCosts.getText());
        if (costReduceText != null && !costReduceText.isEmpty()) {
            sb.append(". ");
            sb.append(costReduceText);
        }
        if (maxActivationsPerTurn == 1) {
            sb.append(". Activate only once each turn.");
        }
        if (showAbilityHint) {
            sb.append(reminderText);
        }
        return sb.toString();
    }
}
