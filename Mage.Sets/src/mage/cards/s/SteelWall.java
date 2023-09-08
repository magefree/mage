

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SteelWall extends CardImpl {

    public SteelWall (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.addAbility(DefenderAbility.getInstance());
    }

    private SteelWall(final SteelWall card) {
        super(card);
    }

    @Override
    public SteelWall copy() {
        return new SteelWall(this);
    }

}
