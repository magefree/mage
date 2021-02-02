
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ForceOfSavagery extends CardImpl {

    public ForceOfSavagery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(0);

        this.addAbility(TrampleAbility.getInstance());
    }

    private ForceOfSavagery(final ForceOfSavagery card) {
        super(card);
    }

    @Override
    public ForceOfSavagery copy() {
        return new ForceOfSavagery(this);
    }
}
