
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author dustinconrad
 */
public final class Castigate extends CardImpl {

    public Castigate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it and exile that card.
        this.getSpellAbility().addEffect(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Castigate(final Castigate card) {
        super(card);
    }

    @Override
    public Castigate copy() {
        return new Castigate(this);
    }
}
