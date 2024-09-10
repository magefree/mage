package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrandywineFarmer extends CardImpl {

    public BrandywineFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brandywine Farmer enters or leaves the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));
    }

    private BrandywineFarmer(final BrandywineFarmer card) {
        super(card);
    }

    @Override
    public BrandywineFarmer copy() {
        return new BrandywineFarmer(this);
    }
}
