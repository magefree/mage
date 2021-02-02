
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class CarrionCrow extends CardImpl {

    public CarrionCrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Carrion Crow enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private CarrionCrow(final CarrionCrow card) {
        super(card);
    }

    @Override
    public CarrionCrow copy() {
        return new CarrionCrow(this);
    }
}
