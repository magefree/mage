package mage.cards.e;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EndlessDetour extends CardImpl {

    public EndlessDetour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}{U}");

        // The owner of target spell, nonland permanent, or card in a graveyard puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyardBattlefieldOrStack(
                1, 1, StaticFilters.FILTER_CARD, StaticFilters.FILTER_PERMANENT_NON_LAND,
                StaticFilters.FILTER_SPELL, "spell, nonland permanent, or card in a graveyard"
        ));
    }

    private EndlessDetour(final EndlessDetour card) {
        super(card);
    }

    @Override
    public EndlessDetour copy() {
        return new EndlessDetour(this);
    }
}
