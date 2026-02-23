package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LeonardoBigBrother extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES, 1);

    public LeonardoBigBrother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Sneak {W}
        this.addAbility(new SneakAbility(this, "{W}"));

        // Leonardo gets +1/+0 for each other creature you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, StaticValue.get(0), Duration.WhileOnBattlefield)));
    }

    private LeonardoBigBrother(final LeonardoBigBrother card) {
        super(card);
    }

    @Override
    public LeonardoBigBrother copy() {
        return new LeonardoBigBrother(this);
    }
}
