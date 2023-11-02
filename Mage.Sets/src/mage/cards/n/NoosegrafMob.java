
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class NoosegrafMob extends CardImpl {

    public NoosegrafMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Noosegraf Mob enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), "with five +1/+1 counters on it"));

        // Whenever a player casts a spell, remove a +1/+1 counter from Noosegraf Mob. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new SpellCastAllTriggeredAbility(new NoosegrafMobEffect(), false));
    }

    private NoosegrafMob(final NoosegrafMob card) {
        super(card);
    }

    @Override
    public NoosegrafMob copy() {
        return new NoosegrafMob(this);
    }
}

class NoosegrafMobEffect extends OneShotEffect {

    public NoosegrafMobEffect() {
        super(Outcome.Benefit);
        staticText = "remove a +1/+1 counter from Noosegraf Mob. If you do, create a 2/2 black Zombie creature token";
    }

    private NoosegrafMobEffect(final NoosegrafMobEffect effect) {
        super(effect);
    }

    @Override
    public NoosegrafMobEffect copy() {
        return new NoosegrafMobEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
            Effect effect = new CreateTokenEffect(new ZombieToken());
            return effect.apply(game, source);
            }
        return false;
    }
}
