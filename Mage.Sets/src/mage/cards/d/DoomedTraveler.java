
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author nantuko
 */
public final class DoomedTraveler extends CardImpl {

    public DoomedTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Doomed Traveler dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));
    }

    private DoomedTraveler(final DoomedTraveler card) {
        super(card);
    }

    @Override
    public DoomedTraveler copy() {
        return new DoomedTraveler(this);
    }
}
