
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public final class EndlessObedience extends CardImpl {

    public EndlessObedience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Put target creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
    }

    private EndlessObedience(final EndlessObedience card) {
        super(card);
    }

    @Override
    public EndlessObedience copy() {
        return new EndlessObedience(this);
    }
}
