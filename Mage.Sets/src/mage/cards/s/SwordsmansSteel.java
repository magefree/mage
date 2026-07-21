package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SwordsmansSteel extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT);
    private static final DynamicValue x2Value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT, 2);
    private static final Hint hint = new ValueHint("Equipment you control", xValue);

    public SwordsmansSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, draw a card for each Equipment you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(xValue)));

        // Equipped creature gets +2/+2 for each Equipment you control.
        this.addAbility(new SimpleStaticAbility(
            new BoostEquippedEffect(x2Value, x2Value, Duration.WhileOnBattlefield)
        ).addHint(hint));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private SwordsmansSteel(final SwordsmansSteel card) {
        super(card);
    }

    @Override
    public SwordsmansSteel copy() {
        return new SwordsmansSteel(this);
    }
}
