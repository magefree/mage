package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyoshiWarriors extends CardImpl {

    public KyoshiWarriors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AllyToken())));
    }

    private KyoshiWarriors(final KyoshiWarriors card) {
        super(card);
    }

    @Override
    public KyoshiWarriors copy() {
        return new KyoshiWarriors(this);
    }
}
