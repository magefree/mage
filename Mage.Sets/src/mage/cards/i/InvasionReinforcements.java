package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionReinforcements extends CardImpl {

    public InvasionReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AllyToken())));
    }

    private InvasionReinforcements(final InvasionReinforcements card) {
        super(card);
    }

    @Override
    public InvasionReinforcements copy() {
        return new InvasionReinforcements(this);
    }
}
