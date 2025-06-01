package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            object.addSubType(game, addedSubtype);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }
}
