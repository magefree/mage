package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.PsionicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ProtossToken;

/**
 *
 * @author NinthWorld
 */
public final class WarpGate extends CardImpl {

    public WarpGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        

        // Psionic 3
        this.addAbility(new PsionicAbility(3));

        // At the beginning of your upkeep, remove a psi counter from Warp Gate. Then if Warp Gate has no psi counters on it, sacrifice it and put a 3/3 blue Protoss creature token onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.PSI.createInstance()), TargetController.YOU, false));
        Ability ability = new WarpGateNoCountersAbilty();
        ability.addEffect(new CreateTokenEffect(new ProtossToken()));
        this.addAbility(ability);
    }

    public WarpGate(final WarpGate card) {
        super(card);
    }

    @Override
    public WarpGate copy() {
        return new WarpGate(this);
    }
}

// From AfiyaGroveNoCountersAbility
class WarpGateNoCountersAbilty extends StateTriggeredAbility {

    public WarpGateNoCountersAbilty() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public WarpGateNoCountersAbilty(final WarpGateNoCountersAbilty ability) {
        super(ability);
    }

    @Override
    public WarpGateNoCountersAbilty copy() {
        return new WarpGateNoCountersAbilty(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(CounterType.PSI) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Then if {this} has no psi counters on it, sacrifice it and put a 3/3 blue Protoss creature token onto the battlefield";
    }
}