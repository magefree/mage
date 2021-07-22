
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo
 */
public final class QuarryHauler extends CardImpl {

    public QuarryHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Quarry Hauler enters the battlefield, for each kind of counter on target permanent, put another counter of that kind on it or remove one from it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarryHaulerEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private QuarryHauler(final QuarryHauler card) {
        super(card);
    }

    @Override
    public QuarryHauler copy() {
        return new QuarryHauler(this);
    }
}

class QuarryHaulerEffect extends OneShotEffect {

    public QuarryHaulerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "for each kind of counter on target permanent, put another counter of that kind on it or remove one from it";

    }

    public QuarryHaulerEffect(final QuarryHaulerEffect effect) {
        super(effect);
    }

    @Override
    public QuarryHaulerEffect copy() {
        return new QuarryHaulerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent != null) {
                Counters counters = permanent.getCounters(game).copy();
                CounterType counterType;
                for (Counter counter : counters.values()) {
                    if (controller.chooseUse(Outcome.BoostCreature, "Choose whether to add or remove a " + counter.getName() + " counter", null, "Add", "Remove", source, game)) {
                        counterType = CounterType.findByName(counter.getName());
                        Counter counterToAdd;
                        if (counterType != null) {
                            counterToAdd = counterType.createInstance();
                        } else {
                            counterToAdd = new Counter(counter.getName());
                        }
                        permanent.addCounters(counterToAdd, source.getControllerId(), source, game);
                    } else {
                        counterType = CounterType.findByName(counter.getName());
                        if (counterType != null) {
                            permanent.removeCounters(counterType.createInstance(), source, game);
                        } else {
                            permanent.removeCounters(counter.getName(), 1, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
