package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ImaryllElfhameElite extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("the number of other Elves you control");

    static {
        filter.add(SubType.ELF.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other elves you control", xValue);

    public ImaryllElfhameElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Imaryll, Elfhame Elite attacks, it gets +X/+X until end of turn, where X is the number of other Elves you control.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("it gets +X/+X until end of turn, where X is the number of other Elves you control")
        ).addHint(hint));

        // You may have Imaryll assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    private ImaryllElfhameElite(final ImaryllElfhameElite card) {
        super(card);
    }

    @Override
    public ImaryllElfhameElite copy() {
        return new ImaryllElfhameElite(this);
    }
}
