package mage.cards.f;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class FlameOn extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland cards");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint = new ValueHint("Noncreature, nonland cards in your graveyard", xValue);

    public FlameOn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Put X +1/+1 counters on target creature, where X is the number of noncreature, nonland cards in your graveyard. It gains flying until end of turn.
        this.getSpellAbility().addEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(), xValue)
                .setText("Put X +1/+1 counters on target creature, where X is the number of noncreature, nonland cards in your graveyard")
        );
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
            .setText("It gains flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);

        // Rebound
        this.addAbility(new ReboundAbility());

    }

    private FlameOn(final FlameOn card) {
        super(card);
    }

    @Override
    public FlameOn copy() {
        return new FlameOn(this);
    }
}
