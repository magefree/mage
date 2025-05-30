package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonIxion extends CardImpl {

    public SummonIxion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Aerospark -- Exile target creature an opponent controls until this Saga leaves the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new ExileUntilSourceLeavesEffect());
            ability.addTarget(new TargetOpponentsCreaturePermanent());
            ability.withFlavorWord("Aerospark");
        });

        // II, III - Put a +1/+1 counter on each of up to two target creatures you control. You gain 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
            ability.addEffect(new GainLifeEffect(2));
            ability.addTarget(new TargetControlledCreaturePermanent(0, 2));
        });
        this.addAbility(sagaAbility);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private SummonIxion(final SummonIxion card) {
        super(card);
    }

    @Override
    public SummonIxion copy() {
        return new SummonIxion(this);
    }
}
