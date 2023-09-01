package mage.cards.c;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CloisteredYouth extends TransformingDoubleFacedCard {

    public CloisteredYouth(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{1}{W}",
                "Unholy Fiend",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HORROR}, "B"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 3);

        // At the beginning of your upkeep, you may transform Cloistered Youth.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, true
        ));

        // Unholy Fiend
        // At the beginning of your end step, you lose 1 life.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), TargetController.YOU, false
        ));
    }

    private CloisteredYouth(final CloisteredYouth card) {
        super(card);
    }

    @Override
    public CloisteredYouth copy() {
        return new CloisteredYouth(this);
    }
}
