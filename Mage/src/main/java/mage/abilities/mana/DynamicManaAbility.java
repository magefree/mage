
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author North
 */
public class DynamicManaAbility extends ActivatedManaAbilityImpl {

    private DynamicManaEffect manaEffect;
    private String rule;

    /**
     * TapSourceCost added by default
     *
     * @param mana
     * @param amount
     */
    public DynamicManaAbility(Mana mana, DynamicValue amount) {
        this(mana, amount, new TapSourceCost());
    }

    /**
     * @param mana   - kind of mana
     * @param amount - value for the numbe rof numer
     * @param text   - rule text for the DynamicManaEffect
     */
    public DynamicManaAbility(Mana mana, DynamicValue amount, String text) {
        this(mana, amount, new TapSourceCost(), text);
    }

    public DynamicManaAbility(Mana mana, DynamicValue amount, Cost cost) {
        this(mana, amount, cost, null);
    }

    public DynamicManaAbility(Mana mana, DynamicValue amount, Cost cost, String text) {
        this(mana, amount, cost, text, false);
    }

    public DynamicManaAbility(Mana mana, DynamicValue amount, Cost cost, String text, boolean oneChoice) {
        this(mana, amount, cost, text, oneChoice, null);
    }

    /**
     * @param mana
     * @param amount
     * @param cost
     * @param text
     * @param oneChoice is all mana from the same colour or if false the player
     *                  can choose different colours
     * @param netAmount a dynamic value that calculates the possible available
     *                  mana (e.g. if you have to pay by removing counters from source)
     */
    public DynamicManaAbility(Mana mana, DynamicValue amount, Cost cost, String text, boolean oneChoice, DynamicValue netAmount) {
        super(Zone.BATTLEFIELD, new DynamicManaEffect(mana, amount, text, oneChoice, netAmount), cost);
        manaEffect = (DynamicManaEffect) this.getEffects().get(0);
    }

    protected DynamicManaAbility(final DynamicManaAbility ability) {
        super(ability);
        manaEffect = ability.manaEffect;
        rule = ability.rule;
    }

    @Override
    public DynamicManaAbility copy() {
        return new DynamicManaAbility(this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }
}
