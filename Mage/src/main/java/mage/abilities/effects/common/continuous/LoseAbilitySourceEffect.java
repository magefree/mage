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
 * @author Noahsark
 */
public class LoseAbilitySourceEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public LoseAbilitySourceEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public LoseAbilitySourceEffect(Ability ability, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        staticText = ("{this} loses " + ability.getRule() + ' ' + duration.toString()).trim();
    }

    public LoseAbilitySourceEffect(final LoseAbilitySourceEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public LoseAbilitySourceEffect copy() {
        return new LoseAbilitySourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeAbility(ability, source.getSourceId(), game);
        }
        return true;
    }
}
