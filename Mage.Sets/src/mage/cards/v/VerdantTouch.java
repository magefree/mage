
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class VerdantTouch extends CardImpl {
    
    public VerdantTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Target land becomes a 2/2 creature that's still a land.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(2, 2), false, true, Duration.Custom));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public VerdantTouch(final VerdantTouch card) {
        super(card);
    }

    @Override
    public VerdantTouch copy() {
        return new VerdantTouch(this);
    }
}