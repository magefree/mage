
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BeckonApparitionToken;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author Loki
 */
public final class BeckonApparition extends CardImpl {

    public BeckonApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W/B}");

        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeckonApparitionToken(), 1));
    }

    public BeckonApparition(final BeckonApparition card) {
        super(card);
    }

    @Override
    public BeckonApparition copy() {
        return new BeckonApparition(this);
    }
}
