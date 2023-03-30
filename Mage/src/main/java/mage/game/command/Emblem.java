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
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.RandomUtil;
import mage.util.SubTypes;

import java.util.*;

/**
 * @author nantuko
 */
public class Emblem implements CommandObject {

    private static final List<CardType> emptyList = Collections.unmodifiableList(new ArrayList<>());
    private static final ObjectColor emptyColor = new ObjectColor();
    private static final ManaCosts emptyCost = new ManaCostsImpl<>();

    private String name = "";
    private UUID id;
    private UUID controllerId;
    private MageObject sourceObject;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private FrameStyle frameStyle;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage = "";

    // list of set codes emblem images are available for
    protected List<String> availableImageSetCodes = new ArrayList<>();

    public Emblem() {
        this.id = UUID.randomUUID();
    }

    public Emblem(final Emblem emblem) {
        this.id = emblem.id;
        this.name = emblem.name;
        this.frameStyle = emblem.frameStyle;
        this.controllerId = emblem.controllerId;
        this.sourceObject = emblem.sourceObject;
        this.copy = emblem.copy;
        this.copyFrom = (emblem.copyFrom != null ? emblem.copyFrom : null);
        this.abilites = emblem.abilites.copy();
        this.expansionSetCodeForImage = emblem.expansionSetCodeForImage;
        this.availableImageSetCodes = emblem.availableImageSetCodes;
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    @Override
    public void assignNewId() {
        this.id = UUID.randomUUID();
    }

    public void setSourceObject(MageObject sourceObject) {
        this.sourceObject = sourceObject;
        if (sourceObject instanceof Card) {
            if (name.isEmpty()) {
                name = sourceObject.getSubtype().toString();
            }
            if (expansionSetCodeForImage.isEmpty()) {
                expansionSetCodeForImage = ((Card) sourceObject).getExpansionSetCode();
            }
            if (!availableImageSetCodes.isEmpty()) {
                if (expansionSetCodeForImage.equals("") || !availableImageSetCodes.contains(expansionSetCodeForImage)) {
                        expansionSetCodeForImage = availableImageSetCodes.get(RandomUtil.nextInt(availableImageSetCodes.size()));
                }
            }
        }
    }

    @Override
    public MageObject getSourceObject() {
        return sourceObject;
    }

    @Override
    public UUID getSourceId() {
        if (sourceObject != null) {
            return sourceObject.getId();
        }
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
        this.name = name;
    }

    @Override
    public List<CardType> getCardType(Game game) {
        return emptyList;
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
    public UUID getId() {
        return this.id;
    }

    @Override
    public Emblem copy() {
        return new Emblem(this);
    }

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1; // Emblems can't move zones until now so return always 1
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
    public void removePTCDA() {
    }
}
