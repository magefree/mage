
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EbonPraetor extends CardImpl {

    public EbonPraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, put a -2/-2 counter on Ebon Praetor.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M2M2.createInstance()), TargetController.YOU, false));

        // Sacrifice a creature: Remove a -2/-2 counter from Ebon Praetor. If the sacrificed creature was a Thrull, put a +1/+0 counter on Ebon Praetor. Activate this ability only during your upkeep and only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.M2M2.createInstance()),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT), 1, new IsStepCondition(PhaseStep.UPKEEP));
        ability.addEffect(new EbonPraetorEffect());
        this.addAbility(ability);
    }

    private EbonPraetor(final EbonPraetor card) {
        super(card);
    }

    @Override
    public EbonPraetor copy() {
        return new EbonPraetor(this);
    }
}

class EbonPraetorEffect extends OneShotEffect {

    public EbonPraetorEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If the sacrificed creature was a Thrull, put a +1/+0 counter on {this}";
    }

    public EbonPraetorEffect(final EbonPraetorEffect effect) {
        super(effect);
    }

    @Override
    public EbonPraetorEffect copy() {
        return new EbonPraetorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                Permanent sacrificedCreature = ((SacrificeTargetCost) cost).getPermanents().get(0);
                Permanent sourceCreature = game.getPermanent(source.getSourceId());
                if (sacrificedCreature.hasSubtype(SubType.THRULL, game) && sourceCreature != null) {
                    sourceCreature.addCounters(CounterType.P1P0.createInstance(), source.getControllerId(), source, game);
                    return true;
                }
            }
        }
        return true;
    }
}
