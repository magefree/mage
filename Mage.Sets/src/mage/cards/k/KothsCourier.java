

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class KothsCourier extends CardImpl {

    public KothsCourier (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new ForestwalkAbility());
    }

    private KothsCourier(final KothsCourier card) {
        super(card);
    }

    @Override
    public KothsCourier copy() {
        return new KothsCourier(this);
    }

}
