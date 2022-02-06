package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ShaileDeanOfRadiance extends ModalDoubleFacesCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    private static final FilterPermanent shaileFilter = new FilterControlledCreaturePermanent("creature that entered the battlefield under your control this turn");

    static {
        filter.add(AnotherPredicate.instance);
        shaileFilter.add(EnteredThisTurnPredicate.instance);
        shaileFilter.add((Predicate<Permanent>) (input, game) -> !input.checkControlChanged(game));
    }

    public ShaileDeanOfRadiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD, SubType.CLERIC}, "{1}{W}",
                "Embrose, Dean of Shadow", new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{2}{B}{B}");

        // 1.
        // Shaile, Dean of Radiance
        // Legendary Creature - Bird Cleric
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(1, 1);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // {T}: Put a +1/+1 counter on each creature that entered the battlefield under your control this turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), shaileFilter), new TapSourceCost()));

        // 2.
        // Embrose, Dean of Shadow
        // Legendary Creature - Human Warlock
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setPT(4, 4);

        // {T}: Put a +1/+1 counter on another target creature, then Embrose, Dean of Shadow deals 2 damage to that creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TapSourceCost());
        ability.addEffect(new DamageTargetEffect(2).concatBy(", then").setText("{this} deals 2 damage to that creature"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.getRightHalfCard().addAbility(ability);

        // Whenever a creature you control with a +1/+1 counter on it dies, draw a card.
        this.getRightHalfCard().addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, StaticFilters.FILTER_A_CONTROLLED_CREATURE_P1P1));
    }

    private ShaileDeanOfRadiance(final ShaileDeanOfRadiance card) {
        super(card);
    }

    @Override
    public ShaileDeanOfRadiance copy() {
        return new ShaileDeanOfRadiance(this);
    }
}