
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Rystan
 */
public class EquipLegendaryAbility extends ActivatedAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("legendary creature you control");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public EquipLegendaryAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetControlledCreaturePermanent(filter));
    }

    public EquipLegendaryAbility(Outcome outcome, Cost cost, Target target) {
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

    public EquipLegendaryAbility(final EquipLegendaryAbility ability) {
        super(ability);
    }

    @Override
    public EquipLegendaryAbility copy() {
        return new EquipLegendaryAbility(this);
    }

    @Override
    public String getRule() {
        return "Equip legendary creature " + costs.getText()
                + manaCosts.getText() + " (" + manaCosts.getText()
                + ": <i>Attach to target legendary creature you control. Equip only as a sorcery.)</i>";
    }

}
