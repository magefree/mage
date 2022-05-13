
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author jeffwadsworth
 */
public final class RiverKelpie extends CardImpl {

    public RiverKelpie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever River Kelpie or another permanent is put onto the battlefield from a graveyard, draw a card.
        this.addAbility(new RiverKelpieTriggeredAbility());

        // Whenever a player casts a spell from a graveyard, draw a card.
        this.addAbility(new RiverKelpieTriggeredAbility2());

        // Persist
        this.addAbility(new PersistAbility());
    }

    private RiverKelpie(final RiverKelpie card) {
        super(card);
    }

    @Override
    public RiverKelpie copy() {
        return new RiverKelpie(this);
    }
}

class RiverKelpieTriggeredAbility extends TriggeredAbilityImpl {

    @Override
    public RiverKelpieTriggeredAbility copy() {
        return new RiverKelpieTriggeredAbility(this);
    }

    public RiverKelpieTriggeredAbility(final RiverKelpieTriggeredAbility ability) {
        super(ability);
    }

    public RiverKelpieTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.GRAVEYARD && zEvent.getToZone() == Zone.BATTLEFIELD;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another permanent enters the battlefield from a graveyard, draw a card.";
    }
}

class RiverKelpieTriggeredAbility2 extends TriggeredAbilityImpl {

    public RiverKelpieTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public RiverKelpieTriggeredAbility2(final RiverKelpieTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public RiverKelpieTriggeredAbility2 copy() {
        return new RiverKelpieTriggeredAbility2(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell from a graveyard, draw a card.";
    }
}
