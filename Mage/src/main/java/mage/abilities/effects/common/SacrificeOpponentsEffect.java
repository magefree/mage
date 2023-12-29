package mage.abilities.effects.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.filter.FilterPermanent;

public class SacrificeOpponentsEffect extends SacrificeAllEffect {

    /**
     * Each opponent sacrifices a permanent
     * @param filter can be generic, will automatically add article and necessary sacrifice predicates
     */
    public SacrificeOpponentsEffect(FilterPermanent filter) {
        this(1, filter);
    }

    /**
     * Each opponent sacrifices N permanents
     * @param filter can be generic, will automatically add necessary sacrifice predicates
     */
    public SacrificeOpponentsEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    /**
     * Each opponent sacrifices X permanents
     * @param filter can be generic, will automatically add necessary sacrifice predicates
     */
    public SacrificeOpponentsEffect(DynamicValue amount, FilterPermanent filter) {
        super(amount, filter, true);
    }

    protected SacrificeOpponentsEffect(final SacrificeOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public SacrificeOpponentsEffect copy() {
        return new SacrificeOpponentsEffect(this);
    }

}
