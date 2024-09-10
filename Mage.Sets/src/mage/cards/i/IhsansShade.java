
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class IhsansShade extends CardImpl {

    public IhsansShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHADE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
    }

    private IhsansShade(final IhsansShade card) {
        super(card);
    }

    @Override
    public IhsansShade copy() {
        return new IhsansShade(this);
    }
}
