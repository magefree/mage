package mage.cards.r;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.NoFlyingSpiritWhiteToken;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevivalOfTheAncestors extends CardImpl {

    public RevivalOfTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create three 1/1 white Spirit creature tokens.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new NoFlyingSpiritWhiteToken(), 3)
        );

        // II -- Distribute three +1/+1 counters among one, two, or three target creatures you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DistributeCountersEffect(CounterType.P1P1),
                new TargetPermanentAmount(3, 1, StaticFilters.FILTER_CONTROLLED_CREATURES)
        );

        // III -- Creatures you control gain trample and lifelink until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("creatures you control gain trample"),
                new GainAbilityControlledEffect(
                        LifelinkAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("and lifelink until end of turn"));
        this.addAbility(sagaAbility);
    }

    private RevivalOfTheAncestors(final RevivalOfTheAncestors card) {
        super(card);
    }

    @Override
    public RevivalOfTheAncestors copy() {
        return new RevivalOfTheAncestors(this);
    }
}
