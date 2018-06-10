
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ClockworkHydra extends CardImpl {

    public ClockworkHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Clockwork Hydra enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)), "with four +1/+1 counters on it"));
        // Whenever Clockwork Hydra attacks or blocks, remove a +1/+1 counter from it. If you do, Clockwork Hydra deals 1 damage to any target.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkHydraEffect(), false));

        // {tap}: Put a +1/+1 counter on Clockwork Hydra.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new TapSourceCost()));
    }

    public ClockworkHydra(final ClockworkHydra card) {
        super(card);
    }

    @Override
    public ClockworkHydra copy() {
        return new ClockworkHydra(this);
    }

}

class ClockworkHydraEffect extends OneShotEffect {

    public ClockworkHydraEffect() {
        super(Outcome.Damage);
        this.staticText = "remove a +1/+1 counter from it. If you do, {this} deals 1 damage to any target";
    }

    public ClockworkHydraEffect(final ClockworkHydraEffect effect) {
        super(effect);
    }

    @Override
    public ClockworkHydraEffect copy() {
        return new ClockworkHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), game);
            Target target = new TargetAnyTarget();
            if (controller.chooseTarget(outcome, target, source, game)) {
                Effect effect = new DamageTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
