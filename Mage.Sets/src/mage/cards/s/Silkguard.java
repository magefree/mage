package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Silkguard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Auras, Equipment, and modified creatures");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate(),
                Predicates.and(
                        ModifiedPredicate.instance,
                        CardType.CREATURE.getPredicate()
                )
        ));
    }

    public Silkguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Put a +1/+1 counter on each of up to X target creatures you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures you control"));
        this.getSpellAbility().setTargetAdjuster(SilkguardAdjuster.instance);

        // Auras, Equipment, and modified creatures you control gain hexproof until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, filter
        ).concatBy("<br>"));
    }

    private Silkguard(final Silkguard card) {
        super(card);
    }

    @Override
    public Silkguard copy() {
        return new Silkguard(this);
    }
}

enum SilkguardAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetControlledCreaturePermanent(0, ability.getManaCostsToPay().getX()));
    }
}
