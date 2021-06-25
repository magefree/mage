package mage.game.command;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class DungeonRoom {

    private final UUID id;
    private final String name;
    private final List<DungeonRoom> nextRooms = new ArrayList<>();
    private final RoomTriggeredAbility roomTriggeredAbility;

    public DungeonRoom(String name, Effect... effects) {
        this.id = UUID.randomUUID();
        this.name = name;
        roomTriggeredAbility = new RoomTriggeredAbility(this, effects);
    }

    public void addNextRoom(DungeonRoom room) {
        nextRooms.add(room);
    }

    public RoomTriggeredAbility getRoomTriggeredAbility() {
        return roomTriggeredAbility;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return roomTriggeredAbility.getText();
    }

    public String getName() {
        return name;
    }

    public DungeonRoom chooseNextRoom(Ability source, Game game) {
        switch (nextRooms.size()) {
            case 0:
                return null;
            case 1:
                return nextRooms.get(0);
            case 2:
                DungeonRoom room1 = nextRooms.get(0);
                DungeonRoom room2 = nextRooms.get(1);
                Player player = game.getPlayer(source.getControllerId());
                if (player == null) {
                    return null;
                }
                return player.chooseUse(
                        Outcome.Neutral, "Choose which room to go to",
                        null, room1.name, room2.name, source, game
                ) ? room1 : room2;
            default:
                throw new UnsupportedOperationException("there shouldn't be more than two rooms to go to");
        }
    }
}

class RoomTriggeredAbility extends TriggeredAbilityImpl {

    private final DungeonRoom room;

    RoomTriggeredAbility(DungeonRoom room, Effect... effects) {
        super(Zone.COMMAND, null, false);
        this.room = room;
        for (Effect effect : effects) {
            this.addEffect(effect);
        }
        this.setRuleVisible(false);
    }

    private RoomTriggeredAbility(final RoomTriggeredAbility ability) {
        super(ability);
        this.room = ability.room;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROOM_ENTERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(room.getId());
    }

    @Override
    public RoomTriggeredAbility copy() {
        return new RoomTriggeredAbility(this);
    }

    public String getText() {
        return CardUtil.getTextWithFirstCharUpperCase(super.getRule());
    }

    @Override
    public String getRule() {
        return "When you enter this room, " + super.getRule();
    }
}
