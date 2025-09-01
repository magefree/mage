package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
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

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ScroungedScythe extends CardImpl {

    private static final Condition condition = new EquippedHasSubtypeCondition(SubType.HUMAN);

    public ScroungedScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.subtype.add(SubType.EQUIPMENT);

        this.nightCard = true;

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // As long as equipped creature is a Human, it has menace.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(new MenaceAbility(false), AttachmentType.EQUIPMENT),
                condition, "As long as equipped creature is a Human, it has menace. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private ScroungedScythe(final ScroungedScythe card) {
        super(card);
    }

    @Override
    public ScroungedScythe copy() {
        return new ScroungedScythe(this);
    }
}
