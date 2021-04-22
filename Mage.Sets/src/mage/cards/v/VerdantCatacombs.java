

package mage.cards.v;

import java.util.EnumSet;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.FetchLandActivatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class VerdantCatacombs extends CardImpl {

    public VerdantCatacombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.frameColor = new ObjectColor("BG");
        this.addAbility(new FetchLandActivatedAbility(SubType.SWAMP, SubType.FOREST));
    }

    private VerdantCatacombs(final VerdantCatacombs card) {
        super(card);
    }

    @Override
    public VerdantCatacombs copy() {
        return new VerdantCatacombs(this);
    }

}
