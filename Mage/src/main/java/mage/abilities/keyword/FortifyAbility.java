

package mage.abilities.keyword;

import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */

//20091005 - 702.64
public class FortifyAbility extends ActivatedAbilityImpl {

    public FortifyAbility(int cost) {
        this(Outcome.AddAbility, new GenericManaCost(cost));
    }

    public FortifyAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetPermanent(new FilterControlledLandPermanent()));
    }

    public FortifyAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Fortify"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.hasSubtype(SubType.FORTIFICATION, game) && !permanent.isCreature() && !permanent.isLand()) {
            return super.canActivate(playerId, game);
        } else {
            return ActivationStatus.getFalse();
        }
    }

    public FortifyAbility(final FortifyAbility ability) {
        super(ability);
    }

    @Override
    public FortifyAbility copy() {
        return new FortifyAbility(this);
    }


    @Override
    public String getRule() {
        return "Fortify " + costs.getText() + manaCosts.getText() + " (" + manaCosts.getText() + ": <i>Attach to target land you control. Fortify only as a sorcery.)</i>";
    }
}