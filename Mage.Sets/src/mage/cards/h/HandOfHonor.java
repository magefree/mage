
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author anonymous
 */
public final class HandOfHonor extends CardImpl {

    public HandOfHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        this.addAbility(new BushidoAbility(1));
    }

    private HandOfHonor(final HandOfHonor card) {
        super(card);
    }

    @Override
    public HandOfHonor copy() {
        return new HandOfHonor(this);
    }
}
