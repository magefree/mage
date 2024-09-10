
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class ThassasBounty extends CardImpl {

    public ThassasBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");


        // Draw three cards. Target player puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ThassasBounty(final ThassasBounty card) {
        super(card);
    }

    @Override
    public ThassasBounty copy() {
        return new ThassasBounty(this);
    }
}
