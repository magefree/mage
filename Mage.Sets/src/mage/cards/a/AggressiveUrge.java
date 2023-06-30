
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class AggressiveUrge extends CardImpl {

    public AggressiveUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AggressiveUrge(final AggressiveUrge card) {
        super(card);
    }

    @Override
    public AggressiveUrge copy() {
        return new AggressiveUrge(this);
    }
}
