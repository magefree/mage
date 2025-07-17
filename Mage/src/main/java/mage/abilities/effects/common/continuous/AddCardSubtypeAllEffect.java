package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author Galatolol
 */
public class AddCardSubtypeAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    private final SubType addedSubtype;

    /**
     * Note: must set text manually
     */
    public AddCardSubtypeAllEffect(FilterPermanent filter, SubType addedSubtype, DependencyType dependency) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.filter = filter;
        this.addedSubtype = addedSubtype;
        this.staticText = filter.getMessage() + " are " + addedSubtype.getPluralName() + " in addition to their other types";
        addDependencyType(dependency);
    }

    protected AddCardSubtypeAllEffect(final AddCardSubtypeAllEffect effect) {
        super(effect);
        filter = effect.filter;
        addedSubtype = effect.addedSubtype;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addSubType(game, addedSubtype);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        ));
        return !affectedObjects.isEmpty();
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }
}
