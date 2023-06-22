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
import mage.cards.FrameStyle;
import mage.cards.repository.TokenInfo;
import mage.cards.repository.TokenRepository;
import mage.constants.CardType;
import mage.constants.Planes;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.RandomUtil;
import mage.util.SubTypes;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public abstract class Plane extends CommandObjectImpl {

    private static final ManaCosts emptyCost = new ManaCostsImpl<>();

    private Planes planeType = null;

    private UUID controllerId;
    private MageObject sourceObject;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private FrameStyle frameStyle;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();

    public Plane() {
        super("");
        this.frameStyle = FrameStyle.M15_NORMAL;
    }

    public Plane(final Plane plane) {
        super(plane);
        this.planeType = plane.planeType;
        this.frameStyle = plane.frameStyle;
        this.controllerId = plane.controllerId;
        this.sourceObject = plane.sourceObject;
        this.copy = plane.copy;
        this.copyFrom = (plane.copyFrom != null ? plane.copyFrom.copy() : null);
        this.abilites = plane.abilites.copy();
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    public void setSourceObject() {
        this.sourceObject = null;

        // choose set code due source
        TokenInfo foundInfo = TokenRepository.instance.findPreferredTokenInfoForClass(this.getClass().getName(), null);
        if (foundInfo != null) {
            this.setExpansionSetCode(foundInfo.getSetCode());
            this.setCardNumber("");
            this.setImageNumber(foundInfo.getImageNumber());
        } else {
            // how-to fix: add plane to the tokens-database
            throw new IllegalArgumentException("Wrong code usage: can't find token info for the plane: " + this.getClass().getName());
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
    abstract public Plane copy();

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
        return planeType != null ? planeType.getFullName() : "";
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Planes don't use setName, use setPlaneType instead");
    }

    public void setPlaneType(Planes planeType) {
        this.planeType = planeType;
    }

    public Planes getPlaneType() {
        return this.planeType;
    }

    @Override
    public List<CardType> getCardType(Game game) {
        return Collections.emptyList();
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
    public List<SuperType> getSuperType(Game game) {
        return Collections.emptyList();
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
        return ObjectColor.COLORLESS;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return ObjectColor.COLORLESS;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return ObjectColor.COLORLESS;
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
    public int getStartingDefense() {
        return 0;
    }

    @Override
    public void setStartingDefense(int startingDefense) {
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1; // Planes can't move zones until now so return always 1
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

    public static Plane createPlane(Planes planeType) {
        if (planeType != null) {
            String planeFullClass = "mage.game.command.planes." + planeType.getClassName();
            try {
                Class<?> c = Class.forName(planeFullClass);
                Constructor<?> cons = c.getConstructor();
                Object plane = cons.newInstance();
                if (plane instanceof Plane) {
                    // TODO: generate image for plane here?
                    return (Plane) plane;
                }
            } catch (Exception ex) {
            }
        }
        return null;
    }

    public static Plane createPlaneByFullName(String fullName) {
        Planes planeType = Planes.fromFullName(fullName);
        return createPlane(planeType);
    }

    public static Plane createRandomPlane() {
        int pick = RandomUtil.nextInt(Planes.values().length);
        Planes planeType = Planes.values()[pick];
        return createPlane(planeType);
    }
}
