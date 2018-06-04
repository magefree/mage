
package mage.abilities.costs.mana;

import mage.Mana;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author nantuko
 */
public class PhyrexianManaCost extends ColoredManaCost {

    public PhyrexianManaCost(ColoredManaSymbol mana) {
        super(mana);
        options.add(Mana.GenericMana(0));
    }

    public PhyrexianManaCost(PhyrexianManaCost manaCost) {
        super(manaCost);
    }

    @Override
    public String getText() {
        return '{' + mana.toString() + "/P}";
    }

    public String getBaseText() {
        return super.getText();
    }

    @Override
    public ColoredManaCost getUnpaid() {
        return new ColoredManaCost(this);
    }

    @Override
    public PhyrexianManaCost copy() {
        return new PhyrexianManaCost(this);
    }
}
