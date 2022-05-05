

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Galatolol
 */

public class AddCardSubtypeAllEffect extends ContinuousEffectImpl {

    private FilterPermanent filter;
    private SubType addedSubtype;

    public AddCardSubtypeAllEffect(FilterPermanent _filter, SubType _addedSubtype, DependencyType _dependency) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        filter = _filter;
        staticText = "";
        addedSubtype = _addedSubtype;
        addDependencyType(_dependency);
    }

    public AddCardSubtypeAllEffect(final AddCardSubtypeAllEffect effect) {
        super(effect);
        filter = effect.filter.copy();
        addedSubtype = effect.addedSubtype;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (perm != null) {
                perm.addSubType(game, addedSubtype);
            }
        }
        return true;
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }

}