
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class SongOfFreyalise extends CardImpl {

    public SongOfFreyalise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Until your next turn, creatures you control gain "T: Add one mana of any color."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new GainAbilityControlledEffect(new AnyColorManaAbility(), Duration.UntilYourNextTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                        .setText("Until your next turn, creatures you control gain \"{T}: Add one mana of any color.\"")
        );

        // III — Put a +1/+1 counter on each creature you control. Those creatures gain vigilance, trample, and indestructible until end of turn.
        Effects effects = new Effects();
        effects.add(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURES));
        effects.add(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("Those creatures gain vigilance"));
        effects.add(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText(", trample"));
        effects.add(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("and indestructible until end of turn"));
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III, effects);
        this.addAbility(sagaAbility);
    }

    private SongOfFreyalise(final SongOfFreyalise card) {
        super(card);
    }

    @Override
    public SongOfFreyalise copy() {
        return new SongOfFreyalise(this);
    }
}
