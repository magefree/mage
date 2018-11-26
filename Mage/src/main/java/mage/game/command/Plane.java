package mage.game.command;

import java.lang.reflect.Constructor;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.Planes;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypeList;

/**
 * @author spjspj
 */
public class Plane implements CommandObject {

    private static EnumSet<CardType> emptySet = EnumSet.noneOf(CardType.class);
    private static ObjectColor emptyColor = new ObjectColor();
    private static ManaCosts emptyCost = new ManaCostsImpl();

    private String name = "";
    private UUID id;
    private UUID controllerId;
    private MageObject sourceObject;
    private FrameStyle frameStyle;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage = "";

    public Plane() {
        this.id = UUID.randomUUID();
        this.frameStyle = FrameStyle.M15_NORMAL;
    }

    public Plane(final Plane plane) {
        this.id = plane.id;
        this.name = plane.name;
        this.frameStyle = plane.frameStyle;
        this.controllerId = plane.controllerId;
        this.sourceObject = plane.sourceObject;
        this.abilites = plane.abilites.copy();
        this.expansionSetCodeForImage = plane.expansionSetCodeForImage;
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
                name = sourceObject.getSubtype(null).toString();
            }
            if (expansionSetCodeForImage.isEmpty()) {
                expansionSetCodeForImage = ((Card) sourceObject).getExpansionSetCode();
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
    public EnumSet<CardType> getCardType() {
        return emptySet;
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        return new SubTypeList();
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
    public boolean hasAbility(UUID abilityId, Game game) {
        return abilites.containsKey(abilityId);
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
    public int getConvertedManaCost() {
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
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public Plane copy() {
        return new Plane(this);
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

    public boolean isAllCreatureTypes() {
        return false;
    }

    public void setIsAllCreatureTypes(boolean value) {
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

    public static Plane getRandomPlane() {
        int pick = new Random().nextInt(Planes.values().length);
        String planeName = Planes.values()[pick].toString();
        planeName = "mage.game.command.planes." + planeName;
        try {
            Class<?> c = Class.forName(planeName);
            Constructor<?> cons = c.getConstructor();
            Object plane = cons.newInstance();
            if (plane instanceof Plane) {
                return (Plane) plane;
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
