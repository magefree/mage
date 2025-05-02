package mage.cards.s;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormbeaconBlade extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_ATTACKING_CREATURE,
            ComparisonType.MORE_THAN, 2, true
    );

    public StormbeaconBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 0)));

        // Whenever equipped creature attacks, draw a card if you control three or more attacking creatures.
        this.addAbility(new AttacksAttachedTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "draw a card if you control three or more attacking creatures"
        ), AttachmentType.EQUIPMENT, false));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private StormbeaconBlade(final StormbeaconBlade card) {
        super(card);
    }

    @Override
    public StormbeaconBlade copy() {
        return new StormbeaconBlade(this);
    }
}
