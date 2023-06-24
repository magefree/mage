package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Elemental11BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalsReinforcements extends CardImpl {

    public RalsReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Create two 1/1 blue and red Elemental creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elemental11BlueRedToken(), 2));
    }

    private RalsReinforcements(final RalsReinforcements card) {
        super(card);
    }

    @Override
    public RalsReinforcements copy() {
        return new RalsReinforcements(this);
    }
}
