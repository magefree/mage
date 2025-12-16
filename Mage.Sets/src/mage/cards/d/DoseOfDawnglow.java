package mage.cards.d;

import mage.abilities.condition.common.IsMainPhaseCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.BlightControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoseOfDawnglow extends CardImpl {

    public DoseOfDawnglow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield. Then if it isn't your main phase, blight 2.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BlightControllerEffect(2), IsMainPhaseCondition.NOT_YOURS
        ).concatBy("Then"));
    }

    private DoseOfDawnglow(final DoseOfDawnglow card) {
        super(card);
    }

    @Override
    public DoseOfDawnglow copy() {
        return new DoseOfDawnglow(this);
    }
}
