package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CityPigeon extends CardImpl {

    public CityPigeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature leaves the battlefield, create a Food token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private CityPigeon(final CityPigeon card) {
        super(card);
    }

    @Override
    public CityPigeon copy() {
        return new CityPigeon(this);
    }
}
