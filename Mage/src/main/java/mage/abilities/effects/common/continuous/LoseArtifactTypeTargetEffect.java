package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noahg
 */
public class LoseArtifactTypeTargetEffect extends ContinuousEffectImpl {

    public LoseArtifactTypeTargetEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
        setText("isn't an artifact");
    }

    protected LoseArtifactTypeTargetEffect(final LoseArtifactTypeTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseArtifactTypeTargetEffect copy() {
        return new LoseArtifactTypeTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            if (targetId == null) {
                continue;
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            permanent.removeCardType(game, CardType.ARTIFACT);
            permanent.removeAllSubTypes(game, SubTypeSet.ArtifactType);
            return true;
        }
        return false;
    }
}
