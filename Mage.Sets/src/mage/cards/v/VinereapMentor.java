package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
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
public final class VinereapMentor extends CardImpl {

    public VinereapMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Vinereap Mentor enters or dies, create a Food token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));
    }

    private VinereapMentor(final VinereapMentor card) {
        super(card);
    }

    @Override
    public VinereapMentor copy() {
        return new VinereapMentor(this);
    }
}
