package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class MiglozMazeCrusher extends CardImpl {

    public MiglozMazeCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Migloz, Maze Crusher enters the battlefield with five oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(5)),
                "with five oil counters on it"
        ));

        // {1}, Remove an oil counter from Migloz: It gains vigilance and menace until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains vigilance"), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false))
                .setText("and menace until end of turn"));
        this.addAbility(ability);

        // {2}, Remove two oil counters from Migloz: It gets +2/+2 until end of turn.
        ability = new SimpleActivatedAbility(new BoostSourceEffect(
                2, 2, Duration.EndOfTurn, "it"
        ), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance(2)));
        this.addAbility(ability);

        // {3}, Remove three oil counters from Migloz: Destroy target artifact or enchantment.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(3));
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance(3)));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private MiglozMazeCrusher(final MiglozMazeCrusher card) {
        super(card);
    }

    @Override
    public MiglozMazeCrusher copy() {
        return new MiglozMazeCrusher(this);
    }
}
