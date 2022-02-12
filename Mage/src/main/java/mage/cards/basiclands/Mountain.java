

package mage.cards.basiclands;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.mana.RedManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mountain extends BasicLand {
    public Mountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new RedManaAbility());
        this.frameColor.setRed(true);
    }

    public Mountain(Mountain land) {
        super(land);
    }

    @Override
    public Card copy() {
        return new Mountain(this);
    }
}
