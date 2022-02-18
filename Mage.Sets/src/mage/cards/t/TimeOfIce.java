
package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimeOfIce extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TimeOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Tap target creature an opponent controls. It doesn't untap during its controller's untap step for as long as you control Time of Ice.
        Effects effects = new Effects();
        effects.add(new TapTargetEffect());
        effects.add(new DontUntapInControllersUntapStepTargetEffect(Duration.WhileControlled, "It"));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, effects,
                new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        );

        // III — Return all tapped creatures to their owners' hands.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ReturnToHandFromBattlefieldAllEffect(filter));
        this.addAbility(sagaAbility);
    }

    private TimeOfIce(final TimeOfIce card) {
        super(card);
    }

    @Override
    public TimeOfIce copy() {
        return new TimeOfIce(this);
    }
}
