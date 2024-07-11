package mage.cards.s;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class StitchTogether extends CardImpl {

    public StitchTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Return target creature card from your graveyard to your hand.
        // Threshold - Return that card from your graveyard to the battlefield instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ReturnFromGraveyardToHandTargetEffect(),
                ThresholdCondition.instance, "Return target creature card from your graveyard to your hand. " +
                "<br>" + AbilityWord.THRESHOLD.formatWord() + "Return that card from your graveyard " +
                "to the battlefield instead if seven or more cards are in your graveyard."
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private StitchTogether(final StitchTogether card) {
        super(card);
    }

    @Override
    public StitchTogether copy() {
        return new StitchTogether(this);
    }
}
