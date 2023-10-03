
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ChaoticGoo extends CardImpl {

    public ChaoticGoo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Chaotic Goo enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
            "{this} enters the battlefield with three +1/+1 counters on it"));
        
        // At the beginning of your upkeep, you may flip a coin. If you win the flip, put a +1/+1 counter on Chaotic Goo. If you lose the flip, remove a +1/+1 counter from Chaotic Goo.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ChaoticGooEffect(), TargetController.YOU, true));
    }

    private ChaoticGoo(final ChaoticGoo card) {
        super(card);
    }

    @Override
    public ChaoticGoo copy() {
        return new ChaoticGoo(this);
    }
}

class ChaoticGooEffect extends OneShotEffect {

    public ChaoticGooEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you win the flip, put a +1/+1 counter on {this}. If you lose the flip, remove a +1/+1 counter from {this}";
    }

    private ChaoticGooEffect(final ChaoticGooEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                game.informPlayers("Chaotic Goo: Won flip. Put a +1/+1 counter on Chaotic Goo.");
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)).apply(game, source);
                return true;
            } else {
                game.informPlayers("Chaotic Goo: Lost flip. Remove a +1/+1 counter on Chaotic Goo.");
                new RemoveCounterSourceEffect(CounterType.P1P1.createInstance(1)).apply(game, source);
                return true;
                }
            }
        return false;
    }

    @Override
    public ChaoticGooEffect copy() {
        return new ChaoticGooEffect(this);
    }
}
