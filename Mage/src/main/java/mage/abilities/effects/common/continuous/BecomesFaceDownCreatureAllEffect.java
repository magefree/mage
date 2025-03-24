package mage.abilities.effects.common.continuous;

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

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author LevelX2
 */

public class BecomesFaceDownCreatureAllEffect extends ContinuousEffectImpl {

    protected FilterPermanent filter;

    public BecomesFaceDownCreatureAllEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.Neutral);
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
            if (!perm.isFaceDown(game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
                perm.setFaceDown(true, game);
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean targetExists = false;
        List<MageObjectReference> objectsToRemove = new ArrayList<>();
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent == null || !permanent.isFaceDown(game)) {
                objectsToRemove.add(mor);
                continue;
            }
            targetExists = true;
            BecomesFaceDownCreatureEffect.FaceDownType type = BecomesFaceDownCreatureEffect.findFaceDownType(game, permanent);
            BecomesFaceDownCreatureEffect.makeFaceDownObject(game,
                    source.getSourceId(),
                    permanent,
                    type,
                    null);
        }
        if (!targetExists) {
            discard();
        }
        affectedObjectList.removeAll(objectsToRemove);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
