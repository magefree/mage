package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Inhumaniac extends CardImpl {

    public Inhumaniac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BRAINIAC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, roll a six-sided die. On a 3 or 4, put a +1/+1 counter on Inhumaniac. On a 5 or higher, put two +1/+1 counters on it. On a 1, remove all +1/+1 counters from Inhumaniac.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InhumaniacEffect(), TargetController.YOU, false));
    }

    private Inhumaniac(final Inhumaniac card) {
        super(card);
    }

    @Override
    public Inhumaniac copy() {
        return new Inhumaniac(this);
    }
}

class InhumaniacEffect extends OneShotEffect {

    public InhumaniacEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die. On a 3 or 4, put a +1/+1 counter on {this}. On a 5 or higher, put two +1/+1 counters on it. On a 1, remove all +1/+1 counters from {this}";
    }

    public InhumaniacEffect(final InhumaniacEffect effect) {
        super(effect);
    }

    @Override
    public InhumaniacEffect copy() {
        return new InhumaniacEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            if (amount >= 3 && amount <= 4) {
                permanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
            } else if (amount >= 5) {
                permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
            } else if (amount == 1) {
                int numToRemove = permanent.getCounters(game).getCount(CounterType.P1P1);
                if (numToRemove > 0) {
                    permanent.removeCounters(CounterType.P1P1.getName(), numToRemove, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
