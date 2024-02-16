
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class PilferedPlans extends CardImpl {

    public PilferedPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{B}");


        // Target player puts the top two cards of their library into their graveyard. Draw two cards.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    private PilferedPlans(final PilferedPlans card) {
        super(card);
    }

    @Override
    public PilferedPlans copy() {
        return new PilferedPlans(this);
    }
}
