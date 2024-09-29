

package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class OgreResister extends CardImpl {

    public OgreResister (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private OgreResister(final OgreResister card) {
        super(card);
    }

    @Override
    public OgreResister copy() {
        return new OgreResister(this);
    }

}
