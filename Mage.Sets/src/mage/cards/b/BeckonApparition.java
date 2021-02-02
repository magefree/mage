
package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BeckonApparition extends CardImpl {

    public BeckonApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W/B}");

        // Exile target card from a graveyard. Create a 1/1 white and black Spirit creature token with flying.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WhiteBlackSpiritToken(), 1));
    }

    private BeckonApparition(final BeckonApparition card) {
        super(card);
    }

    @Override
    public BeckonApparition copy() {
        return new BeckonApparition(this);
    }
}
