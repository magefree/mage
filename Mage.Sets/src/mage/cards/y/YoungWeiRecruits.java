
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class YoungWeiRecruits extends CardImpl {

    public YoungWeiRecruits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Young Wei Recruits can't block.
        this.addAbility(new CantBlockAbility());
    }

    private YoungWeiRecruits(final YoungWeiRecruits card) {
        super(card);
    }

    @Override
    public YoungWeiRecruits copy() {
        return new YoungWeiRecruits(this);
    }
}
