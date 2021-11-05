

package mage.cards.basiclands;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Plains extends BasicLand {
    public Plains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new WhiteManaAbility());
        this.frameColor.setWhite(true);
    }

    public Plains(Plains land) {
        super(land);
    }

    @Override
    public Card copy() {
        return new Plains(this);
    }
}
