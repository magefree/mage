package mage.abilities.effects.common.continuous;

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

    private final Ability ability;

    public GainAbilityPairedEffect(Ability ability, String rule) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = rule;
    }

    private GainAbilityPairedEffect(final GainAbilityPairedEffect effect) {
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
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.getPairedMOR() != null) {
            Permanent paired = permanent.getPairedMOR().getPermanent(game);
            if (paired != null) {
                permanent.addAbility(ability, source.getSourceId(), game);
                paired.addAbility(ability, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

}
