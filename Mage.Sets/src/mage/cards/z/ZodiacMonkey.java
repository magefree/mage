
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ZodiacMonkey extends CardImpl {

    public ZodiacMonkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.MONKEY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new ForestwalkAbility());
    }

    private ZodiacMonkey(final ZodiacMonkey card) {
        super(card);
    }

    @Override
    public ZodiacMonkey copy() {
        return new ZodiacMonkey(this);
    }
}
