
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class ParanoidDelusions extends CardImpl {

    public ParanoidDelusions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{B}");


        // Target player puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private ParanoidDelusions(final ParanoidDelusions card) {
        super(card);
    }

    @Override
    public ParanoidDelusions copy() {
        return new ParanoidDelusions(this);
    }
}
