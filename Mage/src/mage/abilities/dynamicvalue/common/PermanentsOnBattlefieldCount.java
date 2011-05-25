package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author North
 */
public class PermanentsOnBattlefieldCount implements DynamicValue {
    
    private FilterPermanent filter;
    private boolean controlled;
    
    public PermanentsOnBattlefieldCount() {
        filter = new FilterPermanent();
        controlled = false;
    }
    
    public PermanentsOnBattlefieldCount(FilterPermanent filter, boolean controlled) {
        this.filter = filter;
        this.controlled = controlled;
    }
    
    public PermanentsOnBattlefieldCount(final PermanentsOnBattlefieldCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.controlled = dynamicValue.controlled;
    }
    
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (controlled) {
            return game.getBattlefield().countAll(filter, sourceAbility.getControllerId());
        } else {
            return game.getBattlefield().countAll(filter);
        }
    }
    
    @Override
    public DynamicValue clone() {
        return new PermanentsOnBattlefieldCount(this);
    }
    
    @Override
    public String toString() {
        return "X";
    }
}
