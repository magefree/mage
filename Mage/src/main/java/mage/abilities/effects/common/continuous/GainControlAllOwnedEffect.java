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

import java.util.Iterator;

/**
 * @author TheElk801
 */
public class GainControlAllOwnedEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public GainControlAllOwnedEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.filter = filter;
        this.staticText = "each player gains control of all " + filter + " they own";
    }

    public GainControlAllOwnedEffect(final GainControlAllOwnedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GainControlAllOwnedEffect copy() {
        return new GainControlAllOwnedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            affectedObjectList.add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent == null) {
                it.remove();
                continue;
            }
            if (!permanent.isControlledBy(permanent.getOwnerId())) {
                permanent.changeControllerId(permanent.getOwnerId(), game, source);
            }
        }
        if (affectedObjectList.isEmpty()) {
            this.discard();
        }
        return true;
    }
}
