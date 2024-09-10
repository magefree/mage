package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author Susucr
 */
public final class WastelandSurvivalGuideToken extends TokenImpl {

    private static final DynamicValue xValue = new CountersCount(CounterType.QUEST, StaticFilters.FILTER_CONTROLLED_PERMANENT);
    private static final Hint hint = new ValueHint("Total quest counter among permanents you control", xValue);

    public WastelandSurvivalGuideToken() {
        super("Wasteland Survival Guide", "colorless Equipment artifact token named Wasteland Survival Guide "
                + "with \"Equipped creature gets +1/+1 for each quest counter among permanents you control\" and equip {1}");
        this.cardType.add(CardType.ARTIFACT);
        this.subtype.add(SubType.EQUIPMENT);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(xValue, xValue)).addHint(hint));
        this.addAbility(new EquipAbility(1, false));
    }

    private WastelandSurvivalGuideToken(final WastelandSurvivalGuideToken token) {
        super(token);
    }

    public WastelandSurvivalGuideToken copy() {
        return new WastelandSurvivalGuideToken(this);
    }

}
