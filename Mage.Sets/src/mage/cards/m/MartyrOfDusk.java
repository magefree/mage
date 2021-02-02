
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author L_J
 */
public final class MartyrOfDusk extends CardImpl {

    public MartyrOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Martyr of Dusk dies, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken())));
    }

    private MartyrOfDusk(final MartyrOfDusk card) {
        super(card);
    }

    @Override
    public MartyrOfDusk copy() {
        return new MartyrOfDusk(this);
    }
}
