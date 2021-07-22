

package mage.cards.m;

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
public final class MarshFlats extends CardImpl {

    public MarshFlats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.frameColor = new ObjectColor("WB");
        this.addAbility(new FetchLandActivatedAbility(SubType.PLAINS, SubType.SWAMP));
    }

    private MarshFlats(final MarshFlats card) {
        super(card);
    }

    @Override
    public MarshFlats copy() {
        return new MarshFlats(this);
    }

}
