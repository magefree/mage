
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
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

/**
 *
 * @author LevelX2
 */
public final class GrafMole extends CardImpl {

    public GrafMole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you sacrifice a Clue, you gain 3 life.
        this.addAbility(new GrafMoleTriggeredAbility());
    }

    private GrafMole(final GrafMole card) {
        super(card);
    }

    @Override
    public GrafMole copy() {
        return new GrafMole(this);
    }
}

class GrafMoleTriggeredAbility extends TriggeredAbilityImpl {

    public GrafMoleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(3));
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a Clue, ");
    }

    public GrafMoleTriggeredAbility(final GrafMoleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrafMoleTriggeredAbility copy() {
        return new GrafMoleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).hasSubtype(SubType.CLUE, game);
    }
}
