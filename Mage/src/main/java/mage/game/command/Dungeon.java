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
import mage.abilities.text.TextPart;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypes;

import java.util.*;

/**
 * @author TheElk801
 */
public class Dungeon implements CommandObject {

    private static final ArrayList<CardType> emptySet = new ArrayList<>(Arrays.asList(CardType.DUNGEON));
    private static final ObjectColor emptyColor = new ObjectColor();
    private static final ManaCosts<ManaCost> emptyCost = new ManaCostsImpl<>();

    private final String name;
    private UUID id;
    private UUID controllerId;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private FrameStyle frameStyle;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage = "";
    private final List<DungeonRoom> dungeonRooms = new ArrayList<>();
    private DungeonRoom currentRoom = null;

    public Dungeon(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Dungeon(final Dungeon dungeon) {
        this.id = dungeon.id;
        this.name = dungeon.name;
        this.frameStyle = dungeon.frameStyle;
        this.controllerId = dungeon.controllerId;
        this.copy = dungeon.copy;
        this.copyFrom = (dungeon.copyFrom != null ? dungeon.copyFrom : null);
        this.abilites = dungeon.abilites.copy();
        this.expansionSetCodeForImage = dungeon.expansionSetCodeForImage;
    }

    public void addRoom(DungeonRoom room) {
        this.dungeonRooms.add(room);
        this.abilites.add(room.getRoomTriggeredAbility());
    }

    public void moveToNextRoom(Ability source, Game game) {
        if (currentRoom == null) {
            currentRoom = dungeonRooms.get(0);
        } else {
            currentRoom = currentRoom.chooseNextRoom(source, game);
        }
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.ROOM_ENTERED, currentRoom.getId(), source, source.getControllerId()
        ));
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
    public ArrayList<CardType> getCardType() {
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

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
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
