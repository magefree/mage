
package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
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
        this.staticText = "for each kind of counter on target permanent, " +
                "put another counter of that kind on it or remove one from it";

    }

    private QuarryHaulerEffect(final QuarryHaulerEffect effect) {
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
        if (controller == null || permanent == null) {
            return false;
        }
        List<String> counterNames = permanent
                .getCounters(game)
                .values()
                .stream()
                .map(Counter::getName)
                .collect(Collectors.toList());
        for (String counterName : counterNames) {
            Counter newCounter = CounterType.findByName(counterName).createInstance();
            if (controller.chooseUse(
                    Outcome.BoostCreature, "Add or remove a " + counterName + " counter?",
                    null, "Add", "Remove", source, game
            )) {
                permanent.addCounters(newCounter, source.getControllerId(), source, game);
            } else {
                permanent.removeCounters(newCounter, source, game);
            }
        }
        return true;
    }

}
