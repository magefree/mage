
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class BrainFreeze extends CardImpl {

    public BrainFreeze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Target player puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        // Storm
        this.addAbility(new StormAbility());
    }

    private BrainFreeze(final BrainFreeze card) {
        super(card);
    }

    @Override
    public BrainFreeze copy() {
        return new BrainFreeze(this);
    }
}
