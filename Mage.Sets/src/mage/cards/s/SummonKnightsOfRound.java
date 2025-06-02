package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WaylayToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonKnightsOfRound extends CardImpl {

    public SummonKnightsOfRound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{6}{W}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after V.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_V);

        // I, II, III, IV -- Create three 2/2 white Knight creature tokens.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_IV,
                new CreateTokenEffect(new WaylayToken(), 3)
        );

        // V -- Ultimate End -- Other creatures you control get +2/+2 until end of turn. Put an indestructible counter on each of them.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_V, ability -> {
            ability.addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn, true));
            ability.addEffect(new AddCountersAllEffect(
                    CounterType.INDESTRUCTIBLE.createInstance(),
                    StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES
            ).setText("Put an indestructible counter on each of them"));
            ability.withFlavorWord("Ultimate End");
        });
        this.addAbility(sagaAbility);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private SummonKnightsOfRound(final SummonKnightsOfRound card) {
        super(card);
    }

    @Override
    public SummonKnightsOfRound copy() {
        return new SummonKnightsOfRound(this);
    }
}
