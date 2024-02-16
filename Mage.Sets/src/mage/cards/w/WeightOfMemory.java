
package mage.cards.w;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author rscoates
 */
public final class WeightOfMemory extends CardImpl {

    public WeightOfMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        // Target player puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
    }

    private WeightOfMemory(final WeightOfMemory card) {
        super(card);
    }

    @Override
    public WeightOfMemory copy() {
        return new WeightOfMemory(this);
    }
}
