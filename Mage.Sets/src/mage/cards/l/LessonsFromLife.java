package mage.cards.l;

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
public final class LessonsFromLife extends CardImpl {

    public LessonsFromLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{U}");

        // Draw three cards. You may put a land card from your hand onto the battlefield tapped.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A));
    }

    private LessonsFromLife(final LessonsFromLife card) {
        super(card);
    }

    @Override
    public LessonsFromLife copy() {
        return new LessonsFromLife(this);
    }
}
