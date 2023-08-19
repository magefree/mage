
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class Opportunity extends CardImpl {

    public Opportunity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Target player draws four cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Opportunity(final Opportunity card) {
        super(card);
    }

    @Override
    public Opportunity copy() {
        return new Opportunity(this);
    }
}
