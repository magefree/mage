
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class UktabiDrake extends CardImpl {

    public UktabiDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Echo {1}{G}{G}
        this.addAbility(new EchoAbility("{1}{G}{G}"));
    }

    private UktabiDrake(final UktabiDrake card) {
        super(card);
    }

    @Override
    public UktabiDrake copy() {
        return new UktabiDrake(this);
    }
}
