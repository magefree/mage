
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DepthsOfDesire extends CardImpl {

    public DepthsOfDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Return target creature to its owner's hand. Create a colorless Treasure token with "{t}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private DepthsOfDesire(final DepthsOfDesire card) {
        super(card);
    }

    @Override
    public DepthsOfDesire copy() {
        return new DepthsOfDesire(this);
    }
}
