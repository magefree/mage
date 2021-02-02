
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RazortoothRats extends CardImpl {

    public RazortoothRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FearAbility.getInstance());
    }

    private RazortoothRats(final RazortoothRats card) {
        super(card);
    }

    @Override
    public RazortoothRats copy() {
        return new RazortoothRats(this);
    }
}
