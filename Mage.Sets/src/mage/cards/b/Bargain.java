
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class Bargain extends CardImpl {

    public Bargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Target opponent draws a card.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetOpponent());
        
        // You gain 7 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(7).concatBy("<br>"));
    }

    private Bargain(final Bargain card) {
        super(card);
    }

    @Override
    public Bargain copy() {
        return new Bargain(this);
    }
}
