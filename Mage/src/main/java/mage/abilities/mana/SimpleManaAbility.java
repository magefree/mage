package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleManaAbility extends ActivatedManaAbilityImpl {

    private boolean predictable;

    public SimpleManaAbility(Zone zone, ManaEffect effect, Cost cost) {
        this(zone, effect, cost, true);
    }

    /**
     * @param zone
     * @param effect
     * @param cost
     * @param predictable set to false if defining the mana type or amount needs
     *                    to reveal information and can't be predicted
     */
    public SimpleManaAbility(Zone zone, ManaEffect effect, Cost cost, boolean predictable) {
        super(zone, effect, cost);
        this.predictable = predictable;
    }

    public SimpleManaAbility(Zone zone, Mana mana, Cost cost) {
        this(zone, mana, cost, null);
        this.netMana.add(mana.copy());
        this.predictable = true;
    }

    /**
     * @param zone
     * @param mana
     * @param cost      cost for one usage
     * @param netAmount DynamicValu to calculate the max available mana if effect is repeatable
     */
    public SimpleManaAbility(Zone zone, Mana mana, Cost cost, DynamicValue netAmount) {
        super(zone, new BasicManaEffect(mana, netAmount), cost);
        this.netMana.add(mana.copy());
        this.predictable = true;
    }

    protected SimpleManaAbility(final SimpleManaAbility ability) {
        super(ability);
        this.predictable = ability.predictable;
    }

    @Override
    public SimpleManaAbility copy() {
        return new SimpleManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        if (predictable) {
            return super.getNetMana(game);
        }
        return new ArrayList<>(netMana);
    }


}
