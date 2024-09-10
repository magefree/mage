
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class LastCaress extends CardImpl {

    public LastCaress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private LastCaress(final LastCaress card) {
        super(card);
    }

    @Override
    public LastCaress copy() {
        return new LastCaress(this);
    }
}
