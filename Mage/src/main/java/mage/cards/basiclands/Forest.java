

package mage.cards.basiclands;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Forest extends BasicLand {
    public Forest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new GreenManaAbility());
        this.frameColor.setGreen(true);
    }

    public Forest(final Forest land) {
        super(land);
    }

    @Override
    public Card copy() {
        return new Forest(this);
    }
}
