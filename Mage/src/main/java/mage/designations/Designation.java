package mage.designations;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.SubTypes;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class Designation extends MageObjectImpl {

    private static final ManaCostsImpl emptyCost = new ManaCostsImpl<>();

    private final DesignationType designationType;
    private final boolean unique; // can a designation be added multiple times (false) or only once to an object (true)

    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)

    public Designation(DesignationType designationType) {
        this(designationType, true);
    }

    public Designation(DesignationType designationType, boolean unique) {
        super(UUID.randomUUID());
        this.designationType = designationType;
        this.unique = unique;
        this.name = designationType.toString();
        this.frameStyle = FrameStyle.M15_NORMAL;
    }

    public Designation(final Designation designation) {
        super(designation);
        this.designationType = designation.designationType;
        this.unique = designation.unique;
        this.frameStyle = designation.frameStyle;
        this.copy = designation.copy;
        this.copyFrom = (designation.copyFrom != null ? designation.copyFrom.copy() : null);
    }

    @Override
    public abstract Designation copy();

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

    public void addAbility(Ability ability) {
        ability.setSourceId(this.objectId);
        this.abilities.add(ability);
        this.abilities.addAll(ability.getSubAbilities());
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return ObjectColor.COLORLESS;
    }

    public DesignationType getDesignationType() {
        return designationType;
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
    public boolean hasAbility(Ability ability, Game game) {
        return this.getAbilities().contains(ability);
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
    public void removePTCDA() {
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

    public boolean isUnique() {
        return unique;
    }
}
