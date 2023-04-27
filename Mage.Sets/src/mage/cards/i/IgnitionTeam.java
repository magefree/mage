
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.ElementalCreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class IgnitionTeam extends CardImpl {
    
    public IgnitionTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ignition Team enters the battlefield with X +1/+1 counters on it, where X is the number of tapped lands on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0),
                new TappedLandsCount(), true), 
                "with X +1/+1 counters on it, where X is the number of tapped lands on the battlefield."));
        
        // {2}{R}, Remove a +1/+1 counter from Ignition Team: Target land becomes a 4/4 red Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(
                new ElementalCreatureToken(4, 4, "4/4 red Elemental creature", new ObjectColor("R")),
                false, true, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
        
    }

    private IgnitionTeam(final IgnitionTeam card) {
        super(card);
    }

    @Override
    public IgnitionTeam copy() {
        return new IgnitionTeam(this);
    }
}

class TappedLandsCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            FilterLandPermanent filter = new FilterLandPermanent("tapped lands on the battlefield");
            filter.add(TappedPredicate.TAPPED);
            return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new TappedLandsCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "tapped lands on the battlefield";
    }
}