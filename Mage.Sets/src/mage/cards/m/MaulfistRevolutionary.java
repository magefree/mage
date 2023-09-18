package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
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
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author fireshoes
 */
public final class MaulfistRevolutionary extends CardImpl {

    public MaulfistRevolutionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Maulfist Revolutionary enters the battlefield or dies, for each kind of counter on target permanent or player,
        // give that permanent or player another counter of that kind.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new MaulfistRevolutionaryEffect(), false);
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    private MaulfistRevolutionary(final MaulfistRevolutionary card) {
        super(card);
    }

    @Override
    public MaulfistRevolutionary copy() {
        return new MaulfistRevolutionary(this);
    }
}

class MaulfistRevolutionaryEffect extends OneShotEffect {

    MaulfistRevolutionaryEffect() {
        super(Outcome.Neutral);
        this.staticText = "for each kind of counter on target permanent or player, give that permanent or player another counter of that kind";
    }

    private MaulfistRevolutionaryEffect(final MaulfistRevolutionaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                Counters counters = player.getCounters().copy();
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

    @Override
    public MaulfistRevolutionaryEffect copy() {
        return new MaulfistRevolutionaryEffect(this);
    }

}
