package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonPrimalGaruda extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SummonPrimalGaruda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.HARPY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Aerial Blast -- This creature deals 4 damage to target tapped creature an opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new DamageTargetEffect(4));
            ability.addTarget(new TargetPermanent(filter));
            ability.withFlavorWord("Aerial Blast");
        });

        // II, III -- Slipstream -- Another target creature you control gets +1/+0 and gains flying until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new BoostTargetEffect(2, 0).setText("another target creature you control gets +1/+0"));
            ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("and gains flying until end of turn"));
            ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
            ability.withFlavorWord("Slipstream");
        });
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SummonPrimalGaruda(final SummonPrimalGaruda card) {
        super(card);
    }

    @Override
    public SummonPrimalGaruda copy() {
        return new SummonPrimalGaruda(this);
    }
}
