
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Loki
 */
public final class AkromaAngelOfWrath extends CardImpl {

    public AkromaAngelOfWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // protection from black and from red
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED));
    }

    private AkromaAngelOfWrath(final AkromaAngelOfWrath card) {
        super(card);
    }

    @Override
    public AkromaAngelOfWrath copy() {
        return new AkromaAngelOfWrath(this);
    }
}
