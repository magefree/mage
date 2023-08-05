package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsNotSetCondition;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CrownOfGondor extends CardImpl {

    private final static DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public CrownOfGondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each creature you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(xValue, xValue)));

        // When a legendary creature enters the battlefield under your control, if there is no monarch, you become the monarch.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        new BecomesMonarchSourceEffect(),
                        StaticFilters.FILTER_CREATURE_LEGENDARY
                ),
                MonarchIsNotSetCondition.instance,
                "When a legendary creature enters the battlefield under your control, if there is no monarch, you become the monarch."
        ));

        // Equip {4}. This ability costs {3} less to activate if you're the monarch.
        EquipAbility equip = new EquipAbility(4, false);
        equip.setCostReduceText("This ability costs {3} less to activate if you're the monarch");
        equip.setCostAdjuster(CrownOfGondorAdjuster.instance);
        this.addAbility(equip);
    }

    private CrownOfGondor(final CrownOfGondor card) {
        super(card);
    }

    @Override
    public CrownOfGondor copy() {
        return new CrownOfGondor(this);
    }
}

enum CrownOfGondorAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (MonarchIsSourceControllerCondition.instance.apply(game, ability)) {
            CardUtil.reduceCost(ability, 3);
        }
    }
}
