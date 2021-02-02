
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public final class ReapingTheGraves extends CardImpl {

    public ReapingTheGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return target creature card from your graveyard to your hand."));
        // Storm
        this.addAbility(new StormAbility());
    }

    private ReapingTheGraves(final ReapingTheGraves card) {
        super(card);
    }

    @Override
    public ReapingTheGraves copy() {
        return new ReapingTheGraves(this);
    }
}
