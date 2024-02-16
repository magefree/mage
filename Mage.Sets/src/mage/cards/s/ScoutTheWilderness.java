package mage.cards.s;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScoutTheWilderness extends CardImpl {

    public ScoutTheWilderness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle. If this spell was kicked, create two 1/1 white Soldier creature tokens.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SoldierToken(), 2), KickedCondition.ONCE,
                "If this spell was kicked, create two 1/1 white Soldier creature tokens"
        ));
    }

    private ScoutTheWilderness(final ScoutTheWilderness card) {
        super(card);
    }

    @Override
    public ScoutTheWilderness copy() {
        return new ScoutTheWilderness(this);
    }
}
