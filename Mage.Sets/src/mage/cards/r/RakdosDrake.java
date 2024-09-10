

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class RakdosDrake extends CardImpl {

    public RakdosDrake (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Unleash
        this.addAbility(new UnleashAbility());

    }

    private RakdosDrake(final RakdosDrake card) {
        super(card);
    }

    @Override
    public RakdosDrake copy() {
        return new RakdosDrake(this);
    }

}
