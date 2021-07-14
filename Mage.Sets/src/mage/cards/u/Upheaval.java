package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author cbt33
 */
public final class Upheaval extends CardImpl {

    public Upheaval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENTS));
    }

    private Upheaval(final Upheaval card) {
        super(card);
    }

    @Override
    public Upheaval copy() {
        return new Upheaval(this);
    }
}
