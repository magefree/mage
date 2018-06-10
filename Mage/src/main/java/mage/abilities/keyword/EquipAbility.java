
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EquipAbility extends ActivatedAbilityImpl {

    public EquipAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetControlledCreaturePermanent());
    }

    public EquipAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Equip"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ActivationStatus activationStatus = super.canActivate(playerId, game);
        if (activationStatus.canActivate()) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.hasSubtype(SubType.EQUIPMENT, game)) {
                return activationStatus;
            }
        }
        return activationStatus;
    }

    public EquipAbility(final EquipAbility ability) {
        super(ability);
    }

    @Override
    public EquipAbility copy() {
        return new EquipAbility(this);
    }

    @Override
    public String getRule() {
        return "Equip " + costs.getText() + manaCosts.getText() + " (" + manaCosts.getText() + ": <i>Attach to target creature you control. Equip only as a sorcery.)</i>";
    }

}
