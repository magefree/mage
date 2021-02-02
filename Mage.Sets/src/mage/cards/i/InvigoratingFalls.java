package mage.cards.i;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class InvigoratingFalls extends CardImpl {

    public InvigoratingFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // You gain life equal to the number of creature cards in all graveyards.
        Effect effect = new GainLifeEffect(new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE));
        effect.setText("You gain life equal to the number of creature cards in all graveyards.");
        this.getSpellAbility().addEffect(effect);
    }

    private InvigoratingFalls(final InvigoratingFalls card) {
        super(card);
    }

    @Override
    public InvigoratingFalls copy() {
        return new InvigoratingFalls(this);
    }
}
