package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmbraceTheParadox extends CardImpl {

    public EmbraceTheParadox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{U}");

        // Draw three cards. You may put a land card from your hand onto the battlefield tapped.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_LAND_A, false, true
        ));
    }

    private EmbraceTheParadox(final EmbraceTheParadox card) {
        super(card);
    }

    @Override
    public EmbraceTheParadox copy() {
        return new EmbraceTheParadox(this);
    }
}
