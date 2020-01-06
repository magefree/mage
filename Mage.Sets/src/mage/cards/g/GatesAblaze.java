package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatesAblaze extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("the number of Gates you control");

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GatesAblaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gates Ablaze deals X damage to each creature, where X is the number of Gates you control.
        this.getSpellAbility().addEffect(new DamageAllEffect(xValue, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private GatesAblaze(final GatesAblaze card) {
        super(card);
    }

    @Override
    public GatesAblaze copy() {
        return new GatesAblaze(this);
    }
}
