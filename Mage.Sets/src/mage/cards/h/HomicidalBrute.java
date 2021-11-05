
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author nantuko
 */
public final class HomicidalBrute extends CardImpl {

    public HomicidalBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.
        this.addAbility(new HomicidalBruteTriggeredAbility(), new HomicidalBruteWatcher());
    }

    private HomicidalBrute(final HomicidalBrute card) {
        super(card);
    }

    @Override
    public HomicidalBrute copy() {
        return new HomicidalBrute(this);
    }

}

class HomicidalBruteTriggeredAbility extends TriggeredAbilityImpl {

    public HomicidalBruteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TapSourceEffect(), false);
        addEffect(new TransformSourceEffect());
    }

    public HomicidalBruteTriggeredAbility(HomicidalBruteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HomicidalBruteTriggeredAbility copy() {
        return new HomicidalBruteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            Watcher watcher = game.getState().getWatcher(HomicidalBruteWatcher.class, sourceId);
            if (watcher == null || !watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your end step, if {this} didn't attack this turn, tap {this}, then transform it";
    }
}

class HomicidalBruteWatcher extends Watcher {

    public HomicidalBruteWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) {
            return;
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(sourceId)) {
            condition = true;
        }
    }
}