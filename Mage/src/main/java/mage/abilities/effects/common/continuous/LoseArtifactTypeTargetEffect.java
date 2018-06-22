
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public class LoseArtifactTypeTargetEffect extends ContinuousEffectImpl{

    public LoseArtifactTypeTargetEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
        setText("isn't an artifact");
    }

    public LoseArtifactTypeTargetEffect(final LoseArtifactTypeTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseArtifactTypeTargetEffect copy() {
        return new LoseArtifactTypeTargetEffect(this);
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            if (targetId != null) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                permanent.getCardType().remove(CardType.ARTIFACT);
                                permanent.getSubtype(game).removeAll(SubType.getArtifactTypes(false));
                            }
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

}
