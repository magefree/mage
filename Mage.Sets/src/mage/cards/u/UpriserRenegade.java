package mage.cards.u;

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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UpriserRenegade extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other modified creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ModifiedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);
    private static final Hint hint = new ValueHint(
            "Other modified creatures you control", new PermanentsOnBattlefieldCount(filter)
    );

    public UpriserRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Upriser Renegade gets +2/+0 for each other modified creature you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(hint));
    }

    private UpriserRenegade(final UpriserRenegade card) {
        super(card);
    }

    @Override
    public UpriserRenegade copy() {
        return new UpriserRenegade(this);
    }
}
