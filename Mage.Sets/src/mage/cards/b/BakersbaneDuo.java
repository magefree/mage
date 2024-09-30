package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BakersbaneDuo extends CardImpl {

    public BakersbaneDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.RACCOON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Bakersbane Duo enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you expend 4, Bakersbane Duo gets +1/+1 until end of turn.
        this.addAbility(new ExpendTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), ExpendTriggeredAbility.Expend.FOUR
        ));
    }

    private BakersbaneDuo(final BakersbaneDuo card) {
        super(card);
    }

    @Override
    public BakersbaneDuo copy() {
        return new BakersbaneDuo(this);
    }
}
