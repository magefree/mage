package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SquirrelToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class Chitterspitter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a token");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrels");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public Chitterspitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        // At the beginning of your upkeep, you may sacrifice a token. If you do, put an acorn counter on Chitterspitter.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.ACORN.createInstance()),
                        new SacrificeTargetCost(new TargetControlledPermanent(filter))
                ),
                TargetController.YOU,
                false
        ));

        // Squirrels you control get +1/+1 for each acorn counter on Chitterspitter.
        CountersSourceCount counterValue = new CountersSourceCount(CounterType.ACORN);
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(counterValue, counterValue, Duration.WhileOnBattlefield, filter2, false)
        ));

        // {G}, {T}: Create a 1/1 green Squirrel creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new SquirrelToken()), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Chitterspitter(final Chitterspitter card) {
        super(card);
    }

    @Override
    public Chitterspitter copy() {
        return new Chitterspitter(this);
    }
}
