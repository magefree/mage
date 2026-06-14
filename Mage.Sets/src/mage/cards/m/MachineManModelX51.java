package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MachineManModelX51 extends CardImpl {

    public MachineManModelX51(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Machine Man and he gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).concatBy("and"));
        this.addAbility(ability);
    }

    private MachineManModelX51(final MachineManModelX51 card) {
        super(card);
    }

    @Override
    public MachineManModelX51 copy() {
        return new MachineManModelX51(this);
    }
}
