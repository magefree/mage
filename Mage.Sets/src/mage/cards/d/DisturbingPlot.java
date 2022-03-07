
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class DisturbingPlot extends CardImpl {

    public DisturbingPlot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Return target creature card from a graveyard to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));

    }

    private DisturbingPlot(final DisturbingPlot card) {
        super(card);
    }

    @Override
    public DisturbingPlot copy() {
        return new DisturbingPlot(this);
    }
}
