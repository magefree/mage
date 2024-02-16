
package mage.cards.d;

import java.util.UUID;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class DarksteelReactor extends CardImpl {

    public DarksteelReactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Darksteel Reactor is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // At the beginning of your upkeep, you may put a charge counter on Darksteel Reactor.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, true));
        // When Darksteel Reactor has twenty or more charge counters on it, you win the game.
        this.addAbility(new DarksteelReactorStateTriggeredAbility());

    }

    private DarksteelReactor(final DarksteelReactor card) {
        super(card);
    }

    @Override
    public DarksteelReactor copy() {
        return new DarksteelReactor(this);
    }
}

class DarksteelReactorStateTriggeredAbility extends StateTriggeredAbility {

    public DarksteelReactorStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WinGameSourceControllerEffect());
    }

    private DarksteelReactorStateTriggeredAbility(final DarksteelReactorStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarksteelReactorStateTriggeredAbility copy() {
        return new DarksteelReactorStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(this.getSourceId());
        if(permanent != null && permanent.getCounters(game).getCount(CounterType.CHARGE) >= 20){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has twenty or more charge counters on it, you win the game.";
    }

}
