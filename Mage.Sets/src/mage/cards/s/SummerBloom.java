
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class SummerBloom extends CardImpl {

    public SummerBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // You may play up to three additional lands this turn.
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(3, Duration.EndOfTurn));
    }

    private SummerBloom(final SummerBloom card) {
        super(card);
    }

    @Override
    public SummerBloom copy() {
        return new SummerBloom(this);
    }
}
