
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class GitaxianProbe extends CardImpl {

    public GitaxianProbe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U/P}");

        // Look at target player's hand.
        this.getSpellAbility().addEffect(new LookAtTargetPlayerHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

    }

    private GitaxianProbe(final GitaxianProbe card) {
        super(card);
    }

    @Override
    public GitaxianProbe copy() {
        return new GitaxianProbe(this);
    }

}
