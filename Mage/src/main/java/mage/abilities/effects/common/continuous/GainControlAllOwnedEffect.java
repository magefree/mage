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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    protected GainControlAllOwnedEffect(final GainControlAllOwnedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GainControlAllOwnedEffect copy() {
        return new GainControlAllOwnedEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            if (!permanent.isControlledBy(permanent.getOwnerId())) {
                permanent.changeControllerId(permanent.getOwnerId(), game, source);
            }
        }
        return true;
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
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        ArrayList<MageItem> objects = new ArrayList<>();
        for (Iterator<MageObjectReference> iterator = affectedObjectList.iterator(); iterator.hasNext();) {
            Permanent permanent = (Permanent) iterator.next();
            if (permanent == null) {
                iterator.remove();
                continue;
            }
            objects.add(permanent);
        }
        return objects;
    }
}
