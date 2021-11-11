package mage.cards.d;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class DueRespect extends CardImpl {

    public DueRespect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Permanents enter the battlefield tapped this turn.
        this.getSpellAbility().addEffect(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_PERMANENTS, Duration.EndOfTurn
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private DueRespect(final DueRespect card) {
        super(card);
    }

    @Override
    public DueRespect copy() {
        return new DueRespect(this);
    }
}
