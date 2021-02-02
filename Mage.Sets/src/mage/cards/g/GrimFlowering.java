package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class GrimFlowering extends CardImpl {

    public GrimFlowering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Draw a card for each creature card in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)));
    }

    private GrimFlowering(final GrimFlowering card) {
        super(card);
    }

    @Override
    public GrimFlowering copy() {
        return new GrimFlowering(this);
    }
}
