package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AjaniInspiringLeader extends CardImpl {

    public AjaniInspiringLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);
        this.setStartingLoyalty(5);

        // +2: You gain 2 life. Put two +1/+1 counters on up to one target creature.
        Ability ability = new LoyaltyAbility(new GainLifeEffect(2), 2);
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −3: Exile target creature. Its controller gains 2 life.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainLifeTargetControllerEffect(2));
        this.addAbility(ability);

        // −10: Creatures you control gain flying and double strike until end of turn.
        ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("Creatures you control gain flying"), -10);
        ability.addEffect(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and double strike until end of turn"));
        this.addAbility(ability);
    }

    private AjaniInspiringLeader(final AjaniInspiringLeader card) {
        super(card);
    }

    @Override
    public AjaniInspiringLeader copy() {
        return new AjaniInspiringLeader(this);
    }
}
