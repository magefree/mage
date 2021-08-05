package mage.abilities.icon.system;

import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public class CombinedCountIcon extends CardIconImpl {

    public CombinedCountIcon(int hiddenCount, String hint) {
        super(CardIconType.SYSTEM_COMBINED, hint, "+" + hiddenCount);
    }

}
