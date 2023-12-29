package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

import java.util.Optional;

/**
 * @author xenohedron
 */
public class CantBeSacrificedSourceEffect extends ContinuousEffectImpl {

    public CantBeSacrificedSourceEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} can't be sacrificed";
    }

    protected CantBeSacrificedSourceEffect(final CantBeSacrificedSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeSacrificedSourceEffect copy() {
        return new CantBeSacrificedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.setCanBeSacrificed(false));
        return true;
    }

}
