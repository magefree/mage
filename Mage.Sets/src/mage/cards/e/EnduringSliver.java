package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringSliver extends CardImpl {

    public EnduringSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Outlast {2}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{2}")));

        // Other sliver creatures you control have outlast {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new OutlastAbility(new ManaCostsImpl<>("{2}")), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, true
        ).setText("Other Sliver creatures you control have outlast {2}.")));
    }

    private EnduringSliver(final EnduringSliver card) {
        super(card);
    }

    @Override
    public EnduringSliver copy() {
        return new EnduringSliver(this);
    }
}
