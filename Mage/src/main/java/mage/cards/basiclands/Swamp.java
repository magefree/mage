

package mage.cards.basiclands;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Swamp extends BasicLand {
    public Swamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new BlackManaAbility());
        this.frameColor.setBlack(true);
    }

    private Swamp(final Swamp land) {
        super(land);
    }

    @Override
    public Swamp copy() {
        return new Swamp(this);
    }
}
