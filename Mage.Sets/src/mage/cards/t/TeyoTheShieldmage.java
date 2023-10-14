package mage.cards.t;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TeyoToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeyoTheShieldmage extends CardImpl {

    public TeyoTheShieldmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEYO);
        this.setStartingLoyalty(5);

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // -2: Create a 0/3 white Wall creature token with defender.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new TeyoToken()), -2));
    }

    private TeyoTheShieldmage(final TeyoTheShieldmage card) {
        super(card);
    }

    @Override
    public TeyoTheShieldmage copy() {
        return new TeyoTheShieldmage(this);
    }
}
