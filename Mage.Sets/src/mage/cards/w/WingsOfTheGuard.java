
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class WingsOfTheGuard extends CardImpl {

    public WingsOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Melee
        this.addAbility(new MeleeAbility());
    }

    private WingsOfTheGuard(final WingsOfTheGuard card) {
        super(card);
    }

    @Override
    public WingsOfTheGuard copy() {
        return new WingsOfTheGuard(this);
    }
}
