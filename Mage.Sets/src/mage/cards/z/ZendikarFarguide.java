
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
public final class ZendikarFarguide extends CardImpl {

    public ZendikarFarguide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new ForestwalkAbility());
    }

    private ZendikarFarguide(final ZendikarFarguide card) {
        super(card);
    }

    @Override
    public ZendikarFarguide copy() {
        return new ZendikarFarguide(this);
    }
}
