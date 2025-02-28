package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.SacrificedPermanentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

/**
 *
 * @author Grath
 */
public final class EverethViceroyOfPlunder extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a treasure");

    static {
        filter.add(SubType.TREASURE.getPredicate());
    }

    public EverethViceroyOfPlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice another creature or artifact: Put a +1/+1 counter on Evereth, Viceroy of Plunder. If the sacrificed permanent was a Treasure, Evereth gains lifelink until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        );
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new SacrificedPermanentCondition(filter),
                "If the sacrificed permanent was a Treasure, Evereth gains lifelink until end of turn."
        ));
        this.addAbility(ability);

        // When Evereth dies, you may pay {1}{B/R}. When you do, Evereth deals damage equal to its power to each opponent.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DoWhenCostPaid(new ReflexiveTriggeredAbility(
                        new DamagePlayersEffect(SourcePermanentPowerValue.NOT_NEGATIVE, TargetController.OPPONENT),
                        false, "{this} deals damage equal to its power to each opponent"
                ), new ManaCostsImpl<>("{1}{B/R}"), "Pay {1}{B/R}?")
        ));
    }

    private EverethViceroyOfPlunder(final EverethViceroyOfPlunder card) {
        super(card);
    }

    @Override
    public EverethViceroyOfPlunder copy() {
        return new EverethViceroyOfPlunder(this);
    }
}
