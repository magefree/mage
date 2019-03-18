
package mage.cards.c;

import java.util.Objects;
import java.util.UUID;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CityOfTraitors extends CardImpl {

    public CityOfTraitors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // When you play another land, sacrifice City of Traitors.
        this.addAbility(new CityOfTraitorsTriggeredAbility());

        // {tap}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    public CityOfTraitors(final CityOfTraitors card) {
        super(card);
    }

    @Override
    public CityOfTraitors copy() {
        return new CityOfTraitors(this);
    }
}

class CityOfTraitorsTriggeredAbility extends TriggeredAbilityImpl {

    CityOfTraitorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    CityOfTraitorsTriggeredAbility(CityOfTraitorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land.isLand()
                && land.isControlledBy(this.controllerId)
                && !Objects.equals(event.getTargetId(), this.getSourceId());
    }

    @Override
    public CityOfTraitorsTriggeredAbility copy() {
        return new CityOfTraitorsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you play another land, sacrifice {this}";
    }
}
