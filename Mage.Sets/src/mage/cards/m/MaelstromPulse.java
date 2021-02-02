

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllNamedPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MaelstromPulse extends CardImpl {

    public MaelstromPulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{G}");
        

        // Destroy target nonland permanent and all other permanents with the same name as that permanent.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new DestroyAllNamedPermanentsEffect());
    }

    private MaelstromPulse(final MaelstromPulse card) {
        super(card);
    }

    @Override
    public MaelstromPulse copy() {
        return new MaelstromPulse(this);
    }

}
