

package mage.cards.a;

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
public final class AridMesa extends CardImpl {

    public AridMesa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.frameColor = new ObjectColor("WR");
        this.addAbility(new FetchLandActivatedAbility(SubType.MOUNTAIN, SubType.PLAINS));
    }

    private AridMesa(final AridMesa card) {
        super(card);
    }

    @Override
    public AridMesa copy() {
        return new AridMesa(this);
    }

}
