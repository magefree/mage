

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SewnEyeDrake extends CardImpl {

    public SewnEyeDrake (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/R}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRAKE);
        


        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private SewnEyeDrake(final SewnEyeDrake card) {
        super(card);
    }

    @Override
    public SewnEyeDrake copy() {
        return new SewnEyeDrake(this);
    }

}
