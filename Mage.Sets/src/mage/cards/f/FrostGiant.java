
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class FrostGiant extends CardImpl {

    public FrostGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Rampage 2
        this.addAbility(new RampageAbility(2));
    }

    private FrostGiant(final FrostGiant card) {
        super(card);
    }

    @Override
    public FrostGiant copy() {
        return new FrostGiant(this);
    }
}
