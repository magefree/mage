
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MycoidShepherd extends CardImpl {

    public MycoidShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}{W}");
        this.subtype.add(SubType.FUNGUS);



        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Mycoid Shepherd or another creature you control with power 5 or greater dies, you may gain 5 life.
        this.addAbility(new MycoidShepherdTriggeredAbility());
        
    }

    private MycoidShepherd(final MycoidShepherd card) {
        super(card);
    }

    @Override
    public MycoidShepherd copy() {
        return new MycoidShepherd(this);
    }
}

class MycoidShepherdTriggeredAbility extends TriggeredAbilityImpl {

    public MycoidShepherdTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(5), true);
    }

    private MycoidShepherdTriggeredAbility(final MycoidShepherdTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject lastKnown = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        if (lastKnown == null) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        if (super.getSourceId().equals(event.getTargetId())
                || permanent.getPower().getValue() > 4
                && permanent.isControlledBy(controllerId)) {
            Zone after = game.getState().getZone(event.getTargetId());
            return after != null && Zone.GRAVEYARD.match(after);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Mycoid Shepherd or another creature you control with power 5 or greater dies, you may gain 5 life.";
    }

    @Override
    public MycoidShepherdTriggeredAbility copy() {
        return new MycoidShepherdTriggeredAbility(this);
    }
}