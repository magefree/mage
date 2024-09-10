
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class FinalJudgment extends CardImpl {

    public FinalJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Exile all creatures.
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private FinalJudgment(final FinalJudgment card) {
        super(card);
    }

    @Override
    public FinalJudgment copy() {
        return new FinalJudgment(this);
    }
}
