
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class OgreTaskmaster extends CardImpl {

    public OgreTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.OGRE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Ogre Taskmaster can't block.
        this.addAbility(new CantBlockAbility());
    }

    private OgreTaskmaster(final OgreTaskmaster card) {
        super(card);
    }

    @Override
    public OgreTaskmaster copy() {
        return new OgreTaskmaster(this);
    }
}
