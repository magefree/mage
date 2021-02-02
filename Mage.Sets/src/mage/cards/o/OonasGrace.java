
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class OonasGrace extends CardImpl {

    public OonasGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Target player draws a card.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private OonasGrace(final OonasGrace card) {
        super(card);
    }

    @Override
    public OonasGrace copy() {
        return new OonasGrace(this);
    }
}
