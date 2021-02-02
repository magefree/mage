
package mage.cards.w;

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
 * @author LoneFox
 */
public final class WindreaperFalcon extends CardImpl {

    public WindreaperFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private WindreaperFalcon(final WindreaperFalcon card) {
        super(card);
    }

    @Override
    public WindreaperFalcon copy() {
        return new WindreaperFalcon(this);
    }
}
