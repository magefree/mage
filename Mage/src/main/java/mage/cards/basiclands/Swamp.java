

package mage.cards.basiclands;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Swamp extends BasicLand {
    public Swamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new BlackManaAbility());
        this.frameColor.setBlack(true);
    }

    public Swamp(Swamp land) {
        super(land);
    }

    @Override
    public Card copy() {
        return new Swamp(this);
    }
}
