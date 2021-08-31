package mage.game.command;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.hint.HintUtils;
import mage.abilities.text.TextPart;
import mage.cards.FrameStyle;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.command.dungeons.DungeonOfTheMadMageDungeon;
import mage.game.command.dungeons.LostMineOfPhandelverDungeon;
import mage.game.command.dungeons.TombOfAnnihilationDungeon;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.GameLog;
import mage.util.SubTypes;

import java.util.*;

/**
 * @author TheElk801
 */
public class Dungeon implements CommandObject {

    private static final Set<String> dungeonNames = new HashSet<>();

    static {
        dungeonNames.add("Tomb of Annihilation");
        dungeonNames.add("Lost Mine of Phandelver");
        dungeonNames.add("Dungeon of the Mad Mage");
    }

    private static final List<CardType> emptySet = Arrays.asList(CardType.DUNGEON);
    private static final ObjectColor emptyColor = new ObjectColor();
    private static final ManaCosts<ManaCost> emptyCost = new ManaCostsImpl<>();

    private final String name;
    private UUID id;
    private UUID controllerId;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private FrameStyle frameStyle;
    private final Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage;
    private final List<DungeonRoom> dungeonRooms = new ArrayList<>();
    private DungeonRoom currentRoom = null;

    public Dungeon(String name, String expansionSetCodeForImage) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public Dungeon(final Dungeon dungeon) {
        this.id = dungeon.id;
        this.name = dungeon.name;
        this.frameStyle = dungeon.frameStyle;
        this.controllerId = dungeon.controllerId;
        this.copy = dungeon.copy;
        this.copyFrom = (dungeon.copyFrom != null ? dungeon.copyFrom : null);
        this.expansionSetCodeForImage = dungeon.expansionSetCodeForImage;
        this.copyRooms(dungeon);
    }

    private void copyRooms(Dungeon dungeon) {
        Map<String, DungeonRoom> copyMap = new HashMap<>();
        for (DungeonRoom dungeonRoom : dungeon.dungeonRooms) {
            DungeonRoom copiedRoom = copyMap.computeIfAbsent(dungeonRoom.getName(), (s) -> dungeonRoom.copy());
            for (DungeonRoom nextRoom : dungeonRoom.getNextRooms()) {
                copiedRoom.addNextRoom(copyMap.computeIfAbsent(nextRoom.getName(), (s) -> nextRoom.copy()));
            }
            this.addRoom(copiedRoom);
        }
        this.currentRoom = copyMap.computeIfAbsent(dungeon.currentRoom.getName(), (s) -> dungeon.currentRoom.copy());
    }

    public void addRoom(DungeonRoom room) {
        this.dungeonRooms.add(room);
        room.getRoomTriggeredAbility().setSourceId(id);
        this.abilites.add(room.getRoomTriggeredAbility());
    }

    public void moveToNextRoom(UUID playerId, Game game) {
        if (currentRoom == null) {
            currentRoom = dungeonRooms.get(0);
        } else {
            currentRoom = currentRoom.chooseNextRoom(playerId, game);
        }
        Player player = game.getPlayer(getControllerId());
        if (player != null) {
            game.informPlayers(player.getLogName() + " has entered " + currentRoom.getName());
        }
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.ROOM_ENTERED, currentRoom.getId(), null, playerId
        ));
    }

    public DungeonRoom getCurrentRoom() {
        return currentRoom;
    }

    public boolean hasNextRoom() {
        return currentRoom != null && currentRoom.hasNextRoom();
    }

    public List<String> getRules() {
        List<String> rules = new ArrayList<>();
        rules.add("<i>(" + (
                currentRoom != null ?
                        "Currently in " + currentRoom.getName() :
                        "Not currently in a room"
        ) + ")</i>");
        dungeonRooms.stream()
                .map(room -> {
                    // mark useful rooms by icons
                    String prefix = "";
                    if (room.equals(currentRoom)) {
                        prefix += HintUtils.prepareText(null, null, HintUtils.HINT_ICON_DUNGEON_ROOM_CURRENT);
                    }
                    if (currentRoom != null && currentRoom.getNextRooms().stream().anyMatch(room::equals)) {
                        prefix += HintUtils.prepareText(null, null, HintUtils.HINT_ICON_DUNGEON_ROOM_NEXT);
                    }
                    return prefix + room;
                })
                .forEach(rules::add);
        return rules;
    }

    public static Dungeon selectDungeon(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        Choice choice = new ChoiceImpl(true, ChoiceHintType.CARD_DUNGEON);
        choice.setMessage("Choose a dungeon to venture into");
        choice.setChoices(dungeonNames);
        player.choose(Outcome.Neutral, choice, game);
        return createDungeon(choice.getChoice());
    }

    public static Dungeon createDungeon(String name) {
        switch (name) {
            case "Tomb of Annihilation":
                return new TombOfAnnihilationDungeon();
            case "Lost Mine of Phandelver":
                return new LostMineOfPhandelverDungeon();
            case "Dungeon of the Mad Mage":
                return new DungeonOfTheMadMageDungeon();
            default:
                throw new UnsupportedOperationException("A dungeon should have been chosen");
        }
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    @Override
    public void assignNewId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public MageObject getSourceObject() {
        return null;
    }

    @Override
    public UUID getSourceId() {
        return null;
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
        this.abilites.setControllerId(controllerId);
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copyFrom) {
        this.copy = isCopy;
        this.copyFrom = (copyFrom != null ? copyFrom.copy() : null);
    }

    @Override
    public boolean isCopy() {
        return this.copy;
    }

    @Override
    public MageObject getCopyFrom() {
        return this.copyFrom;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public String getImageName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public List<CardType> getCardType(Game game) {
        return emptySet;
    }

    @Override
    public SubTypes getSubtype() {
        return new SubTypes();
    }

    @Override
    public SubTypes getSubtype(Game game) {
        return new SubTypes();
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return false;
    }

    @Override
    public EnumSet<SuperType> getSuperType() {
        return EnumSet.noneOf(SuperType.class);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilites;
    }

    @Override
    public boolean hasAbility(Ability ability, Game game) {
        return getAbilities().contains(ability);
    }

    @Override
    public ObjectColor getColor() {
        return emptyColor;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return emptyColor;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return emptyColor;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return emptyCost;
    }

    @Override
    public int getManaValue() {
        return 0;
    }

    @Override
    public MageInt getPower() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public MageInt getToughness() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public int getStartingLoyalty() {
        return 0;
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Dungeon copy() {
        return new Dungeon(this);
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
    }

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1;
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean isAllCreatureTypes(Game game) {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
    }

    @Override
    public void setIsAllCreatureTypes(Game game, boolean value) {
    }

    public void discardEffects() {
        for (Ability ability : abilites) {
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof ContinuousEffect) {
                    ((ContinuousEffect) effect).discard();
                }
            }
        }
    }

    @Override
    public List<TextPart> getTextParts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TextPart addTextPart(TextPart textPart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePTCDA() {
    }
}
