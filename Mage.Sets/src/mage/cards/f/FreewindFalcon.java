
package mage.cards.f;

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
 * @author anonymous
 */
public final class FreewindFalcon extends CardImpl {

    public FreewindFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private FreewindFalcon(final FreewindFalcon card) {
        super(card);
    }

    @Override
    public FreewindFalcon copy() {
        return new FreewindFalcon(this);
    }
}
