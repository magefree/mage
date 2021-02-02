
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class ClutchOfTheUndercity extends CardImpl {

    public ClutchOfTheUndercity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}{B}");


        // Return target permanent to its owner's hand. Its controller loses 3 life.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent());
        // Transmute {1}{U}{B}
        this.addAbility(new TransmuteAbility("{1}{U}{B}"));
    }

    private ClutchOfTheUndercity(final ClutchOfTheUndercity card) {
        super(card);
    }

    @Override
    public ClutchOfTheUndercity copy() {
        return new ClutchOfTheUndercity(this);
    }
}
