package mage.cards.s;

import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SafePassage extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer("you and creatures you control",
            StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED,
            StaticFilters.FILTER_PLAYER_CONTROLLER);

    public SafePassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent all damage that would be dealt to you and creatures you control this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter));
    }

    public SafePassage(final SafePassage card) {
        super(card);
    }

    @Override
    public SafePassage copy() {
        return new SafePassage(this);
    }

}
