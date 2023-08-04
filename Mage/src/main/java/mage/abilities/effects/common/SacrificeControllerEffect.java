
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 * The controlling player of the source ability has to sacrifice [count] permanents
 * that match the [filter].
 *
 * @author LevelX
 */
public class SacrificeControllerEffect extends SacrificeEffect {


    public SacrificeControllerEffect(FilterPermanent filter, DynamicValue count, String preText) {
        super(filter, count, preText);
    }

    public SacrificeControllerEffect(FilterPermanent filter, int count, String preText) {
        this(filter, StaticValue.get(count), preText);
    }

    protected SacrificeControllerEffect(final SacrificeControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        this.targetPointer = new FixedTarget(source.getControllerId());
        return super.apply(game, source);
    }

    @Override
    public SacrificeControllerEffect copy() {
        return new SacrificeControllerEffect(this);
    }
}

