
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TyphoidRats extends CardImpl {

    public TyphoidRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DeathtouchAbility.getInstance());
    }

    private TyphoidRats(final TyphoidRats card) {
        super(card);
    }

    @Override
    public TyphoidRats copy() {
        return new TyphoidRats(this);
    }
}
