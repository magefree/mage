package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author muz
 */
public final class PowerfulBroker extends CardImpl {

    public PowerfulBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: For each kind of counter on target permanent or player, give that permanent or player another counter of that kind. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new PowerfulBrokerEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    private PowerfulBroker(final PowerfulBroker card) {
        super(card);
    }

    @Override
    public PowerfulBroker copy() {
        return new PowerfulBroker(this);
    }
}

class PowerfulBrokerEffect extends OneShotEffect {

    PowerfulBrokerEffect() {
        super(Outcome.Neutral);
        this.staticText = "for each kind of counter on target permanent or player, give that permanent or player another counter of that kind";
    }

    private PowerfulBrokerEffect(final PowerfulBrokerEffect effect) {
        super(effect);
    }

    @Override
    public PowerfulBrokerEffect copy() {
        return new PowerfulBrokerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                Counters counters = player.getCountersAsCopy();
                for (Counter counter : counters.values()) {
                    CounterType counterType = CounterType.findByName(counter.getName());
                    Counter counterToAdd;
                    if (counterType != null) {
                        counterToAdd = counterType.createInstance();
                    } else {
                        counterToAdd = new Counter(counter.getName());
                    }
                    player.addCounters(counterToAdd, source.getControllerId(), source, game);
                }
                return true;
            }
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                Counters counters = permanent.getCounters(game).copy();
                for (Counter counter : counters.values()) {
                    CounterType counterType = CounterType.findByName(counter.getName());
                    Counter counterToAdd;
                    if (counterType != null) {
                        counterToAdd = counterType.createInstance();
                    } else {
                        counterToAdd = new Counter(counter.getName());
                    }
                    permanent.addCounters(counterToAdd, source.getControllerId(), source, game);
                }
            }
            return true;

        }
        return false;
    }
}
