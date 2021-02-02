
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class HoverguardObserver extends CardImpl {

    public HoverguardObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Hoverguard Observer can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private HoverguardObserver(final HoverguardObserver card) {
        super(card);
    }

    @Override
    public HoverguardObserver copy() {
        return new HoverguardObserver(this);
    }
}
