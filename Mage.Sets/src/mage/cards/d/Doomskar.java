package mage.cards.d;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Doomskar extends CardImpl {

    public Doomskar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // Foretell {1}{W}{W}
        this.addAbility(new ForetellAbility(this, "{1}{W}{W}"));
    }

    private Doomskar(final Doomskar card) {
        super(card);
    }

    @Override
    public Doomskar copy() {
        return new Doomskar(this);
    }
}
