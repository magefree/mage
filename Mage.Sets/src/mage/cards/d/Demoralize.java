
package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;


/**
 *
 * @author LevelX2
 */
public final class Demoralize extends CardImpl {

    public Demoralize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // All creatures gain menace until end of turn. (They can't be blocked except by two or more creatures.)
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("All creatures gain menace until end of turn. <i>(They can't be blocked except by two or more creatures.)</i>"));

        // Threshold — If seven or more cards are in your graveyard, creatures can't block this turn.
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                    new AddContinuousEffectToGame(new CantBlockAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn)),
                    new CardsInControllerGraveyardCondition(7),
                    "<br/><br/><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, creatures can't block this turn"
                ));
    }


    private Demoralize(final Demoralize card) {
        super(card);
    }

    @Override
    public Demoralize copy() {
        return new Demoralize(this);
    }
}