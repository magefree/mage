
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class OgreSentry extends CardImpl {

    public OgreSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
    }

    private OgreSentry(final OgreSentry card) {
        super(card);
    }

    @Override
    public OgreSentry copy() {
        return new OgreSentry(this);
    }
}
