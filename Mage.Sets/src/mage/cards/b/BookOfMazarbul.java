package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BookOfMazarbul extends CardImpl {

    public BookOfMazarbul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Amass Orcs 1.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new AmassEffect(1, SubType.ORC)
        );

        // II -- Amass Orcs 2.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new AmassEffect(2, SubType.ORC)
                        .setText("amass Orcs 2")
        );

        // III -- Creatures you control get +1/+0 and gain menace until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new BoostControlledEffect(
                        1, 0, Duration.EndOfTurn
                ).setText("creatures you control get +1/+0"),
                new GainAbilityControlledEffect(
                        new MenaceAbility(false), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("and gain menace until end of turn")
        );

        this.addAbility(sagaAbility);
    }

    private BookOfMazarbul(final BookOfMazarbul card) {
        super(card);
    }

    @Override
    public BookOfMazarbul copy() {
        return new BookOfMazarbul(this);
    }
}
