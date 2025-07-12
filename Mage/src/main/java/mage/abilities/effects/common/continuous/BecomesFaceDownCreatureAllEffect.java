package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author LevelX2
 */

public class BecomesFaceDownCreatureAllEffect extends ContinuousEffectImpl {

    protected FilterPermanent filter;

    public BecomesFaceDownCreatureAllEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.BecomeCreature);
        this.filter = filter;
        staticText = "turn all " + filter.getMessage() + " face down. (They're 2/2 creatures.)";
    }

    protected BecomesFaceDownCreatureAllEffect(final BecomesFaceDownCreatureAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public BecomesFaceDownCreatureAllEffect copy() {
        return new BecomesFaceDownCreatureAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        // save permanents to become face down (one time usage on resolve)
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!perm.isFaceDown(game) && !perm.isTransformable()) {
                affectedObjectList.add(new MageObjectReference(perm, game));
                perm.setFaceDown(true, game);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null && permanent.isFaceDown(game)) {
                affectedObjects.add(permanent);
            }
        }
        if (affectedObjects.isEmpty()) {
            this.discard();
            return false;
        }
        return true;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            BecomesFaceDownCreatureEffect.FaceDownType faceDownType = BecomesFaceDownCreatureEffect.findFaceDownType(game, permanent);
            BecomesFaceDownCreatureEffect.makeFaceDownObject(game, source.getId(), permanent, faceDownType, null);
        }
    }
}
