
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class AnguishedUnmaking extends CardImpl {

    public AnguishedUnmaking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{B}");

        // Exile target nonland permanent. You lose 3 life.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());
        getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));
    }

    private AnguishedUnmaking(final AnguishedUnmaking card) {
        super(card);
    }

    @Override
    public AnguishedUnmaking copy() {
        return new AnguishedUnmaking(this);
    }
}
