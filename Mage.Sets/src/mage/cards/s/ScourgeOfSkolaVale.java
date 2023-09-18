
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ScourgeOfSkolaVale extends CardImpl {

    public ScourgeOfSkolaVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Scourge of Skola Vale enters the battlefield with two +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(2), true);
        effect.setText("with two +1/+1 counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // {T}, Sacrifice another creature: Put a number of +1/+1 counters on Scourge of Skola Vale equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScourgeOfSkolaValeEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private ScourgeOfSkolaVale(final ScourgeOfSkolaVale card) {
        super(card);
    }

    @Override
    public ScourgeOfSkolaVale copy() {
        return new ScourgeOfSkolaVale(this);
    }
}

class ScourgeOfSkolaValeEffect extends OneShotEffect {

    public ScourgeOfSkolaValeEffect() {
        super(Outcome.GainLife);
        this.staticText = "Put a number of +1/+1 counters on {this} equal to the sacrificed creature's toughness";
    }

    private ScourgeOfSkolaValeEffect(final ScourgeOfSkolaValeEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfSkolaValeEffect copy() {
        return new ScourgeOfSkolaValeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                int amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                Player player = game.getPlayer(source.getControllerId());
                if (amount > 0 && player != null) {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount), true).apply(game, source);
                }
            }
        }
        return false;
    }
}
