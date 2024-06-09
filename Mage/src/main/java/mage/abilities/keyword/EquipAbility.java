package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;

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

        // verify check: only controlled target allowed
        // 702.6c
        // Equip abilities may further restrict what creatures may be chosen as legal targets.
        // Such restrictions usually appear in the form “Equip [quality]” or “Equip [quality] creature.”
        // These equip abilities may legally target only a creature that’s controlled by the player
        // activating the ability and that has the chosen quality. Additional restrictions for an equip
        // ability don’t restrict what the Equipment may be attached to.
        List<Predicate> list = new ArrayList<>();
        Predicates.collectAllComponents(target.getFilter().getPredicates(), target.getFilter().getExtraPredicates(), list);
        if (list.stream()
                .filter(p -> p instanceof TargetController.ControllerPredicate)
                .map(p -> (TargetController.ControllerPredicate) p)
                .noneMatch(p -> p.getController().equals(TargetController.YOU))) {
            throw new IllegalArgumentException("Wrong code usage: equip ability must use target/filter with controller predicate - " + target);
        }

        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
        this.showAbilityHint = showAbilityHint;
    }

    protected EquipAbility(final EquipAbility ability) {
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
        String reminderText = " <i>(" + getManaCosts().getText() + ": Attach to target " + targetText + ". Equip only as a sorcery.)</i>";

        StringBuilder sb = new StringBuilder("Equip");
        if (!targetText.equals("creature you control")) {
            sb.append(' ').append(targetText);
        }
        String costText = getCosts().getText();
        if (costText != null && !costText.isEmpty()) {
            sb.append("&mdash;").append(costText).append('.');
        } else {
            sb.append(' ');
        }
        sb.append(getManaCosts().getText());
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
