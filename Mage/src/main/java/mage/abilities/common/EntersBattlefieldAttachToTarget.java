package mage.abilities.common;

import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class EntersBattlefieldAttachToTarget extends EntersBattlefieldTriggeredAbility {

    public EntersBattlefieldAttachToTarget() {
        this(StaticFilters.FILTER_CONTROLLED_CREATURE);
    }

    public EntersBattlefieldAttachToTarget(FilterPermanent filter) {
        super(new AttachEffect(Outcome.BoostCreature, "attach it to target " + filter.getMessage()));
        this.addTarget(new TargetPermanent(filter));
    }

    private EntersBattlefieldAttachToTarget(final EntersBattlefieldAttachToTarget ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldAttachToTarget copy() {
        return new EntersBattlefieldAttachToTarget(this);
    }
}
