
package mage.cards.g;

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
public final class GlacialWall extends CardImpl {

    public GlacialWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        this.addAbility(DefenderAbility.getInstance());
    }

    private GlacialWall(final GlacialWall card) {
        super(card);
    }

    @Override
    public GlacialWall copy() {
        return new GlacialWall(this);
    }
}
