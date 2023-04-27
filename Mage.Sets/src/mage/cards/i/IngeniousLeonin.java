package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngeniousLeonin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target attacking creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public IngeniousLeonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}{W}: Put a +1/+1 counter on another target attacking creature you control. If that creature is a Cat, it gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{W}")
        );
        ability.addEffect(new IngeniousLeoninEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private IngeniousLeonin(final IngeniousLeonin card) {
        super(card);
    }

    @Override
    public IngeniousLeonin copy() {
        return new IngeniousLeonin(this);
    }
}

class IngeniousLeoninEffect extends OneShotEffect {

    IngeniousLeoninEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is a Cat, it gains first strike until end of turn";
    }

    private IngeniousLeoninEffect(final IngeniousLeoninEffect effect) {
        super(effect);
    }

    @Override
    public IngeniousLeoninEffect copy() {
        return new IngeniousLeoninEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null && permanent.hasSubtype(SubType.CAT, game)) {
            game.addEffect(new GainAbilityTargetEffect(
                    FirstStrikeAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
            return true;
        }
        return false;
    }
}
