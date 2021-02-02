
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RoninCavekeeper extends CardImpl {

    public RoninCavekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(new BushidoAbility(2));
    }

    private RoninCavekeeper(final RoninCavekeeper card) {
        super(card);
    }

    @Override
    public RoninCavekeeper copy() {
        return new RoninCavekeeper(this);
    }
}
