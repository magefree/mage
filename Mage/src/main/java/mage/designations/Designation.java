package mage.designations;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.text.TextPart;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.Copyable;
import mage.util.GameLog;
import mage.util.SubTypeList;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class Designation implements MageObject, Copyable<Designation> {

    private static final ObjectColor emptyColor = new ObjectColor();
    private static final ManaCostsImpl emptyCost = new ManaCostsImpl();

    private String name;
    private final DesignationType designationType;
    private UUID id;
    private final FrameStyle frameStyle;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage;
    private final boolean unique; // can a designation be added multiple times (false) or only once to an object (true)

    public Designation(DesignationType designationType, String expansionSetCode) {
        this(designationType, expansionSetCode, true);
    }

    public Designation(DesignationType designationType, String expansionSetCode, boolean unique) {
        this.name = designationType.toString();
        this.designationType = designationType;
        this.id = UUID.randomUUID();
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCodeForImage = expansionSetCode;
        this.unique = unique;
    }

    public Designation(final Designation designation) {
        this.name = designation.name;
        this.designationType = designation.designationType;
        this.id = designation.id;
        this.frameStyle = designation.frameStyle;
        this.copy = designation.copy;
        this.copyFrom = (designation.copyFrom != null ? designation.copyFrom.copy() : null);
        this.abilites = designation.abilites.copy();
        this.expansionSetCodeForImage = designation.expansionSetCodeForImage;
        this.unique = designation.unique;
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    public void assignNewId() {
        this.id = UUID.randomUUID();
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
    public String getImageName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void addAbility(Ability ability) {
        ability.setSourceId(id);
        abilites.add(ability);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilites;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return emptyColor;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public DesignationType getDesignationType() {
        return designationType;
    }

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public ArrayList<CardType> getCardType() {
        return new ArrayList<>();
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
    public boolean hasAbility(Ability ability, Game game) {
        return this.getAbilities().contains(ability);
    }

    @Override
    public ObjectColor getColor(Game game) {
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
    public void setStartingLoyalty(int startingLoyalty) {
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
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

    /**
     * @param game
     * @param controllerId
     */
    public void start(Game game, UUID controllerId) {

    }

    @Override
    public boolean isAllCreatureTypes() {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {

    }

    @Override
    public List<TextPart> getTextParts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TextPart addTextPart(TextPart textPart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isUnique() {
        return unique;
    }
}
