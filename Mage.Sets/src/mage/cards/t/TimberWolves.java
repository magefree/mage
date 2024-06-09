

package mage.cards.t;

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
public final class TimberWolves extends CardImpl {

    public TimberWolves (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private TimberWolves(final TimberWolves card) {
        super(card);
    }

    @Override
    public TimberWolves copy() {
        return new TimberWolves(this);
    }

}
