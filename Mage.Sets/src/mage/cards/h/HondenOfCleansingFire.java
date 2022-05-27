

package mage.cards.h;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfCleansingFire extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SHRINE)
    );
    private static final Hint hint = new ValueHint("Shrines you control", xValue);

    public HondenOfCleansingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);


        // At the beginning of your upkeep, you gain 2 life for each Shrine you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(new MultipliedValue(xValue, 2)), TargetController.YOU, false).addHint(hint));
    }

    private HondenOfCleansingFire(final HondenOfCleansingFire card) {
        super(card);
    }

    @Override
    public HondenOfCleansingFire copy() {
        return new HondenOfCleansingFire(this);
    }

}
