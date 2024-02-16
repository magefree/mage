

package mage.cards.basiclands;

import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Forest extends BasicLand {
    public Forest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new GreenManaAbility());
        this.frameColor.setGreen(true);
    }

    private Forest(final Forest land) {
        super(land);
    }

    @Override
    public Forest copy() {
        return new Forest(this);
    }
}
