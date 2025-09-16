package mage.cards.a;

import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class AzusasManyJourneys extends TransformingDoubleFacedCard {

    public AzusasManyJourneys(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{G}",
                "Likeness of the Seeker",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MONK}, "G");

        this.getRightHalfCard().setPT(3, 3);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — You may play an additional land this turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        // II — You gain 3 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new GainLifeEffect(3));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Likeness of the Seeker
        // Whenever Likeness of the Seeker becomes blocked, untap up to three lands you control.
        this.getRightHalfCard().addAbility(new BecomesBlockedSourceTriggeredAbility(new UntapLandsEffect(3, true, true), false));
    }

    private AzusasManyJourneys(final AzusasManyJourneys card) {
        super(card);
    }

    @Override
    public AzusasManyJourneys copy() {
        return new AzusasManyJourneys(this);
    }
}
