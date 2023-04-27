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

    private final FilterPermanent filter;
    private final SubType addedSubtype;

    public AddCardSubtypeAllEffect(FilterPermanent filter, SubType addedSubtype, DependencyType dependency) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.filter = filter;
        this.addedSubtype = addedSubtype;
        addDependencyType(dependency);
    }

    public AddCardSubtypeAllEffect(final AddCardSubtypeAllEffect effect) {
        super(effect);
        filter = effect.filter;
        addedSubtype = effect.addedSubtype;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            perm.addSubType(game, addedSubtype);
        }
        return true;
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }
}
