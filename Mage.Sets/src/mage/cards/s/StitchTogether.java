
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class StitchTogether extends CardImpl {

    public StitchTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Return target creature card from your graveyard to your hand.
        // Threshold - Return that card from your graveyard to the battlefield instead if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new ReturnFromGraveyardToHandTargetEffect(),
                new CardsInControllerGraveyardCondition(7),
                "Return target creature card from your graveyard to your hand. <br/><br/><i>Threshold</i> &mdash; Return that card from your graveyard to the battlefield instead if seven or more cards are in your graveyard.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    public StitchTogether(final StitchTogether card) {
        super(card);
    }

    @Override
    public StitchTogether copy() {
        return new StitchTogether(this);
    }
}
