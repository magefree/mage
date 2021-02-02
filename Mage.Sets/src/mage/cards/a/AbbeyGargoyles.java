
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Sir-Speshkitty
 */
public final class AbbeyGargoyles extends CardImpl {

    public AbbeyGargoyles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}{W}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private AbbeyGargoyles(final AbbeyGargoyles card) {
        super(card);
    }

    @Override
    public AbbeyGargoyles copy() {
        return new AbbeyGargoyles(this);
    }
}
