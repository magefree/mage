package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class UndercityNecrolisk extends CardImpl {

    public UndercityNecrolisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature:
        // Put a +1/+1 counter on Undercity Necrolisk.
        Ability ability = new SilencedActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
        // It gains menace until end of turn. Activate this ability only any time you could cast a sorcery.
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(),
                Duration.EndOfTurn
        ).setText("It gains menace until end of turn. Activate only as a sorcery. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private UndercityNecrolisk(final UndercityNecrolisk card) {
        super(card);
    }

    @Override
    public UndercityNecrolisk copy() {
        return new UndercityNecrolisk(this);
    }
}

// Needed in order to move menace hint text to the very end.
class SilencedActivateAsSorceryActivatedAbility extends ActivatedAbilityImpl {
    SilencedActivateAsSorceryActivatedAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
        timing = TimingRule.SORCERY;
    }

    private SilencedActivateAsSorceryActivatedAbility(final SilencedActivateAsSorceryActivatedAbility ability) {
        super(ability);
    }

    @Override
    public SilencedActivateAsSorceryActivatedAbility copy() {
        return new SilencedActivateAsSorceryActivatedAbility(this);
    }

    @Override
    public String getRule() { return super.getRule(); }
}