
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class Anthroplasm extends CardImpl {

    public Anthroplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Anthroplasm enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), "with two +1/+1 counters on it"));
        // {X}, {tap}: Remove all +1/+1 counters from Anthroplasm and put X +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AnthroplasmEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private Anthroplasm(final Anthroplasm card) {
        super(card);
    }

    @Override
    public Anthroplasm copy() {
        return new Anthroplasm(this);
    }
}

class AnthroplasmEffect extends OneShotEffect {

    AnthroplasmEffect() {
        super(Outcome.Benefit);
        staticText = "Remove all +1/+1 counters from {this} and put X +1/+1 counters on it";
    }

    AnthroplasmEffect(AnthroplasmEffect effect) {
        super(effect);
    }

    @Override
    public AnthroplasmEffect copy() {
        return new AnthroplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            //Remove all +1/+1 counters
            permanent.removeCounters(permanent.getCounters(game).get(CounterType.P1P1.getName()), source, game);
            //put X +1/+1 counters
            permanent.addCounters(CounterType.P1P1.createInstance(source.getManaCostsToPay().getX()), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

}
