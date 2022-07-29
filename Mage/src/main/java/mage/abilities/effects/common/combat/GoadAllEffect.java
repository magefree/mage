package mage.abilities.effects.common.combat;

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
        this.affectedObjectsSet = affectedObjectsSet;
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
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            game.getBattlefield()
                    .getActivePermanents(
                            filter, source.getControllerId(), source, game
                    ).stream()
                    .map(permanent -> new MageObjectReference(permanent, game))
                    .forEach(this.affectedObjectList::add);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            this.affectedObjectList.removeIf(mor -> !mor.zoneCounterIsCurrent(game));
            if (affectedObjectList.isEmpty()) {
                discard();
                return false;
            }
            for (MageObjectReference mor : this.affectedObjectList) {
                mor.getPermanent(game).addGoadingPlayer(source.getControllerId());
            }
            return true;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            creature.addGoadingPlayer(source.getControllerId());
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "Goad all " + filter.getMessage() + ". <i>(Until your next turn, those creatures attack each combat if able and attack a player other than you if able.)</i>";
    }
}
