package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CongregationGryff extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNT));
    private static final Hint hint = new ValueHint("Number of mounts you control", xValue);

    public CongregationGryff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.HIPPOGRIFF);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Congregation Gryff attacks while saddled, it gets +X/+X until end of turn, where X is the number of Mounts you control.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("it gets +X/+X until end of turn, where X is the number of Mounts you control")
        ).addHint(hint));

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private CongregationGryff(final CongregationGryff card) {
        super(card);
    }

    @Override
    public CongregationGryff copy() {
        return new CongregationGryff(this);
    }
}
