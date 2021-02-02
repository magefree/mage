
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class WalkingCorpse extends CardImpl {

    public WalkingCorpse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private WalkingCorpse(final WalkingCorpse card) {
        super(card);
    }

    @Override
    public WalkingCorpse copy() {
        return new WalkingCorpse(this);
    }
}
