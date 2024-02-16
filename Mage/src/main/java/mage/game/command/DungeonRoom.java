package mage.game.command;

import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
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

    private DungeonRoom(final DungeonRoom room) {
        this.id = room.id;
        this.name = room.name;
        this.roomTriggeredAbility = new RoomTriggeredAbility(this, room.roomTriggeredAbility);
    }

    public DungeonRoom copy() {
        return new DungeonRoom(this);
    }

    public void addTarget(Target target) {
        roomTriggeredAbility.addTarget(target);
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

    public boolean hasNextRoom() {
        return !nextRooms.isEmpty();
    }

    List<DungeonRoom> getNextRooms() {
        return nextRooms;
    }

    public DungeonRoom chooseNextRoom(UUID playerId, Game game) {
        switch (nextRooms.size()) {
            case 0:
                return null;
            case 1:
                return nextRooms.get(0);
            case 2:
                DungeonRoom room1 = nextRooms.get(0);
                DungeonRoom room2 = nextRooms.get(1);
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    return null;
                }
                return player.chooseUse(
                        Outcome.Neutral, "Choose which room to go to",
                        null, room1.name, room2.name, null, game
                ) ? room1 : room2;
            default:
                throw new UnsupportedOperationException("there shouldn't be more than two rooms to go to");
        }
    }

    String generateDestinationText() {
        if (nextRooms.isEmpty()) {
            return "";
        }
        return " <i>(Leads to "
                + nextRooms
                .stream()
                .map(DungeonRoom::getName)
                .reduce((a, b) -> a + " or " + b)
                .orElse("")
                + ")</i>";
    }

    public static boolean isRoomTrigger(StackObject stackObject) {
        return stackObject instanceof StackAbility
                && stackObject.getStackAbility() instanceof RoomTriggeredAbility;
    }

    public static boolean isRoomTrigger(TriggeredAbility ability) {
        return ability instanceof RoomTriggeredAbility;
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
        setTriggerPhrase("When you enter this room, ");
    }

    RoomTriggeredAbility(DungeonRoom room, final RoomTriggeredAbility ability) {
        super(ability);
        this.room = room;
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
        return new RoomTriggeredAbility(this.room, this);
    }

    public String getText() {
        return room.getName() + " &mdash; "
                + CardUtil.getTextWithFirstCharUpperCase(super.getRule())
                + room.generateDestinationText();
    }

    @Override
    public String getRule() {
        return super.getRule() + " <i>(" + room.getName() + ")</i>";
    }
}
