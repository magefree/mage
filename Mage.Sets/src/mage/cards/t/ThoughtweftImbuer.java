package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtweftImbuer extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.KITHKIN), null
    );
    private static final Hint hint = new ValueHint("Kithkin you control", xValue);

    public ThoughtweftImbuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of Kithkin you control.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new BoostTargetEffect(xValue, xValue), true, false
        ).addHint(hint));
    }

    private ThoughtweftImbuer(final ThoughtweftImbuer card) {
        super(card);
    }

    @Override
    public ThoughtweftImbuer copy() {
        return new ThoughtweftImbuer(this);
    }
}
