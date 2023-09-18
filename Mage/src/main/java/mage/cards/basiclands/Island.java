

package mage.cards.basiclands;

import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Island extends BasicLand {
    public Island(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new BlueManaAbility());
        this.frameColor.setBlue(true);
    }

    private Island(final Island land) {
        super(land);
    }

    @Override
    public Island copy() {
        return new Island(this);
    }
}
