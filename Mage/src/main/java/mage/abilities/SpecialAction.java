

package mage.abilities;

import mage.abilities.costs.mana.ManaCost;
import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class SpecialAction extends ActivatedAbilityImpl {

    private boolean manaAction;
    protected ManaCost unpaidMana;

    public SpecialAction() {
        this(Zone.ALL);
    }

    public SpecialAction(Zone zone) {
        this(zone, false);
    }
    public SpecialAction(Zone zone, boolean manaAction) {
        super(AbilityType.SPECIAL_ACTION, zone);
        this.usesStack = false;
        this.manaAction = manaAction;
    }

    public SpecialAction(final SpecialAction action) {
        super(action);
        this.manaAction = action.manaAction;
        this.unpaidMana = action.unpaidMana;
    }

    public boolean isManaAction() {
        return manaAction;
    }

    public void setUnpaidMana(ManaCost manaCost) {
        this.unpaidMana = manaCost;
    }

    public ManaCost getUnpaidMana() {
        return unpaidMana;
    }
}
