

package mage.abilities.effects.common.continuous;

import mage.abilities.keyword.ProtectionAbility;
import mage.constants.Duration;
import mage.filter.FilterCard;

/**
 * @author ayratn
 */
public class GainProtectionFromTypeTargetEffect extends GainAbilityTargetEffect {

    public GainProtectionFromTypeTargetEffect(Duration duration, FilterCard protectionFrom) {
        super(new ProtectionAbility(new FilterCard()).setFilter(protectionFrom), duration);
        staticText = "Target creature gains protection from " + protectionFrom.getMessage() + ' ' + duration.toString();
    }

    protected GainProtectionFromTypeTargetEffect(final GainProtectionFromTypeTargetEffect effect) {
        super(effect);
    }

    @Override
    public GainProtectionFromTypeTargetEffect copy() {
        return new GainProtectionFromTypeTargetEffect(this);
    }

}
