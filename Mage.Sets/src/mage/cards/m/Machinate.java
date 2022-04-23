package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Alexsandr0x
 */
public final class Machinate extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS, null);

    public Machinate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Look at the top X cards of your library, where X is the number of artifacts you control. Put one of those cards into your hand and the rest on the bottom of your library in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(xValue, 1, PutCards.HAND, PutCards.BOTTOM_ANY);
        effect.setText("look at the top X cards of your library, where X is the number of artifacts you control. " +
                "Put one of those cards into your hand and the rest on the bottom of your library in any order");
        this.getSpellAbility().addEffect(effect);
    }

    private Machinate(final Machinate card) {
        super(card);
    }

    @Override
    public Machinate copy() {
        return new Machinate(this);
    }
}
