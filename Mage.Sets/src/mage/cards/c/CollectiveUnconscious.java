package mage.cards.c;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class CollectiveUnconscious extends CardImpl {

    public CollectiveUnconscious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");


        // Draw a card for each creature you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)));
    }

    private CollectiveUnconscious(final CollectiveUnconscious card) {
        super(card);
    }

    @Override
    public CollectiveUnconscious copy() {
        return new CollectiveUnconscious(this);
    }
}
