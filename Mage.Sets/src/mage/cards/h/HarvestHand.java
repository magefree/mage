package mage.cards.h;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ReturnSourceToBattlefieldTransformedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author halljared
 */
public final class HarvestHand extends TransformingDoubleFacedCard {

    private static final Condition condition = new EquippedHasSubtypeCondition(SubType.HUMAN);

    public HarvestHand(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.SCARECROW}, "{3}",
                "Scrounged Scythe",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, ""
        );
        this.getLeftHalfCard().setPT(2, 2);

        // When Harvest Hand dies, return it to the battlefield transformed under your control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new ReturnSourceToBattlefieldTransformedEffect(false)));

        // Scrounged Scythe
        // Equipped creature gets +1/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // As long as equipped creature is a Human, it has menace.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.EQUIPMENT),
                condition, "As long as equipped creature is a Human, it has menace. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"
        )));

        // Equip {2}
        this.getRightHalfCard().addAbility(new EquipAbility(2, false));
    }

    private HarvestHand(final HarvestHand card) {
        super(card);
    }

    @Override
    public HarvestHand copy() {
        return new HarvestHand(this);
    }
}
