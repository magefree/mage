

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ShieldBearer extends CardImpl {

    public ShieldBearer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private ShieldBearer(final ShieldBearer card) {
        super(card);
    }

    @Override
    public ShieldBearer copy() {
        return new ShieldBearer(this);
    }

}
