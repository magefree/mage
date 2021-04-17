package mage.cards.o;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class OneWithTheMachine extends CardImpl {

    public OneWithTheMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw cards equal to the highest converted mana cost among artifacts you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(
                new HighestManaValueCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT)
        ).setText("Draw cards equal to the highest mana value among artifacts you control"));
    }

    private OneWithTheMachine(final OneWithTheMachine card) {
        super(card);
    }

    @Override
    public OneWithTheMachine copy() {
        return new OneWithTheMachine(this);
    }
}
