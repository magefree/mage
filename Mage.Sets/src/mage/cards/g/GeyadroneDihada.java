package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class GeyadroneDihada extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanents with corruption counters on them");
    private static final FilterPermanent filter2 = new FilterCreatureOrPlaneswalkerPermanent("other creature or planeswalker");

    static {
        filter.add(CounterType.CORRUPTION.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public GeyadroneDihada(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DIHADA);
        this.setStartingLoyalty(4);

        // Protection from permanents with corruption counters on them
        this.addAbility(new ProtectionAbility(filter));

        // +1: Each opponent loses 2 life and you gain 2 life. Put a corruption counter on up to one other target creature or planeswalker.
        Ability ability = new LoyaltyAbility(new LoseLifeOpponentsEffect(2), 1);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.CORRUPTION.createInstance(), Outcome.Detriment)
                .setText("Put a corruption counter on up to one other target creature or planeswalker"));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        this.addAbility(ability);

        // −3: Gain control of target creature or planeswalker until end of tun. Untap it and put a corruption counter on it. It gains haste until end of turn.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.EndOfTurn), -3);
        ability.addEffect(new UntapTargetEffect().setText("Untap it"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.CORRUPTION.createInstance(), Outcome.Detriment).setText("and put a corruption counter on it"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // −7: Gain control of each permanent with a corruption counter on it.
        this.addAbility(new LoyaltyAbility(
                new GainControlAllEffect(Duration.Custom, filter).setText("gain control of each permanent with a corruption counter on it"),
                -7
        ));
    }

    private GeyadroneDihada(final GeyadroneDihada card) {
        super(card);
    }

    @Override
    public GeyadroneDihada copy() {
        return new GeyadroneDihada(this);
    }
}
