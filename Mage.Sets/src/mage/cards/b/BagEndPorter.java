package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BagEndPorter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("legendary creatures you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public BagEndPorter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bag End Porter attacks, it gets +X/+X until end of turn, where X is the number of legendary creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)));
    }

    private BagEndPorter(final BagEndPorter card) {
        super(card);
    }

    @Override
    public BagEndPorter copy() {
        return new BagEndPorter(this);
    }
}
