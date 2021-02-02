
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CemeteryGate extends CardImpl {

    public CemeteryGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    private CemeteryGate(final CemeteryGate card) {
        super(card);
    }

    @Override
    public CemeteryGate copy() {
        return new CemeteryGate(this);
    }
}
