

package mage.cards.basiclands;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Island extends BasicLand {
    public Island(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new BlueManaAbility());
        this.frameColor = ObjectColor.BLUE;
    }

    public Island(Island land) {
        super(land);
    }

    @Override
    public Card copy() {
        return new Island(this);
    }
}
