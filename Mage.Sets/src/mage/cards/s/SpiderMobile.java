package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SpiderMobile extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Spider you control");

    static {
        filter.add(SubType.SPIDER.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public SpiderMobile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this Vehicle attacks or blocks, it gets +1/+1 until end of turn for each Spider you control.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn),
                false
        ));

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private SpiderMobile(final SpiderMobile card) {
        super(card);
    }

    @Override
    public SpiderMobile copy() {
        return new SpiderMobile(this);
    }
}
