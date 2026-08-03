package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class DownDownToGoblinTown extends CardImpl {

    public DownDownToGoblinTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I,
            new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND),
            new TargetOpponent()
        );

        // II -- Amass Goblins 1.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new AmassEffect(1, SubType.GOBLIN));

        // III, IV -- Target opponent loses 1 life and you gain 1 life.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_IV,
            new Effects(
                new LoseLifeTargetEffect(1),
                new GainLifeEffect(1).concatBy("and")
            ),
            new TargetOpponent()
        );

        this.addAbility(sagaAbility);
    }

    private DownDownToGoblinTown(final DownDownToGoblinTown card) {
        super(card);
    }

    @Override
    public DownDownToGoblinTown copy() {
        return new DownDownToGoblinTown(this);
    }
}
