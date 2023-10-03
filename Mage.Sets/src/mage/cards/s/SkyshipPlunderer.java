package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author Styxo
 */
public final class SkyshipPlunderer extends CardImpl {

    public SkyshipPlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skyship Plunderer deals combat damage to a player, for each kind of counter on target permanent or player, give that permanent or player another counter of that kind.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SkyshipPlundererEffect(), false);
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    private SkyshipPlunderer(final SkyshipPlunderer card) {
        super(card);
    }

    @Override
    public SkyshipPlunderer copy() {
        return new SkyshipPlunderer(this);
    }
}

class SkyshipPlundererEffect extends OneShotEffect {

    SkyshipPlundererEffect() {
        super(Outcome.Neutral);
        this.staticText = "for each kind of counter on target permanent or player, give that permanent or player another counter of that kind";
    }

    private SkyshipPlundererEffect(final SkyshipPlundererEffect effect) {
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
    public SkyshipPlundererEffect copy() {
        return new SkyshipPlundererEffect(this);
    }

}
