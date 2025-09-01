package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ViviOrnitier extends CardImpl {

    public ViviOrnitier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {0}: Add X mana in any combination of {U} and/or {R}, where X is Vivi Ornitier's power. Activate only during your turn and only once each turn.
        this.addAbility(new ViviOrnitierManaAbility());

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Vivi Ornitier and it deals 1 damage to each opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT, "it").concatBy("and"));
        this.addAbility(ability);
    }

    private ViviOrnitier(final ViviOrnitier card) {
        super(card);
    }

    @Override
    public ViviOrnitier copy() {
        return new ViviOrnitier(this);
    }
}

class ViviOrnitierManaAbility extends ActivatedManaAbilityImpl {

    ViviOrnitierManaAbility() {
        super(
                Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(
                        SourcePermanentPowerValue.NOT_NEGATIVE,
                        SourcePermanentPowerValue.NOT_NEGATIVE,
                        ColoredManaSymbol.U,
                        ColoredManaSymbol.R
                ),
                new GenericManaCost(0)
        );
        this.condition = MyTurnCondition.instance;
        this.maxActivationsPerTurn = 1;
    }

    private ViviOrnitierManaAbility(final ViviOrnitierManaAbility ability) {
        super(ability);
    }

    public ViviOrnitierManaAbility copy() {
        return new ViviOrnitierManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only during your turn and only once each turn.";
    }
}
