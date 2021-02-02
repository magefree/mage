
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class NightsoilKami extends CardImpl {

    public NightsoilKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        this.addAbility(new SoulshiftAbility(5));
    }

    private NightsoilKami(final NightsoilKami card) {
        super(card);
    }

    @Override
    public NightsoilKami copy() {
        return new NightsoilKami(this);
    }
}
