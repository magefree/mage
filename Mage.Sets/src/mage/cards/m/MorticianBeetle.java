
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author North
 */
public final class MorticianBeetle extends CardImpl {

    public MorticianBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Mortician Beetle.
        this.addAbility(new PlayerSacrificesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true));
    }

    private MorticianBeetle(final MorticianBeetle card) {
        super(card);
    }

    @Override
    public MorticianBeetle copy() {
        return new MorticianBeetle(this);
    }
}

class PlayerSacrificesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerSacrificesCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a player sacrifices a creature, ");
    }

    public PlayerSacrificesCreatureTriggeredAbility(final PlayerSacrificesCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        if (mageObject != null && mageObject.isCreature(game)) {
            return true;
        }
        return false;
    }

    @Override
    public PlayerSacrificesCreatureTriggeredAbility copy() {
        return new PlayerSacrificesCreatureTriggeredAbility(this);
    }
}