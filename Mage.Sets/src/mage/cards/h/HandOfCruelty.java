
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
 *
 * @author Loki
 */
public final class HandOfCruelty extends CardImpl {

    public HandOfCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        this.addAbility(new BushidoAbility(1));
    }

    private HandOfCruelty(final HandOfCruelty card) {
        super(card);
    }

    @Override
    public HandOfCruelty copy() {
        return new HandOfCruelty(this);
    }
}
