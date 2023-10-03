

package mage.cards.basiclands;

import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Plains extends BasicLand {
    public Plains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new WhiteManaAbility());
        this.frameColor.setWhite(true);
    }

    private Plains(final Plains land) {
        super(land);
    }

    @Override
    public Plains copy() {
        return new Plains(this);
    }
}
