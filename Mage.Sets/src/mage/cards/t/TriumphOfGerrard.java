package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TriumphOfGerrard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creture you control with the greatest power");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public TriumphOfGerrard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put a +1/+1 counter on target creature you control with the greatest power.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetPermanent(filter)
        );
        // III — Target creature you control with the greatest power gains flying, first strike, and lifelink until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                ability -> {
                    ability.addEffect(new GainAbilityTargetEffect(
                            FlyingAbility.getInstance(), Duration.EndOfTurn
                    ).setText("Target creature you control with the greatest power gains flying"));
                    ability.addEffect(new GainAbilityTargetEffect(
                            FirstStrikeAbility.getInstance(), Duration.EndOfTurn
                    ).setText(", first strike"));
                    ability.addEffect(new GainAbilityTargetEffect(
                            LifelinkAbility.getInstance(), Duration.EndOfTurn
                    ).setText(", and lifelink until end of turn"));
                    ability.addTarget(new TargetPermanent(filter));
                }
        );
        this.addAbility(sagaAbility);
    }

    private TriumphOfGerrard(final TriumphOfGerrard card) {
        super(card);
    }

    @Override
    public TriumphOfGerrard copy() {
        return new TriumphOfGerrard(this);
    }
}
