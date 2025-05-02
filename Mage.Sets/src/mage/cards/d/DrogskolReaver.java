
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Loki
 */
public final class DrogskolReaver extends CardImpl {

    public DrogskolReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        // Whenever you gain life, draw a card.
        this.addAbility(new DrogskolReaverAbility());
    }

    private DrogskolReaver(final DrogskolReaver card) {
        super(card);
    }

    @Override
    public DrogskolReaver copy() {
        return new DrogskolReaver(this);
    }
}

class DrogskolReaverAbility extends TriggeredAbilityImpl {

    public DrogskolReaverAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private DrogskolReaverAbility(final DrogskolReaverAbility ability) {
        super(ability);
    }

    @Override
    public DrogskolReaverAbility copy() {
        return new DrogskolReaverAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, draw a card.";
    }

}