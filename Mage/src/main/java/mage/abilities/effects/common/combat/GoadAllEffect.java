package mage.abilities.effects.common.combat;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class GoadAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public GoadAllEffect(FilterPermanent filter) {
        this(Duration.UntilYourNextTurn, filter);
    }

    public GoadAllEffect(Duration duration, FilterPermanent filter) {
        this(duration, filter, true);
    }

    public GoadAllEffect(Duration duration, FilterPermanent filter, boolean affectedObjectsSet) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        this.filter = filter;
        this.setAffectedObjectsSet(affectedObjectsSet);
    }

    private GoadAllEffect(final GoadAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GoadAllEffect copy() {
        return new GoadAllEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.addGoadingPlayer(source.getControllerId());
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            game.getBattlefield()
                    .getActivePermanents(
                            filter, source.getControllerId(), source, game
                    ).stream()
                    .map(permanent -> new MageObjectReference(permanent, game))
                    .forEach(this.affectedObjectList::add);
        }
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        ArrayList<MageItem> objects = new ArrayList<>();
        if (getAffectedObjectsSet()) {
            affectedObjectList.removeIf(mor -> !mor.zoneCounterIsCurrent(game));
            for (MageObjectReference mor : affectedObjectList) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null) {
                    objects.add(permanent);
                }
            }
        } else {
            objects.addAll(game.getBattlefield()
                    .getActivePermanents(filter, source.getControllerId(), source, game)
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        return objects;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "Goad all " + filter.getMessage() + ". <i>(Until your next turn, those creatures attack each combat if able and attack a player other than you if able.)</i>";
    }
}
