package mage.cards.f;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FortifyingProvisions extends CardImpl {

    public FortifyingProvisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield)));

        // When Fortifying Provisions enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private FortifyingProvisions(final FortifyingProvisions card) {
        super(card);
    }

    @Override
    public FortifyingProvisions copy() {
        return new FortifyingProvisions(this);
    }
}
