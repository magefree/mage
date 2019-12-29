package mage.cards.t;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ElephantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrumpetingHerd extends CardImpl {

    public TrumpetingHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Create a 3/3 green Elephant creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private TrumpetingHerd(final TrumpetingHerd card) {
        super(card);
    }

    @Override
    public TrumpetingHerd copy() {
        return new TrumpetingHerd(this);
    }
}
