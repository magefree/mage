
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class Bandage extends CardImpl {

    public Bandage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Bandage(final Bandage card) {
        super(card);
    }

    @Override
    public Bandage copy() {
        return new Bandage(this);
    }
}
