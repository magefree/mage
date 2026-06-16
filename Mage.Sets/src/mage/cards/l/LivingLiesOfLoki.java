package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LivingLiesOfLoki extends CardImpl {

    private static final FilterPermanent filter
        = new FilterControlledPermanent(SubType.ILLUSION, "other Illusion you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint(
        "Other Illusions you control", new PermanentsOnBattlefieldCount(filter)
    );

    public LivingLiesOfLoki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // This creature gets +1/+0 for each other Illusion you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
            xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(hint));

        // When this creature dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private LivingLiesOfLoki(final LivingLiesOfLoki card) {
        super(card);
    }

    @Override
    public LivingLiesOfLoki copy() {
        return new LivingLiesOfLoki(this);
    }
}
