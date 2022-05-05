
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ResourcefulReturn extends CardImpl {

    public ResourcefulReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return target creature card from your graveyard to your hand. If you control an artifact, draw a card.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT),
                "If you control an artifact, draw a card"));

    }

    private ResourcefulReturn(final ResourcefulReturn card) {
        super(card);
    }

    @Override
    public ResourcefulReturn copy() {
        return new ResourcefulReturn(this);
    }
}
