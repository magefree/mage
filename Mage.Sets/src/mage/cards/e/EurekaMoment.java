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
public final class EurekaMoment extends CardImpl {

    public EurekaMoment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{U}");

        // Draw two cards. You may put a land card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A));
    }

    private EurekaMoment(final EurekaMoment card) {
        super(card);
    }

    @Override
    public EurekaMoment copy() {
        return new EurekaMoment(this);
    }
}
