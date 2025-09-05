package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HotDogCart extends CardImpl {

    public HotDogCart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When this artifact enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private HotDogCart(final HotDogCart card) {
        super(card);
    }

    @Override
    public HotDogCart copy() {
        return new HotDogCart(this);
    }
}
