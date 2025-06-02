package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonShiva extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature your opponents control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Tapped creatures your opponents control", xValue);

    public SummonShiva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Heavenly Strike -- Tap target creature an opponent controls. Put a stun counter on it.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new TapTargetEffect());
            ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                    .setText("Put a stun counter on it"));
            ability.addTarget(new TargetOpponentsCreaturePermanent());
            ability.withFlavorWord("Heavenly Strike");
        });

        // III -- Diamond Dust -- Draw a card for each tapped creature your opponents control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DrawCardSourceControllerEffect(xValue));
            ability.withFlavorWord("Diamond Dust");
            ability.addHint(hint);
        });

        this.addAbility(sagaAbility);
    }

    private SummonShiva(final SummonShiva card) {
        super(card);
    }

    @Override
    public SummonShiva copy() {
        return new SummonShiva(this);
    }
}
