package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HalflingToken;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class FarmerCotton extends CardImpl {

    public FarmerCotton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Farmer Cotton enters the battlefield, create X 1/1 white Halfling creature tokens and X Food tokens.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(new HalflingToken(), ManacostVariableValue.ETB)
        );
        trigger.addEffect(
            new CreateTokenEffect(new FoodToken(), ManacostVariableValue.ETB)
                .setText("and X Food tokens")
        );

        this.addAbility(trigger);
    }

    private FarmerCotton(final FarmerCotton card) {
        super(card);
    }

    @Override
    public FarmerCotton copy() {
        return new FarmerCotton(this);
    }
}
