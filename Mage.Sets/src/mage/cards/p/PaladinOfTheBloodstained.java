
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author TheElk801
 */
public final class PaladinOfTheBloodstained extends CardImpl {

    public PaladinOfTheBloodstained(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Paladin of the Bloodstained enters the battlefield, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken())));
    }

    private PaladinOfTheBloodstained(final PaladinOfTheBloodstained card) {
        super(card);
    }

    @Override
    public PaladinOfTheBloodstained copy() {
        return new PaladinOfTheBloodstained(this);
    }
}
