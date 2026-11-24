package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

enum isChosenTypePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        SubType type = ChooseCreatureTypeEffect.getChosenCreatureType(input.getSourceId(), game);
        return input.getObject().hasSubtype(type, game);
    }
}

/**
 * @author LevelX2
 */
public class BoostAllOfChosenSubtypeEffect extends BoostAllEffect {

    public BoostAllOfChosenSubtypeEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, new FilterCreaturePermanent("creatures of the chosen type"), excludeSource);
    }

    public BoostAllOfChosenSubtypeEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, filter, excludeSource);
    }

    public BoostAllOfChosenSubtypeEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        super(power, toughness, duration, modify(filter), excludeSource);
    }

    protected BoostAllOfChosenSubtypeEffect(final BoostAllOfChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public BoostAllOfChosenSubtypeEffect copy() {
        return new BoostAllOfChosenSubtypeEffect(this);
    }

    private static FilterCreaturePermanent modify(FilterCreaturePermanent filter) {
        FilterCreaturePermanent copy = filter.copy();
        copy.add(isChosenTypePredicate.instance);
        return copy;
    }
}
