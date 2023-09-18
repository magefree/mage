package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class AzusasManyJourneys extends CardImpl {

    public AzusasManyJourneys(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.l.LikenessOfTheSeeker.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — You may play an additional land this turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        // II — You gain 3 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new GainLifeEffect(3));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private AzusasManyJourneys(final AzusasManyJourneys card) {
        super(card);
    }

    @Override
    public AzusasManyJourneys copy() {
        return new AzusasManyJourneys(this);
    }
}
