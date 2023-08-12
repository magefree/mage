

package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.keyword.ProtectionAbility;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author ayratn
 */
public class GainProtectionFromTypeTargetEffect extends GainAbilityTargetEffect {

    private String typeName;

    public GainProtectionFromTypeTargetEffect(Duration duration, FilterCard protectionFrom) {
        super(new ProtectionAbility(new FilterCard()), duration);
        ((ProtectionAbility) ability).setFilter(protectionFrom);
        typeName = protectionFrom.getMessage();
        staticText = "Target creature gains protection from " + typeName + ' ' + duration.toString();
    }

    protected GainProtectionFromTypeTargetEffect(final GainProtectionFromTypeTargetEffect effect) {
        super(effect);
        this.typeName = effect.typeName;
    }

    @Override
    public GainProtectionFromTypeTargetEffect copy() {
        return new GainProtectionFromTypeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }

}
