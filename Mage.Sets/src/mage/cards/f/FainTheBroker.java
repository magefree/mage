package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.InklingToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class FainTheBroker extends CardImpl {

    public FainTheBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice a creature: Put two +1/+1 counters on target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {T}, Remove a counter from a creature you control: Create a Treasure token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new TapSourceCost());
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true)));
        this.addAbility(ability);

        // {T}, Sacrifice an artifact: Create a 2/1 white and black Inkling creature token with flying.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new InklingToken()), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)
        ));
        this.addAbility(ability);

        // {3}{B}: Untap Fain, the Broker.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{3}{B}")));
    }

    private FainTheBroker(final FainTheBroker card) {
        super(card);
    }

    @Override
    public FainTheBroker copy() {
        return new FainTheBroker(this);
    }
}
