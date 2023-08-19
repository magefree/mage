
package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class GainAbilityPairedEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public GainAbilityPairedEffect(Ability ability, String rule) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = rule;
    }

    protected GainAbilityPairedEffect(final GainAbilityPairedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
    }

    @Override
    public GainAbilityPairedEffect copy() {
        return new GainAbilityPairedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getPairedCard() != null) {
            Permanent paired = permanent.getPairedCard().getPermanent(game);
            if (paired != null && paired.getPairedCard() != null && paired.getPairedCard().equals(new MageObjectReference(permanent, game))) {
                permanent.addAbility(ability, source.getSourceId(), game);
                paired.addAbility(ability, source.getSourceId(), game);
                return true;

            } else {
                // No longer the same cards as orininally paired.
                permanent.setPairedCard(null);
            }
        }
        return false;
    }

}
