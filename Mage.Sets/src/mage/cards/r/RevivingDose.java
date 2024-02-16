
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class RevivingDose extends CardImpl {

    public RevivingDose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RevivingDose(final RevivingDose card) {
        super(card);
    }

    @Override
    public RevivingDose copy() {
        return new RevivingDose(this);
    }
}
