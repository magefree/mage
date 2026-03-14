package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AvacynianMissionaries extends TransformingDoubleFacedCard {

    public AvacynianMissionaries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{3}{W}",
                "Lunarch Inquisitors",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "W");
        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(4, 4);

        // At the beginning of your end step, if Avacynian Missionaries is equipped, transform it.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU, new TransformSourceEffect().setText("transform it"),
                false, EquippedSourceCondition.instance));

        // Lunarch Inquisitors
        // When this creature transforms into Lunarch Inquisitors, you may exile another target creature until Lunarch Inquisitors leaves the battlefield.
        Ability ability = new TransformIntoSourceTriggeredAbility(new ExileUntilSourceLeavesEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.getRightHalfCard().addAbility(ability);
    }

    private AvacynianMissionaries(final AvacynianMissionaries card) {
        super(card);
    }

    @Override
    public AvacynianMissionaries copy() {
        return new AvacynianMissionaries(this);
    }
}
