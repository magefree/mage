
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class ReaperOfSheoldred extends CardImpl {

    public ReaperOfSheoldred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(InfectAbility.getInstance());

        // Whenever a source deals damage to Reaper of Sheoldred, that source's controller gets a poison counter.
        this.addAbility(new ReaperOfSheoldredTriggeredAbility());
    }

    private ReaperOfSheoldred(final ReaperOfSheoldred card) {
        super(card);
    }

    @Override
    public ReaperOfSheoldred copy() {
        return new ReaperOfSheoldred(this);
    }
}

class ReaperOfSheoldredTriggeredAbility extends TriggeredAbilityImpl {

    ReaperOfSheoldredTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.POISON.createInstance()));
    }

    ReaperOfSheoldredTriggeredAbility(final ReaperOfSheoldredTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReaperOfSheoldredTriggeredAbility copy() {
        return new ReaperOfSheoldredTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            UUID controller = game.getControllerId(event.getSourceId());
            if (controller != null) {
                Player player = game.getPlayer(controller);
                if (player != null) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(player.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, that source's controller gets a poison counter.";
    }
}
