

package mage.cards.basiclands;

import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mountain extends BasicLand {
    public Mountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new RedManaAbility());
        this.frameColor.setRed(true);
    }

    private Mountain(final Mountain land) {
        super(land);
    }

    @Override
    public Mountain copy() {
        return new Mountain(this);
    }
}
