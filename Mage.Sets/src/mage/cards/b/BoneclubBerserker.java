package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneclubBerserker extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.GOBLIN, "other Goblin you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
            "Other Goblins you control", new PermanentsOnBattlefieldCount(filter)
    );

    public BoneclubBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // This creature gets +2/+0 for each other Goblin you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(hint));
    }

    private BoneclubBerserker(final BoneclubBerserker card) {
        super(card);
    }

    @Override
    public BoneclubBerserker copy() {
        return new BoneclubBerserker(this);
    }
}
