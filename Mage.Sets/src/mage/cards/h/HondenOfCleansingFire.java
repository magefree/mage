package mage.cards.h;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfCleansingFire extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ShrinesYouControlCount.FOR_EACH, 2);

    public HondenOfCleansingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your upkeep, you gain 2 life for each Shrine you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(xValue)).addHint(ShrinesYouControlCount.getHint()));
    }

    private HondenOfCleansingFire(final HondenOfCleansingFire card) {
        super(card);
    }

    @Override
    public HondenOfCleansingFire copy() {
        return new HondenOfCleansingFire(this);
    }

}
