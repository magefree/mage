
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class RakshasasSecret extends CardImpl {

    public RakshasasSecret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        Effect effect = new PutTopCardOfLibraryIntoGraveControllerEffect(2);
        effect.setText("Put the top two cards of your library into your graveyard");
        this.getSpellAbility().addEffect(effect);
    }

    public RakshasasSecret(final RakshasasSecret card) {
        super(card);
    }

    @Override
    public RakshasasSecret copy() {
        return new RakshasasSecret(this);
    }
}
