package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimlisAxe extends CardImpl {

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_LEGENDARY);

    public GimlisAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 0)));

        // As long as equipped creature is legendary, it has menace.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(new MenaceAbility(false), AttachmentType.EQUIPMENT),
                condition, "As long as equipped creature is legendary, it has menace. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private GimlisAxe(final GimlisAxe card) {
        super(card);
    }

    @Override
    public GimlisAxe copy() {
        return new GimlisAxe(this);
    }
}
