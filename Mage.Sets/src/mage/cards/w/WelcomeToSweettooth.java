package mage.cards.w;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WelcomeToSweettooth extends CardImpl {

    private static final DynamicValue numberFood =
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_FOOD);
    private static final DynamicValue xValue = new IntPlusDynamicValue(1, numberFood);

    private static final Hint hint = new ValueHint("Controlled Foods", numberFood);

    public WelcomeToSweettooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);
        // I -- Create a 1/1 white Human creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new HumanToken()));

        // II -- Create a Food token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new FoodToken()));

        // III -- Put X +1/+1 counters on target creature you control, where X is one plus the number of Foods you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), xValue)
                        .setText("put X +1/+1 counters on target creature you control, "
                                + "where X is one plus the number of Foods you control"),
                new TargetControlledCreaturePermanent()
        );
        sagaAbility.addHint(hint);

        this.addAbility(sagaAbility);
    }

    private WelcomeToSweettooth(final WelcomeToSweettooth card) {
        super(card);
    }

    @Override
    public WelcomeToSweettooth copy() {
        return new WelcomeToSweettooth(this);
    }
}
