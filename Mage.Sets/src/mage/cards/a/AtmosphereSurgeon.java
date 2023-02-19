package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public class AtmosphereSurgeon extends CardImpl {

    public AtmosphereSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Whenever you cast a noncreature spell, put an oil counter on Atmosphere Surgeon.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        //Remove an oil counter from Atmosphere Surgeon: Target creature gains flying until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AtmosphereSurgeon(final AtmosphereSurgeon card) {
        super(card);
    }

    @Override
    public AtmosphereSurgeon copy() {
        return new AtmosphereSurgeon(this);
    }
}
