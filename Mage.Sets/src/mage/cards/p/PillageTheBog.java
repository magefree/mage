package mage.cards.p;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PillageTheBog extends CardImpl {

    static final DynamicValue value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);
    static final DynamicValue xValue = new MultipliedValue(value, 2);
    private static final Hint hint = new ValueHint("lands you control", value);

    public PillageTheBog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Look at the top X cards of your library, where X is twice the number of lands you control. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(
                new LookLibraryAndPickControllerEffect(xValue, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM)
                        .setText("Look at the top X cards of your library, where X is twice the number of lands you control. "
                                + "Put one of them into your hand and the rest on the bottom of your library in a random order."));
        this.getSpellAbility().addHint(hint);

        // Plot {1}{B}{G}
        this.addAbility(new PlotAbility("{1}{B}{G}"));
    }

    private PillageTheBog(final PillageTheBog card) {
        super(card);
    }

    @Override
    public PillageTheBog copy() {
        return new PillageTheBog(this);
    }
}