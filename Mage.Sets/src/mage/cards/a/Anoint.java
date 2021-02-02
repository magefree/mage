
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Anoint extends CardImpl {

    public Anoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Prevent the next 3 damage that would be dealt to target creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Anoint(final Anoint card) {
        super(card);
    }

    @Override
    public Anoint copy() {
        return new Anoint(this);
    }
}
